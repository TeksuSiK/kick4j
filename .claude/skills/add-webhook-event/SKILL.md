---
name: add-webhook-event
description: Add support for a new Kick webhook event type (or a new version of an existing one) to kick4j — event class, nested models, dispatcher test with the documented payload, README table. Use when asked to add, support or handle a Kick webhook/event such as "channel.xyz.updated".
---

# Add a Kick webhook event

Input: the event name (e.g. `kicks.gifted`) and version. Open the Kick docs "Event types" / webhook payloads page (https://docs.kick.com) and copy the exact example payload — it is the source of truth and is reused verbatim in the test.

## 1. Event class

Create `src/main/java/pl/teksusik/kick4j/events/type/<Name>Event.java` modelled on `KicksGiftedEvent.java`:

- `extends KickEvent`
- `private final` fields, `@JsonCreator` constructor with `@JsonProperty("snake_case")` per parameter, getters only
- static `getEventType()` returning the exact event name and static `getEventVersion()` returning the version as a string (`"1"`)

The dispatcher discovers the type/version through these static methods by reflection, so no dispatcher or registry change is needed — and a typo there silently drops events.

New version of an existing event: create a separate class (e.g. `ChatMessageSentV2Event`) rather than mutating the v1 class.

## 2. Nested models

- Reuse before creating: `EventUser`, `Identity`, `Badge`, `Emote`, `ReplyInfo`, `LivestreamMetadata`, `BanMetadata`, and `rewards.RedemptionStatus`.
- Kick often sends a reduced user object (no `is_anonymous`/`identity`); reuse `EventUser` and let the missing fields be `null` — don't create a near-duplicate class.
- New nested objects go in `events/type/` with the same immutable style.

## 3. Test

Create `src/test/java/pl/teksusik/kick4j/events/<Name>EventTest.java` extending `KickEventDispatcherTest` (see `KicksGiftedEventTest.java`):

- `PAYLOAD` text block = the docs example, unchanged
- `dispatchAndCapture(<Name>Event.class, "<event.name>", "<version>", PAYLOAD)`
- assert every top-level field and every nested field

The test mapper does **not** disable `FAIL_ON_UNKNOWN_PROPERTIES`, so a field missing from the model fails the test — keep it that way and model every documented field.

## 4. Docs and verification

- `README.md` → "Supported Events" table: add `| \`<event.name>\` | \`<Name>Event\` | <version> |`, grouped with related events.
- If the event needs a new OAuth scope, add it to `authorization/Scope.java`.
- Run `./gradlew clean build`.
- Commit as `feat: add <event.name> webhook event`, body naming the class, nested models and reused types.
