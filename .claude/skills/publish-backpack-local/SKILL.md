---
name: publish-backpack-local
description: Publish backpack-android to Maven local as a SNAPSHOT and wire skyscanner-app to consume it. Use when the user wants to test unreleased backpack-android changes inside the skyscanner-app before a real release.
---

Delegate this task to the `publish-backpack-local` subagent (via the Agent tool, `subagent_type: publish-backpack-local`). It runs on a cheaper model since the steps are mechanical: run `./gradlew publishToMavenLocal -Pversion="99.9.9.SNAPSHOT"`, then edit two files in the sibling `skyscanner-app` repo to point at the snapshot. See `.claude/agents/publish-backpack-local.md` for the full procedure.

Report the agent's result back to the user.
