---
name: sync-api-schema
description: Audit kick4j against the current Kick.com public API documentation and report drift — missing or changed endpoints, response fields, OAuth scopes, webhook events/versions and deprecations. Use when asked to check what kick4j is missing, sync with the Kick API, find API drift, or plan the next release's API coverage. Read-only by default.
---

# Sync kick4j with the Kick API

Goal: a precise drift report, not code changes. Only implement fixes if the user asks; then use the `add-endpoint` / `add-webhook-event` skills, one PR per area.

## 1. Gather the current API surface

Read the Kick developer docs (https://docs.kick.com) — every API section (users, channels, channel rewards, chat, moderation, livestreams, categories, KICKs, drops, public key, events/subscriptions), the OAuth pages (endpoints and scope list) and the webhook event types/payloads. Also check the docs' changelog for deprecations. If a page can't be fetched, say so in the report rather than guessing.

Record for each endpoint: method, path, API version (v1/v2), query/body params, response fields with types, required scope and token type.

## 2. Gather the kick4j surface

- Paths: `KickConfiguration.Builder` defaults.
- Calls: public methods of every `*Client` (grep `this.get(`, `this.post(`, `this.patch(`, `this.delete(` and `.baseUrl(`).
- Response fields: the `@JsonProperty` names in each model's `@JsonCreator`.
- Scopes: `authorization/Scope.java`.
- Events: `getEventType()`/`getEventVersion()` in `events/type/*Event.java`.

## 3. Compare and report

Produce a table per category:

| Category | Item | Kick docs | kick4j | Action |
|---|---|---|---|---|

Categories: missing endpoints, changed endpoints (path/version/params), missing or renamed response fields, type mismatches, missing scopes, missing events or event versions, endpoints Kick deprecated that kick4j doesn't mark `@Deprecated`, things kick4j has that the docs no longer list.

End with a prioritised list of suggested PRs (one per API area), each with a conventional-commit title.

## 4. Optional follow-up

On request, open one GitHub issue per suggested PR with `gh issue create` (show the list first and wait for confirmation, since issues are public). This skill also suits a recurring check via `/schedule`.
