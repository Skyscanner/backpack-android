#!/bin/bash

ls -la app/build/outputs/apk/release/app-release-unsigned.apk
# curl -F ipa=@app/build/outputs/apk/release/app-release-unsigned.apk -H X-HockeyAppToken:43da894438f64ef08f5d35e5d037a379 https://rink.hockeyapp.net/api/2/apps/upload
