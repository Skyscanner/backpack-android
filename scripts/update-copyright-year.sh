#!/bin/bash
# Backpack for Android - Skyscanner's Design System
#
# Copyright 2018 - 2026 Skyscanner Ltd
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
# This script finds ALL files with copyright headers, regardless of extension
# Usage: ./update-copyright-year.sh [target_year]

set -e

# Get current year or use provided year
CURRENT_YEAR=${1:-$(date +%Y)}
START_YEAR="2018"
NEW_PATTERN="Copyright $START_YEAR - $CURRENT_YEAR Skyscanner Ltd"

echo "Updating copyright to range format: $NEW_PATTERN..."

# Find ALL files with copyright, regardless of extension
ALL_COPYRIGHT_FILES=$(find . -type f ! -path "./build/*" ! -path "./.git/*" ! -path "./node_modules/*" -exec grep -l "Copyright.*Skyscanner Ltd" {} \; 2>/dev/null)

# Check if update is needed - look for files that don't already have the correct range format
FILES_WITH_TARGET_PATTERN=$(echo "$ALL_COPYRIGHT_FILES" | xargs grep -l "$NEW_PATTERN" 2>/dev/null | wc -l)
FILES_WITH_COPYRIGHT=$(echo "$ALL_COPYRIGHT_FILES" | wc -l)

if [ "$FILES_WITH_TARGET_PATTERN" -eq "$FILES_WITH_COPYRIGHT" ]; then
    echo "Copyright format is already up to date ($NEW_PATTERN). No update needed."
    exit 0
fi

# Count files before update
echo "Counting files that need copyright format update..."
OLD_COUNT=$(echo "$ALL_COPYRIGHT_FILES" | xargs grep -L "$NEW_PATTERN" 2>/dev/null | wc -l)
echo "Found $OLD_COUNT files that need copyright format update out of $FILES_WITH_COPYRIGHT total files with copyright"

# Function to detect comment style and update accordingly
update_file() {
    local file="$1"
    echo "Updating: $file"
    
    # Check comment style by looking at the copyright line context
    if grep -q "^ *\* Copyright" "$file" 2>/dev/null; then
        # Java/Kotlin/Gradle style comments (/* */ with *)
        if [[ "$OSTYPE" == "darwin"* ]]; then
            sed -i '' "s/ \* Copyright [0-9]\{4\} Skyscanner Ltd/ * $NEW_PATTERN/g" "$file"
            sed -i '' "s/\* Copyright [0-9]\{4\} Skyscanner Ltd/* $NEW_PATTERN/g" "$file"
            sed -i '' "s/ \* Copyright [0-9]\{4\} - [0-9]\{4\} Skyscanner Ltd/ * $NEW_PATTERN/g" "$file"
            sed -i '' "s/\* Copyright [0-9]\{4\} - [0-9]\{4\} Skyscanner Ltd/* $NEW_PATTERN/g" "$file"
        else
            sed -i "s/ \* Copyright [0-9]\{4\} Skyscanner Ltd/ * $NEW_PATTERN/g" "$file"
            sed -i "s/\* Copyright [0-9]\{4\} Skyscanner Ltd/* $NEW_PATTERN/g" "$file"
            sed -i "s/ \* Copyright [0-9]\{4\} - [0-9]\{4\} Skyscanner Ltd/ * $NEW_PATTERN/g" "$file"
            sed -i "s/\* Copyright [0-9]\{4\} - [0-9]\{4\} Skyscanner Ltd/* $NEW_PATTERN/g" "$file"
        fi
    elif grep -q "^# Copyright" "$file" 2>/dev/null; then
        # Shell script style comments (#)
        if [[ "$OSTYPE" == "darwin"* ]]; then
            sed -i '' "s/# Copyright [0-9]\{4\} Skyscanner Ltd/# $NEW_PATTERN/g" "$file"
            sed -i '' "s/# Copyright [0-9]\{4\} - [0-9]\{4\} Skyscanner Ltd/# $NEW_PATTERN/g" "$file"
        else
            sed -i "s/# Copyright [0-9]\{4\} Skyscanner Ltd/# $NEW_PATTERN/g" "$file"
            sed -i "s/# Copyright [0-9]\{4\} - [0-9]\{4\} Skyscanner Ltd/# $NEW_PATTERN/g" "$file"
        fi
    elif grep -q "^ *Copyright" "$file" 2>/dev/null; then
        # XML/TXT style comments (<!-- --> with spaces) or plain text
        if [[ "$OSTYPE" == "darwin"* ]]; then
            sed -i '' "s/    Copyright [0-9]\{4\} Skyscanner Ltd/    $NEW_PATTERN/g" "$file"
            sed -i '' "s/    Copyright [0-9]\{4\} - [0-9]\{4\} Skyscanner Ltd/    $NEW_PATTERN/g" "$file"
            sed -i '' "s/   Copyright [0-9]\{4\} Skyscanner Ltd/   $NEW_PATTERN/g" "$file"
            sed -i '' "s/   Copyright [0-9]\{4\} - [0-9]\{4\} Skyscanner Ltd/   $NEW_PATTERN/g" "$file"
        else
            sed -i "s/    Copyright [0-9]\{4\} Skyscanner Ltd/    $NEW_PATTERN/g" "$file"
            sed -i "s/    Copyright [0-9]\{4\} - [0-9]\{4\} Skyscanner Ltd/    $NEW_PATTERN/g" "$file"
            sed -i "s/   Copyright [0-9]\{4\} Skyscanner Ltd/   $NEW_PATTERN/g" "$file"
            sed -i "s/   Copyright [0-9]\{4\} - [0-9]\{4\} Skyscanner Ltd/   $NEW_PATTERN/g" "$file"
        fi
    else
        echo "Warning: Unknown comment style in $file, skipping"
    fi
}

# Update all files that need updating
echo "Updating all files with copyright headers..."
echo "$ALL_COPYRIGHT_FILES" | xargs grep -L "$NEW_PATTERN" 2>/dev/null | while read -r file; do
    update_file "$file"
done

echo "Copyright format update completed!"

# Count updated files for verification  
NEW_COUNT=$(echo "$ALL_COPYRIGHT_FILES" | xargs grep -l "$NEW_PATTERN" 2>/dev/null | wc -l)
echo "Total files with updated copyright range format: $NEW_COUNT"

# Show remaining files with old copyright (should be 0)
REMAINING_COUNT=$(echo "$ALL_COPYRIGHT_FILES" | xargs grep -L "$NEW_PATTERN" 2>/dev/null | wc -l)
echo "Files still with old copyright format: $REMAINING_COUNT"

if [ "$REMAINING_COUNT" -gt 0 ]; then
    echo "Files that still need updating:"
    echo "$ALL_COPYRIGHT_FILES" | xargs grep -L "$NEW_PATTERN" 2>/dev/null
fi