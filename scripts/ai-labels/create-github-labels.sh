#!/usr/bin/env bash
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
gh label list --json name -q '.[].name' > /tmp/repo_labels.txt 2>/dev/null || true

# Create missing labels
while IFS= read -r label; do
    [ -z "$label" ] && continue

    grep -Fxq "$label" /tmp/repo_labels.txt 2>/dev/null && continue

    COLOR=$(get_color "$label")
    gh label create "$label" --color "$COLOR" --description "AI-assisted work" 2>/dev/null || true
done < "$LABELS_FILE"

rm -f /tmp/repo_labels.txt
