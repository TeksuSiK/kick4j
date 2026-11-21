---
name: add-endpoint
description: Add a new Kick REST endpoint or a whole new API client to kick4j — models, request DTO, client method, KickClient/KickConfiguration wiring, tests and README. Use when asked to implement, add or support a Kick API endpoint, API area or Kick docs page.
---

# Add a Kick API endpoint

Input: a Kick docs URL/section (https://docs.kick.com), a pasted schema, or an endpoint name. Read the docs page before writing anything — field names, types, nullability, required scope and token type (user vs app) come from there, not from memory.

## 1. Decide where it goes

- Existing area (e.g. another `/channels/...` call) → add a method to that package's `XxxClient`.
- New area → new package `pl.teksusik.kick4j.<area>` with `<Area>Client`. Use `drops/` and `kicks/` as the reference implementations.
- V2 endpoint → call `.baseUrl(this.configuration.getBaseUrlV2())` on the request.

## 2. Configuration path

In `KickConfiguration` add, in the same order as the neighbouring entries:
- a `private final String` field, its assignment in the private constructor, a getter;
- a `Builder` field with the default path (`"/drops/claims"`, placeholders as `{id}`), and a fluent setter.

## 3. Models

Follow the existing style exactly (see `drops/DropClaim.java`):
- `private final` fields, one `@JsonCreator` constructor, `@JsonProperty("snake_case")` on each parameter, getters only.
- Wrapper types for optional/nullable fields, `Instant` for timestamps, `List<T>` for arrays, enums only when the docs list a closed set (reuse existing ones, e.g. `rewards.RedemptionStatus`).
- Reuse shared models before creating new ones.

Request DTOs:
- Query filters → class with `@JsonInclude(NON_NULL)`, `@JsonProperty` on fields, static `builder()` (see `drops/GetDropClaimsRequest.java`). If the API expects CSV-joined arrays, add `toQueryParams()` instead (see `categories/GetCategoriesV2Request.java`).
- JSON bodies → plain DTO or `Map.of(...)` for trivial wrappers.

## 4. Client method

```java
/**
 * <What it does>. Requires the {@code <scope>} scope / an app access token.
 */
public Foo getFoo(GetFooRequest request) {
    return this.get(this.configuration.getFoo())
            .queryParams(request)
            .send(new TypeReference<>() {});
}
```

- `send(...)` unwraps the `{data, message}` envelope; use `sendRaw(...)` with `PaginatedResponse<T>` (or a dedicated response type) when the body also carries `pagination`/cursor fields.
- Path params: `.pathParams(Map.of("id", id))`.
- Endpoints that only accept an app token force `.appAuth()`; otherwise leave the configured `AuthMode` default.
- Add a no-arg convenience overload when every filter is optional.
- `204 No Content` methods return `void` and still call `send(new TypeReference<>() {})`.

## 5. Wiring

- New client: field + construction in `KickClient`'s constructor + accessor (`public FooClient foo()`), in the same order as the others.
- New scope required → add it to `authorization/Scope.java`.

## 6. Tests

Add tests in `src/test/java/pl/teksusik/kick4j/<area>/` for logic we own: request DTO query-param mapping, custom serialization, deserialization of a docs example response. Don't mock HTTP for plain pass-through calls.

## 7. Docs and verification

- `README.md`: add a bullet under "Features" and a usage section next to the related ones.
- Run `./gradlew clean build` and fix every failure.
- Commit as `feat: add <area> <endpoint> endpoint` (or `feat: add <Area> API client`), body listing endpoints + models, `Refs #<issue>` if there is one.
