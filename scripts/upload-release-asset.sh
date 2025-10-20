#!/bin/bash
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

set -e

# Usage: ./upload-release-asset.sh <github_token> <repository> <tag_name> <file_path>

if [ "$#" -ne 4 ]; then
    echo "Usage: $0 <github_token> <repository> <tag_name> <file_path>"
    exit 1
fi

GITHUB_TOKEN=$1
REPOSITORY=$2
TAG_NAME=$3
FILE_PATH=$4
FILE_NAME=$(basename "$FILE_PATH")

echo "Uploading $FILE_NAME to release $TAG_NAME..."

# Get the upload URL for the release
UPLOAD_URL=$(curl -s -H "Authorization: Bearer $GITHUB_TOKEN" \
  "https://api.github.com/repos/$REPOSITORY/releases/tags/$TAG_NAME" \
  | jq -r '.upload_url' | sed 's/{?name,label}//')

if [ -z "$UPLOAD_URL" ] || [ "$UPLOAD_URL" == "null" ]; then
    echo "Error: Could not get upload URL for release $TAG_NAME"
    exit 1
fi

# Upload the asset
curl -X POST \
  -H "Authorization: Bearer $GITHUB_TOKEN" \
  -H "Content-Type: text/plain" \
  --data-binary "@$FILE_PATH" \
  "${UPLOAD_URL}?name=${FILE_NAME}"

echo "Upload complete!"
