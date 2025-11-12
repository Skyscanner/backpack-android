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

labels_file="$1"

gh label list --json name -q '.[].name' > /tmp/repo_labels.txt 2>/dev/null || touch /tmp/repo_labels.txt
# Create missing labels
while IFS= read -r label; do
    [ -z "$label" ] && continue
    grep -Fxq "$label" /tmp/repo_labels.txt 2>/dev/null && continue

    color=$(printf "%02X%02X%02X" $((RANDOM % 156 + 50)) $((RANDOM % 156 + 50)) $((RANDOM % 156 + 50)))
    gh label create "$label" --color "$color" --description "AI-assisted work" 2>/dev/null || true
done < "$labels_file"

rm -f /tmp/repo_labels.txt
