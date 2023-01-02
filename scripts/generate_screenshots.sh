#!/bin/bash
# Backpack for Android - Skyscanner's Design System
#
# Copyright 2018 Skyscanner Ltd
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

function generate() {
	locale=$1

	echo ""
	echo "-----------------------"
	echo "🚩  Taking screenshots "
	echo "-----------------------"
	echo ""

	node ./scripts/screenshot-server.js &
	echo $! > ss-server.pid

	echo "📸  Taking screenshots..."
  ./gradlew :app:PixelScreenshotsDebugAndroidTest

	cat ss-server.pid | xargs kill -9 2> /dev/null

	echo "✅  All done. Please review the new screenshots"
}

generate
