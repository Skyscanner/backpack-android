# Backpack Android - AI Work Labels

Automated system for tracking AI-assisted development work in Backpack Android.

## Overview

This system automatically:
1. Extracts work metadata from `Log.<tool>.json` files
2. Appends labels to commit messages with `ai:` prefix
3. Applies GitHub PR labels automatically
4. Tracks which AI tools were used

All labels are prefixed with `ai:` to clearly separate AI work from other project labels (like `major`, `minor`, `dependencies`).

## Quick Start

### 1. Prerequisites

Install `jq` for JSON processing:

```bash
# macOS
brew install jq

# Linux
sudo apt-get install jq
```

### 2. Auto-Installation

The system auto-installs on your first build:

```bash
./gradlew build
# or
# Open project in Android Studio (triggers Gradle sync)
```

That's it! The `prepare-commit-msg` hook is now active.

## Usage

### Creating a Log File

When you use AI tools (Claude, Copilot, Cursor, etc.) for development work, create a log file in the **repository root**:

```bash
# Create log file (use tool name in filename)
touch Log.claude.json
# or
touch Log.copilot.json
```

**Important**: Log file MUST be in repository root, not in subdirectories like `app/` or `Backpack/`.

### Log File Format

```json
{
  "changes": [
    {
      "type_of_change": ["implementation", "testing"],
      "branch": "feature/badge-component",
      "description": "Added BpkBadge component with tests",
      "model": "claude-sonnet-4.5"
    }
  ]
}
```

### Fields

| Field | Required | Options | Description |
|-------|----------|---------|-------------|
| `type_of_change` | ✅ | `implementation`, `testing`, `documentation`, `ci` | What type of work was done |
| `branch` | ⚠️ | Any string | Current git branch (optional but recommended) |
| `description` | ⚠️ | Any string | Brief description (optional but recommended) |
| `model` | ⚠️ | Any string | AI model used (e.g., `claude-sonnet-4.5`, `gpt-4`) |

### Use Case Labels

**Four simple categories:**

- **`ai: implementation`** - Creating new code or functionality (components, utilities, features)
- **`ai: testing`** - Writing or updating tests (unit tests, UI tests, snapshots)
- **`ai: documentation`** - Documentation updates (README, KDoc comments, guides)
- **`ai: ci`** - CI/CD or build system work (Gradle, GitHub Actions, scripts)

## Workflow

### Normal Workflow

```bash
# 1. Use AI tool to write code
# (tool creates Log.claude.json automatically, or you create it manually)

# 2. Stage your changes
git add .

# 3. Commit normally
git commit -m "Add Badge component"

# Hook processes log file automatically:
# ✅ Extracted labels
# ✅ Appended to commit message
# ✅ Cleaned up log file
```

### Commit Message Enhancement

**Before** (your message):
```
Add Badge component
```

**After** (enhanced by hook):
```
Add Badge component

AI-Labels: ai: implementation, ai: testing
AI-Tools: ai: claude
AI-Tool: ai: claude | Model: claude-sonnet-4.5
```

### PR Labels

When you create a PR, GitHub Actions automatically:
1. Scans all commits for `AI-Labels:` lines
2. Creates labels if they don't exist
3. Applies labels to the PR
4. Posts a summary comment

**Example PR labels:**
```
✅ minor                  # Semantic version
✅ ai: implementation     # AI helped with implementation
✅ ai: testing           # AI helped with tests
✅ ai: claude            # Claude was used
```

## Examples

### Example 1: New Component

```json
{
  "changes": [
    {
      "type_of_change": ["implementation"],
      "branch": "feature/badge",
      "description": "Created BpkBadge Compose component",
      "model": "claude-sonnet-4.5"
    }
  ]
}
```

**Result**: PR gets labels `ai: implementation` and `ai: claude`

### Example 2: Test Suite

```json
{
  "changes": [
    {
      "type_of_change": ["testing"],
      "branch": "test/badge-snapshots",
      "description": "Added Roborazzi snapshot tests",
      "model": "claude-sonnet-4.5"
    }
  ]
}
```

**Result**: PR gets labels `ai: testing` and `ai: claude`

### Example 3: Multiple Use Cases

```json
{
  "changes": [
    {
      "type_of_change": ["implementation", "testing", "documentation"],
      "branch": "feature/chip",
      "description": "Added Chip component with tests and docs",
      "model": "claude-sonnet-4.5"
    }
  ]
}
```

**Result**: PR gets labels `ai: implementation`, `ai: testing`, `ai: documentation`, and `ai: claude`

### Example 4: CI Work

