#!/usr/bin/env bash
# Backpack for Android - Skyscanner's Design System
#
# Copyright 2018 - 2025 Skyscanner Ltd
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

set -euo pipefail

COMMIT_MSG_FILE="${1:-$(git rev-parse --git-dir)/COMMIT_EDITMSG}"
GIT_ROOT="$(git rev-parse --show-toplevel)"
CURRENT_BRANCH="$(git rev-parse --abbrev-ref HEAD)"
LABELS=()
VALID_TYPES=("implementation" "testing" "documentation" "ci")

validate_type() {
    local type="$1"
    for valid in "${VALID_TYPES[@]}"; do
        [[ "$type" == "$valid" ]] && return 0
    done
    return 1
}

parse_yaml_branch() {
    grep -E '^[[:space:]]+branch:' "$1" 2>/dev/null | head -1 | sed 's/.*:[[:space:]]*//' || echo ""
}

parse_yaml_types() {
    sed -n '/type_of_change:/,/branch:/p' "$1" 2>/dev/null | \
        grep -E '^[[:space:]]+- [a-z]' | \
        grep -v ':' | \
        sed 's/.*-[[:space:]]*//' || true
}

for log in "${GIT_ROOT}"/Log.*.yaml; do
    [ -e "$log" ] || continue

    TOOL=$(basename "$log" .yaml | sed 's/Log\.//' | tr '[:upper:]' '[:lower:]')

    LOG_BRANCH=$(parse_yaml_branch "$log")

    if [ -z "$LOG_BRANCH" ]; then
        LABELS+=("ai: failed")
        rm -f "$log" 2>/dev/null || true
        continue
    fi

    if [ "$LOG_BRANCH" != "$CURRENT_BRANCH" ]; then
        LABELS+=("ai: failed")
        rm -f "$log" 2>/dev/null || true
        continue
    fi

    HAS_VALID_TYPE=false
    while IFS= read -r label; do
        if [ -n "$label" ]; then
            if validate_type "$label"; then
                LABELS+=("ai: $label")
                HAS_VALID_TYPE=true
            else
                LABELS+=("ai: failed")
            fi
        fi
    done < <(parse_yaml_types "$log" | sort -u)

    if [ "$HAS_VALID_TYPE" = true ]; then
        LABELS+=("ai: $TOOL")
        rm -f "$log"
    else
        LABELS+=("ai: failed")
        rm -f "$log" 2>/dev/null || true
    fi
done

if [ ${#LABELS[@]} -gt 0 ]; then
    UNIQUE_LABELS=($(printf '%s\n' "${LABELS[@]}" | sort -u))
    echo "" >> "$COMMIT_MSG_FILE"
    echo "${UNIQUE_LABELS[*]}" >> "$COMMIT_MSG_FILE"
fi

exit 0
