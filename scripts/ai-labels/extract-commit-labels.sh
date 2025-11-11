#!/usr/bin/env bash
#
# Extract AI labels from commit messages
#
set -euo pipefail

BASE_REF="$1"
HEAD_REF="$2"
OUTPUT_FILE="$3"

# Extract all "ai: <word>" patterns from commit messages
git log --format=%B "$BASE_REF..$HEAD_REF" | \
    grep -oE 'ai: [a-z0-9-]+' | \
    sort -u > "$OUTPUT_FILE"

# Return count
wc -l < "$OUTPUT_FILE" | tr -d ' '
