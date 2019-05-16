#!/bin/bash

set -e

if [ "$TEST_METHOD" == "screenshot" ]; then

  bucket_name="test-lab-8yj19qy8ht4mm-ku5ff32sak89k" # TODO: create a `backpack-android` bucket
  date_str=$(date '+%Y-%m-%d_%H:%M:%S')
  dir_name=""$date_str".$$"

  remote_screenshots_folder=""$bucket_name"/"$dir_name"/Nexus4-22-en-portrait/artifacts/sdcard/screenshots/net.skyscanner.backpack.test/screenshots-default"
  local_screenshots_folder="app/build/test-labs-screenshots"

  gcloud firebase test android run \
      --type instrumentation \
      --app ./app/build/outputs/apk/debug/app-debug.apk \
      --test ./app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk \
      --device model=Nexus4,version=22 \
      --results-dir="$dir_name"


  remote_screenshots_folder=""$bucket_name"/"$dir_name"/Nexus4-22-en-portrait/artifacts/sdcard/screenshots/net.skyscanner.backpack.test/screenshots-default"

  echo "Retrieving screenshots from temporary bucket [$remote_screenshots_folder]"

  if [ -d "$local_screenshots_folder" ]; then
    rm -rf "$local_screenshots_folder"
  fi 

  mkdir -p "$local_screenshots_folder"

  gsutil -m cp -r "gs://$remote_screenshots_folder" "$local_screenshots_folder"

  echo "Verifying screenshots..."

  ./gradlew :app:verifyDebugAndroidTestScreenshotTest

elif [ "$TEST_METHOD" == "connected" ]; then

  gcloud firebase test android run \
      --type instrumentation \
      --app ./app/build/outputs/apk/debug/app-debug.apk \
      --test ./Backpack/build/outputs/apk/androidTest/debug/Backpack-debug-androidTest.apk \
      --device model=Nexus4,version=22
else 

  echo "Env TEST_METHOD not defined"
  exit 1

fi
