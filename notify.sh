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

comment="Thank you for your contribution.\nYou can use the following dependency to test your application while we review your pull request:\n\n\`com.github.${2%/*}:backpack-android:${4}\`"
curl -H "Authorization: token $1" -X POST -d "{\"body\":\" $comment \"}" https://api.github.com/repos/$2/issues/$3/comments
