#!/bin/sh

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
