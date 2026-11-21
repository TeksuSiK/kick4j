---
name: release
description: Cut a kick4j release — bump the version from -SNAPSHOT, tag, publish a GitHub Release with notes, then open the next -SNAPSHOT. Use when asked to release, publish or tag a new kick4j version.
disable-model-invocation: true
---

# Release kick4j

Every push to `main` runs `.github/workflows/publish.yml`, which publishes whatever `version` is in `build.gradle.kts` to Reposilite (`releases` repo when there is no `-SNAPSHOT` suffix). Pushing the release commit **is** the publish, and a release version can't be republished — confirm with the user before every push.

## 1. Pre-flight

- On `main`, clean tree, up to date with `origin/main`; no open PRs the user wants in this release (`gh pr list`).
- `./gradlew clean build` passes.
- Determine the version: current `version` in `build.gradle.kts` minus `-SNAPSHOT` (e.g. `1.3.0-SNAPSHOT` → `1.3.0`). Ask whether to bump differently: breaking change → major, new features → minor, fixes only → patch. Kick4j tags have no `v` prefix (`1.2.0`).

## 2. Release notes

Collect changes since the last tag: `git log <last-tag>..HEAD --oneline` and the merged PRs (`gh pr list --state merged --search "merged:>=<last-tag date>"`). Draft notes in the style of the 1.2.0 release (`gh release view 1.2.0`):

- one-paragraph summary
- `## ⚠️ Breaking change` (only if any, with migration code)
- `## Highlights`
- `## Deprecated (still functional)` (if any)
- `## Notes` (if any)

GitHub's generated "What's Changed" list is appended automatically by `--generate-notes`. Show the draft to the user before continuing.

## 3. Release commit

- Set `version = "X.Y.Z"` in `build.gradle.kts`.
- Commit `chore: release X.Y.Z`.
- After the user confirms: `git push origin main` → publishes `X.Y.Z`. Watch the run with `gh run list --workflow publish.yml --limit 1` and stop if it fails.
- `gh release create X.Y.Z --target <release-commit-sha> --title "Release X.Y.Z" --notes-file <draft> --generate-notes` (this creates the tag).

## 4. Next development version

- Set `version = "X.(Y+1).0-SNAPSHOT"` in `build.gradle.kts`.
- Commit `chore: prepare next development version X.(Y+1).0-SNAPSHOT` and push (publishes a snapshot).
- Report the release URL and both workflow runs to the user.
