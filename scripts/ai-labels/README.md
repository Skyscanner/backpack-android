# AI Work Labels

Automated tracking for AI-assisted development work using git hooks and GitHub Actions.

## Overview

Tracks AI contributions by:
- Extracting metadata from `Log.<tool>.yaml` files at commit time
- Appending `ai:` prefixed labels to commit messages
- Auto-applying PR labels via GitHub Actions
- Capturing which AI tools were used (Claude, Copilot, Cursor, etc.)

All labels use `ai:` prefix for clear separation from project labels.

## Quick Start

### Installation

Auto-installs on first build (installs git hooks):

```bash
./gradlew build
```

That's it! The system is ready to use.

## Usage

### Create Log File

When using AI tools, create `Log.<tool>.yaml` in **repository root**:

```bash
touch Log.claude.yaml
# or
touch Log.copilot.yaml
```

### Log Format

```yaml
changes:
  - type_of_change:
      - implementation
      - testing
    branch: feature/new-component
```

**Fields:**
- `type_of_change` (required): One or more of `implementation`, `testing`, `documentation`, `ci`
- `branch` (required): Current git branch for validation

### Label Categories

- `ai: implementation` - Code and features
- `ai: testing` - Test additions/updates
- `ai: documentation` - Docs updates
- `ai: ci` - CI/CD and build changes

Tool labels are auto-generated: `ai: claude`, `ai: copilot`, etc.

## Workflow

```bash
# 1. AI tool creates Log.claude.yaml (or you create manually)

# 2. Stage and commit
git add .
git commit -m "Add new component"

# Hook automatically:
# ✅ Extracts labels from log file
# ✅ Appends to commit message
# ✅ Deletes log file
```

When PR is created, GitHub Actions extracts all `ai:` labels from commits and applies them with random colors.

## Troubleshooting

**Getting `ai: failed` label?**
- Branch in log file doesn't match current git branch
- Invalid YAML syntax in log file
- Missing `type_of_change` field
- Invalid type_of_change value (must be: `implementation`, `testing`, `documentation`, `ci`)

**Labels not appearing?**
```bash
# Check log file location (must be repo root)
ls Log.*.yaml

# Validate YAML syntax (check indentation)
cat Log.claude.yaml

# Check branch matches
git branch --show-current
grep 'branch:' Log.claude.yaml

# Check hook exists
ls -la .git/hooks/prepare-commit-msg

# Reinstall hook
cp hooks/prepare-commit-msg .git/hooks/
chmod +x .git/hooks/prepare-commit-msg
```

**Manual hook execution:**
```bash
./scripts/ai-labels/process-labels.sh .git/COMMIT_EDITMSG
```
