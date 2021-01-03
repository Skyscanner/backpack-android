/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

const { execSync } = require('child_process');

const fetch = require('node-fetch');

function getVersion() {
  return execSync('git describe')
    .toString()
    .trim();
}

function triggerJitPackBuild(version) {
  const url = `https://jitpack.io/com/github/Skyscanner/backpack-android/${version}/build.log`;
  return fetch(url).then(response => {
    const { status } = response;
    if (status / 100 === 4 || status / 100 === 5) {
      throw new Error(`Unable to trigger JitPack build, response.code=${status}`);
    }
    console.log('JitPack build triggered!');
  });
}

async function postrelease() {
  try {
    const version = getVersion();
    await triggerJitPackBuild(version);
  } catch (exc) {
    console.error('Failed to trigger JitPack build', exc);
  }
}

postrelease();
