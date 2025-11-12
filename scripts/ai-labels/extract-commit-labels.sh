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

base_ref="$1"
head_ref="$2"
output_file="$3"

touch "$output_file"

git log --format=%B "$base_ref..$head_ref" 2>/dev/null | \
    { grep -oE 'ai: [a-z0-9-]+' || true; } | \
    sort -u > "$output_file"

wc -l < "$output_file" | tr -d ' '