```json
{
  "changes": [
    {
      "type_of_change": ["ci"],
      "branch": "ci/update-gradle",
      "description": "Updated Gradle to 8.5",
      "model": "claude-sonnet-4.5"
    }
  ]
}
```

**Result**: PR gets labels `ai: ci` and `ai: claude`

### Example 5: Multiple Tools

```bash
# You used Claude for implementation
cat Log.claude.json
{
  "changes": [{
    "type_of_change": ["implementation"],
    "description": "Created component",
    "model": "claude-sonnet-4.5"
  }]
}

# Then used Copilot for tests
cat Log.copilot.json
{
  "changes": [{
    "type_of_change": ["testing"],
    "description": "Generated tests",
    "model": "gpt-4"
  }]
}

# Commit processes both logs
git commit -m "Add component and tests"
```

**Result**: PR gets labels `ai: implementation`, `ai: testing`, `ai: claude`, and `ai: copilot`

## Configuration

### Customizing Labels

Edit `config.default.yaml` to customize:
- Label colors
- Label descriptions
- Tool label colors

**Note**: Changes to `config.default.yaml` are shared across the team (checked into git).

### Local Overrides

Create `config.yaml` (gitignored) for personal overrides:

```bash
cp config.default.yaml config.yaml
# Edit config.yaml with your preferences
```

## Troubleshooting

### Labels Not Appearing

**Check 1**: Is log file in repository root?
```bash
# Wrong locations:
app/Log.claude.json           # ❌ Too deep
Backpack/Log.claude.json      # ❌ Too deep

# Correct location:
Log.claude.json               # ✅ Repository root
```

**Check 2**: Is JSON valid?
```bash
jq empty Log.claude.json
# If error: fix JSON syntax
```

**Check 3**: Is hook installed?
```bash
ls -la .git/hooks/prepare-commit-msg
# Should exist and be executable
```

**Check 4**: Re-run hook installation
```bash
./gradlew installGitHooks
```

### Hook Not Running

**Solution 1**: Reinstall hooks
```bash
./gradlew clean installGitHooks
```

**Solution 2**: Check hook permissions
```bash
chmod +x .git/hooks/prepare-commit-msg
```

### jq Not Found

**macOS**:
```bash
brew install jq
```

**Linux**:
```bash
sudo apt-get install jq
```

**Windows (WSL)**:
```bash
sudo apt-get install jq
```

### Manual Rerun

If the hook fails, you can run it manually:

```bash
./scripts/ai-labels/process-labels.sh .git/COMMIT_EDITMSG
```

### View Hook Output

To see detailed hook execution:

```bash
# Make a test commit and watch output
git commit -m "test" --verbose
```

## How It Works

### Local (Pre-Commit Hook)

```
Developer commits
    ↓
prepare-commit-msg hook runs
    ↓
process-labels.sh script
    ↓
Searches repo root for Log.*.json
    ↓
Validates JSON structure
    ↓
Extracts type_of_change values
    ↓
Adds "ai: " prefix to labels
    ↓
Appends to commit message
    ↓
Deletes log files
    ↓
Commit proceeds
```

### Remote (GitHub Actions)

```
PR created/updated
    ↓
apply-ai-labels.yml workflow runs
    ↓
Scans all commits in PR
    ↓
Parses "AI-Labels:" lines
    ↓
Deduplicates labels
    ↓
Creates labels if missing
    ↓
Applies to PR
    ↓
Posts summary comment
```

## Files

- **`config.default.yaml`** - Default label configuration (committed to git)
- **`config.yaml`** - Local overrides (gitignored, auto-created)
- **`process-labels.sh`** - Main hook script
- **`.installed`** - Installation marker (gitignored)

## FAQ

**Q: Do I have to create log files manually?**
A: It depends on your AI tool. Some tools can auto-create them. Otherwise, yes, create manually.

**Q: Can I use multiple tools on the same PR?**
A: Yes! Create separate log files: `Log.claude.json`, `Log.copilot.json`, etc. The hook merges them.

**Q: What if I forget to create a log file?**
A: No problem! The system is opt-in. If no log file exists, commits proceed normally without AI labels.

**Q: Will this block my commits if something goes wrong?**
A: No. The hook is non-blocking. If errors occur, it logs them but doesn't prevent commits.

**Q: Can I add custom use case labels?**
A: Yes, edit `config.default.yaml` and add to `label_types`. Commit the change to share with team.

**Q: Why "ai:" prefix?**
A: It creates a clear namespace separation from other project labels and matches the existing `ai: copilot` label pattern.

## Support

For issues or questions:
1. Check this README
2. Check `CONTRIBUTING.md`
3. Ask in #backpack-android Slack channel
4. Create a GitHub issue
