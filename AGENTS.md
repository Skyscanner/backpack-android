# Project instructions

## Tool preferences

- For GitHub PRs, issues, comments, and reviews: use `gh` (GitHub CLI) via Bash, NOT `webfetch`. Example: `gh pr view 2772 --comments`, `gh pr comment 2772 --body "..."`, `gh api repos/Skyscanner/backpack-android/pulls/2772/comments`.
- Never use `webfetch` for github.com URLs — `gh` is faster, authenticated, and returns structured output.
