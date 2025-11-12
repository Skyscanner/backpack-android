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

pr_number="$1"
labels_file="$2"

gh pr view "$pr_number" --json labels -q '.labels[].name' > /tmp/existing_labels.txt 2>/dev/null || touch /tmp/existing_labels.txt

comm -23 <(sort "$labels_file") <(sort /tmp/existing_labels.txt) | while IFS= read -r label; do
    gh pr edit "$pr_number" --add-label "$label" 2>/dev/null || true
done

rm -f /tmp/existing_labels.txt
