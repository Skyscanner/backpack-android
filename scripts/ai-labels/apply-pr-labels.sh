#!/usr/bin/env bash
#
# Apply labels to a GitHub PR
#
set -euo pipefail

PR_NUMBER="$1"
LABELS_FILE="$2"

# Get existing PR labels
gh pr view "$PR_NUMBER" --json labels -q '.labels[].name' > /tmp/existing_labels.txt 2>/dev/null || true

# Apply only new labels (deduplicated with comm)
comm -23 <(sort "$LABELS_FILE") <(sort /tmp/existing_labels.txt) | \
    xargs -r -I {} gh pr edit "$PR_NUMBER" --add-label "{}" 2>/dev/null || true

rm -f /tmp/existing_labels.txt
