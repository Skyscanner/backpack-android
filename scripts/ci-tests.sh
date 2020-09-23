#!/bin/bash
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

export CLOUDSDK_PYTHON=$(which python3)
set -e
export CLOUDSDK_PYTHON=$(which python3)
if [ "$TEST_METHOD" == "screenshot" ]; then

  bucket_name="test-lab-8yj19qy8ht4mm-ku5ff32sak89k" # TODO: create a `backpack-android` bucket
  date_str=$(date '+%Y-%m-%d_%H:%M:%S')
  dir_name=""$date_str".$$"

  remote_screenshots_folder=""$bucket_name"/"$dir_name"/Nexus4-21-en-portrait/artifacts/sdcard/screenshots/net.skyscanner.backpack.test/screenshots-default"
  local_screenshots_folder="app/build/test-labs-screenshots"

  gcloud firebase test android run \
      --type instrumentation \
      --app ./app/build/outputs/apk/oss/debug/app-oss-debug.apk \
      --test ./app/build/outputs/apk/androidTest/oss/debug/app-oss-debug-androidTest.apk \
      --device model=Nexus4,version=21 \
      --environment-variables "notClass=net.skyscanner.backpack.docs.GenerateScreenshots" \
      --results-dir="$dir_name"


  remote_screenshots_folder=""$bucket_name"/"$dir_name"/Nexus4-21-en-portrait/artifacts/sdcard/screenshots/net.skyscanner.backpack.test/screenshots-default"

  echo "Retrieving screenshots from temporary bucket [$remote_screenshots_folder]"

  if [ -d "$local_screenshots_folder" ]; then
    rm -rf "$local_screenshots_folder"
  fi

  mkdir -p "$local_screenshots_folder"

  gsutil -m cp -r "gs://$remote_screenshots_folder" "$local_screenshots_folder"

  echo "Verifying screenshots..."

  ./gradlew :app:verifyOssDebugAndroidTestScreenshotTest

elif [ "$TEST_METHOD" == "connected" ]; then

  gcloud firebase test android run \
      --type instrumentation \
      --app ./app/build/outputs/apk/oss/debug/app-oss-debug.apk \
      --test ./Backpack/build/outputs/apk/androidTest/oss/debug/Backpack-oss-debug-androidTest.apk \
      --device model=Nexus4,version=21
else

  echo "Env TEST_METHOD not defined"
  exit 1

fi
