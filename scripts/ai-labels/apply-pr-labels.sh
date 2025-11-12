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
# Apply labels to a GitHub PR
#
set -euo pipefail

PR_NUMBER="$1"
LABELS_FILE="$2"

# Get existing PR labels
gh pr view "$PR_NUMBER" --json labels -q '.labels[].name' > /tmp/existing_labels.txt 2>&1 || touch /tmp/existing_labels.txt

# Apply only new labels (deduplicated with comm)
echo "Applying labels from $LABELS_FILE to PR #$PR_NUMBER"
cat "$LABELS_FILE"

comm -23 <(sort "$LABELS_FILE") <(sort /tmp/existing_labels.txt) | while IFS= read -r label; do
    echo "Adding label: $label"
    gh pr edit "$PR_NUMBER" --add-label "$label" || echo "Failed to add label: $label"
done

rm -f /tmp/existing_labels.txt
