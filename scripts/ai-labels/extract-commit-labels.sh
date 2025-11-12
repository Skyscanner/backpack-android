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
