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

#
# Create GitHub labels if they don't exist
#
set -euo pipefail

LABELS_FILE="$1"
CONFIG_FILE="$(dirname "$0")/config.default.yaml"

# Get color from config or generate random color
get_color() {
    local label="$1"
    if command -v yq &>/dev/null && [ -f "$CONFIG_FILE" ]; then
        local color=$(yq eval ".label_colors.\"$label\"" "$CONFIG_FILE" 2>/dev/null)
        if [ "$color" != "null" ] && [ -n "$color" ]; then
            echo "$color"
            return
        fi
    fi
    # Generate random hex color (avoid too dark or too light)
    printf "%02X%02X%02X" $((RANDOM % 156 + 50)) $((RANDOM % 156 + 50)) $((RANDOM % 156 + 50))
}

# Get existing labels
gh label list --json name -q '.[].name' > /tmp/repo_labels.txt 2>&1 || touch /tmp/repo_labels.txt

echo "Creating missing labels from $LABELS_FILE"
cat "$LABELS_FILE"

# Create missing labels
while IFS= read -r label; do
    [ -z "$label" ] && continue

    if grep -Fxq "$label" /tmp/repo_labels.txt 2>/dev/null; then
        echo "Label already exists: $label"
        continue
    fi

    COLOR=$(get_color "$label")
    echo "Creating label: $label with color $COLOR"
    gh label create "$label" --color "$COLOR" --description "AI-assisted work" || echo "Failed to create label: $label"
done < "$LABELS_FILE"

rm -f /tmp/repo_labels.txt
