#!/bin/bash
# Backpack for Android - Skyscanner's Design System
#
# Copyright 2025 Skyscanner Ltd
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

# Script to automatically update copyright years in source files
# Usage: ./update-copyright-year.sh [target_year]

set -e

# Get current year or use provided year
CURRENT_YEAR=${1:-$(date +%Y)}
OLD_YEAR="2018"

echo "Updating copyright from $OLD_YEAR to $CURRENT_YEAR..."

# Check if update is needed
if [ "$OLD_YEAR" == "$CURRENT_YEAR" ]; then
    echo "Copyright year is already current ($CURRENT_YEAR). No update needed."
    exit 0
fi

# Count files before update
echo "Counting files with old copyright..."
OLD_COUNT=$(find . -type f \( -name '*.kt' -o -name '*.java' -o -name '*.sh' \) ! -path "./build/*" ! -path "./.git/*" ! -path "./node_modules/*" -exec grep -l "Copyright $OLD_YEAR Skyscanner Ltd" {} \; 2>/dev/null | wc -l)
echo "Found $OLD_COUNT files with Copyright $OLD_YEAR"

# Update Java/Kotlin files (/* */ comments)
echo "Updating Java/Kotlin files..."
find . -type f \( -name '*.kt' -o -name '*.java' \) ! -path "./build/*" ! -path "./.git/*" ! -path "./node_modules/*" -exec grep -l "Copyright $OLD_YEAR Skyscanner Ltd" {} \; 2>/dev/null | \
while read -r file; do
    echo "Updating: $file"
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS sed - handle both variants
        sed -i '' "s/ \* Copyright $OLD_YEAR Skyscanner Ltd/ * Copyright $CURRENT_YEAR Skyscanner Ltd/g" "$file"
        sed -i '' "s/\* Copyright $OLD_YEAR Skyscanner Ltd/* Copyright $CURRENT_YEAR Skyscanner Ltd/g" "$file"
    else
        # GNU sed - handle both variants  
        sed -i "s/ \* Copyright $OLD_YEAR Skyscanner Ltd/ * Copyright $CURRENT_YEAR Skyscanner Ltd/g" "$file"
        sed -i "s/\* Copyright $OLD_YEAR Skyscanner Ltd/* Copyright $CURRENT_YEAR Skyscanner Ltd/g" "$file"
    fi
done

# Update Shell script files (# comments)  
echo "Updating Shell files..."
find . -type f -name '*.sh' ! -path "./build/*" ! -path "./.git/*" ! -path "./node_modules/*" -exec grep -l "Copyright $OLD_YEAR Skyscanner Ltd" {} \; 2>/dev/null | \
while read -r file; do
    echo "Updating: $file"
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS sed
        sed -i '' "s/# Copyright $OLD_YEAR Skyscanner Ltd/# Copyright $CURRENT_YEAR Skyscanner Ltd/g" "$file"
    else
        # GNU sed
        sed -i "s/# Copyright $OLD_YEAR Skyscanner Ltd/# Copyright $CURRENT_YEAR Skyscanner Ltd/g" "$file"
    fi
done

# Update copyright template file
COPYRIGHT_TEMPLATE="buildSrc/src/main/resources/copyright.txt"
if [ -f "$COPYRIGHT_TEMPLATE" ]; then
    echo "Updating copyright template: $COPYRIGHT_TEMPLATE"
    if [[ "$OSTYPE" == "darwin"* ]]; then
        sed -i '' "s/ \* Copyright $OLD_YEAR Skyscanner Ltd/ * Copyright $CURRENT_YEAR Skyscanner Ltd/g" "$COPYRIGHT_TEMPLATE"
    else
        sed -i "s/ \* Copyright $OLD_YEAR Skyscanner Ltd/ * Copyright $CURRENT_YEAR Skyscanner Ltd/g" "$COPYRIGHT_TEMPLATE"
    fi
fi

echo "Copyright year update completed!"

# Count updated files for verification
NEW_COUNT=$(find . -type f \( -name '*.kt' -o -name '*.java' -o -name '*.sh' \) ! -path "./build/*" ! -path "./.git/*" ! -path "./node_modules/*" -exec grep -l "Copyright $CURRENT_YEAR Skyscanner Ltd" {} \; 2>/dev/null | wc -l)
echo "Total files with updated copyright: $NEW_COUNT"

# Show remaining files with old copyright (should be 0)
REMAINING_COUNT=$(find . -type f \( -name '*.kt' -o -name '*.java' -o -name '*.sh' \) ! -path "./build/*" ! -path "./.git/*" ! -path "./node_modules/*" -exec grep -l "Copyright $OLD_YEAR Skyscanner Ltd" {} \; 2>/dev/null | wc -l)
echo "Files still with old copyright: $REMAINING_COUNT"