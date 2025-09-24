# CI/CD Integration

## Overview

Figma Code Connect is integrated as steps in existing workflows:

- **Validation**: In `_build.yml` (runs on PRs and releases)
- **Publishing**: In `release.yml` (runs on releases only)

## Setup

Add this secret to your repository:
- `FIGMA_ACCESS_TOKEN`: Your Figma personal access token

## Implementation

### Validation (_build.yml)
```bash
if find backpack-compose/src/main/kotlin -name "*.figma.kt" -type f | grep -q .; then
  npx @figma/code-connect parse --verbose
  npx @figma/code-connect publish --dry-run
fi
```

### Publishing (release.yml)
```yaml
- name: Publish Code Connect to Figma
  run: npx @figma/code-connect publish --exit-on-unreadable-files
  env:
    FIGMA_ACCESS_TOKEN: ${{ secrets.FIGMA_ACCESS_TOKEN }}
```

## Monitoring

- **PR validation**: Actions → PR workflow → Build job → "Validate Code Connect files" step
- **Release publishing**: Actions → Release workflow → Deploy job → "Publish Code Connect to Figma" step