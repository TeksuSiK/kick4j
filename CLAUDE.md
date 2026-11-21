# kick4j

Java 21 client library for the Kick.com public API (`pl.teksusik:kick4j`). Gradle (Kotlin DSL), Jackson, JUnit 5 + Mockito.

## Commands

- Build and test: `./gradlew clean build`
- Tests only: `./gradlew test` (Mockito runs as a `-javaagent`, configured in `build.gradle.kts`)

## Layout

- One package per API area under `src/main/java/pl/teksusik/kick4j/` (`chat`, `drops`, `kicks`, `rewards`, ...), each with an `XxxClient` plus its models and `*Request` DTOs.
- `api/` — `ApiClient` base class with the fluent `RequestBuilder` (`get/post/patch/delete`, `pathParams`, `queryParams`, `body`, `baseUrl`, `appAuth`/`userAuth`, `send`/`sendRaw`), `ApiResponse` (`{data, message}` envelope), `PaginatedResponse<T>` + `Pagination` for cursor-paginated V2 endpoints, `ApiException`.
- `KickClient` wires every client and exposes it through an accessor (`client.drops()`); `KickConfiguration` holds every endpoint path as an overridable builder field with a default.
- `authorization/` — OAuth 2.1 (PKCE user flow and Client Credentials app flow), `Scope` enum, `AuthMode`.
- `events/type/` — webhook event classes (`extends KickEvent`, static `getEventType()`/`getEventVersion()`); `events/handler/` — dispatcher, signature verifier, built-in receiver.
- `src/main/java/pl/teksusik/kick4j/KickClientTest.java` is a local scratch file and is git-ignored on purpose; leave it alone.

## Conventions

- Models are immutable: `private final` fields, a `@JsonCreator` constructor with `@JsonProperty("snake_case")` on every parameter, plain getters, no setters, no Lombok, no records.
- Request DTOs with several optional fields use a static `builder()`; query DTOs carry `@JsonInclude(NON_NULL)` and `@JsonProperty` on fields.
- Use wrapper types (`Integer`, `Boolean`) for fields that may be absent. Timestamps are `java.time.Instant`.
- Javadoc on public client methods states the HTTP endpoint, required token type/scope and pagination behaviour. Keep comments sparse elsewhere.
- Deprecated Kick endpoints stay but get `@Deprecated` plus Javadoc pointing at the replacement.
- Tests live in `src/test/java` mirroring the main package; event tests extend `KickEventDispatcherTest` and use the exact payload from the Kick docs.
- Every user-visible addition updates `README.md` (feature list, usage section, Supported Events table).

## Git

- Conventional commits (`feat:`, `fix:`, `docs:`, `chore:`), one feature per PR from a `feature/...` branch, PR number appended on squash merge.
- Every push to `main` triggers `.github/workflows/publish.yml`, which publishes the current `version` to Reposilite (snapshots or releases repo by `-SNAPSHOT` suffix). Releases follow the `release` skill.

## Project skills

`.claude/skills/`: `add-endpoint`, `add-webhook-event`, `sync-api-schema`, `release`.
