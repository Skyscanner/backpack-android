#!/usr/bin/env bash
#
# AI Labels Pre-Commit Hook
# Extracts labels from Log.<tool>.json and appends to commit message
#
set -euo pipefail

COMMIT_MSG_FILE="${1:-$(git rev-parse --git-dir)/COMMIT_EDITMSG}"
GIT_ROOT="$(git rev-parse --show-toplevel)"
LABELS=()

# Find and process Log.*.json files
for log in "${GIT_ROOT}"/Log.*.json; do
    [ -e "$log" ] || continue

    # Extract tool name from filename
    TOOL=$(basename "$log" .json | sed 's/Log\.//')

    # Extract type_of_change labels, add ai: prefix
    if command -v jq &>/dev/null && jq -e '.changes[].type_of_change[]' "$log" &>/dev/null; then
        while IFS= read -r label; do
            [ -n "$label" ] && LABELS+=("ai: $label")
        done < <(jq -r '.changes[].type_of_change[]' "$log" 2>/dev/null | sort -u)

        # Add tool label
        LABELS+=("ai: $TOOL")

        # Clean up processed log
        rm -f "$log"
    else
        # On error, add failure label
        LABELS+=("ai: failed")
        rm -f "$log" 2>/dev/null || true
    fi
done

# Append unique labels to commit message
if [ ${#LABELS[@]} -gt 0 ]; then
    UNIQUE_LABELS=($(printf '%s\n' "${LABELS[@]}" | sort -u))
    echo "" >> "$COMMIT_MSG_FILE"
    echo "${UNIQUE_LABELS[*]}" >> "$COMMIT_MSG_FILE"
fi

exit 0
