#!/bin/sh
# Backpack for Android - Skyscanner's Design System
#
# Copyright 2018-2020 Skyscanner Ltd
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


# Trigger a RN build using the latest backpack-android version

set -e

backpack_version=$(grep "BpkVersion =" build.gradle | awk '{ print $4 }' | sed "s/'//g")

body="{
 \"request\": {
    \"message\": \"Test backpack-react-native with backpack-android $backpack_version\",
    \"branch\":\"master\",
    \"config\": {
      \"merge_mode\": \"deep_merge\",
      \"env\": {
        \"jobs\": [
          \"OVERRIDE_BACKPACK_VERSION=$backpack_version\"
        ]
      }
    }
  }
}"

echo "Triggering a backpack-react-native build using backpack-android $backpack_version"
echo $body

curl -s --fail --show-error -X POST \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -H "Travis-API-Version: 3" \
  -H "Authorization: token $TRAVIS_API_KEY" \
  -d "$body" \
  https://api.travis-ci.org/repo/Skyscanner%2Fbackpack-react-native/requests
