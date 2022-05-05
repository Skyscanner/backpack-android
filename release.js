/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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

const childProcess = require('child_process');

const inquirer = require('inquirer');
const semver = require('semver');
const releaseit = require('release-it');


async function versionPrompt() {

  const question =
    {
      type: 'input',
      name: 'version',
      message: 'What version do you want to release? (see https://github.com/Skyscanner/backpack-android/releases)',
      validate: async (input) => { return semver.valid(input) == input; }
    };
  return await inquirer.prompt(question);
}

async function release() {
  try {
    childProcess.execSync('./gradlew :Backpack:checkMavenCredentials');
    const { version } = await versionPrompt();

    const releaseOptions = {
      increment: version,
      requireCleanWorkingDir: true,
      npm: false,
      git: {
      commit: false,
        tagName: version,
        requireCleanWorkingDir: true,
      },
      prompt: {
        src: {
          release: true,
        },
      },
    };
    await releaseit(releaseOptions);
    const publishTask = 'publishMavenPublicationToSonatypeRepository';
    const releaseTask = 'closeAndReleaseSonatypeStagingRepository';
    childProcess.execSync(`./gradlew ${publishTask} ${releaseTask} -Pversion=${version}`);
  } catch (exc) {
    console.error(exc);
    process.exit(1);
  }
}

release();
