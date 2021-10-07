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

const childProcess = require('child_process');

const inquirer = require('inquirer');
const semver = require('semver');
const replace = require('replace-in-file');
const releaseit = require('release-it');

const versions = require('./versions.json');

const major = semver.inc(versions.android, 'major');
const minor = semver.inc(versions.android, 'minor');
const patch = semver.inc(versions.android, 'patch');

const gradleFiles = [`${__dirname}/build.gradle`];
const readmeFile = [`${__dirname}/README.md`];
const versionsFile =  [`${__dirname}/versions.json`]

const questions = [
  {
    type: 'list',
    name: 'version',
    message: 'What version do you want to release?',
    choices: [
      {
        major,
      },
      {
        minor,
      },
      {
        patch,
      },
    ].map(i => {
      const key = Object.keys(i)[0];
      return {
        key,
        name: `${key} (${i[key]})`,
        value: `${i[key]}`,
      };
    }),
  },
];

async function amendGradleFiles(key, version) {
  const options = {
    files: gradleFiles,
    from: new RegExp(key + ' = .+', 'g'),
    to: `${key} = '${version}'`,
  };

  try {
    console.log('🎉  Version amended in', await replace(options));
    return true;
  } catch (exc) {
    throw new Error(exc);
  }
}

async function amendReadmeFiles(version) {
  const options = {
    files: readmeFile,
    from: /backpack-android:([0-9]+\.[0-9]+\.[0-9]+)/g,
    to: `backpack-android:${version}`,
  };

  try {
    console.log('🎉  Version amended in', await replace(options));
    return true;
  } catch (exc) {
    throw new Error(exc);
  }
}

async function amendVersionsFile(key, version) {
  const options = {
    files: versionsFile,
    from: new RegExp('"' + key + '": ".+"', 'g'),
    to: `"${key}": "${version}"`,
  };

  try {
    console.log('🎉  Version amended in', await replace(options));
    return true;
  } catch (exc) {
    throw new Error(exc);
  }
}

async function release() {
  try {
    childProcess.execSync('./gradlew :Backpack:checkMavenCredentials');
    const { version } = await inquirer.prompt(questions);
    const commonVersion = semver.inc(versions.common, 'minor')

    await amendGradleFiles('BpkAndroidVersion', version);
    await amendGradleFiles('BpkCommonVersion', commonVersion);
    await amendVersionsFile('android', version);
    await amendVersionsFile('common', commonVersion);
    await amendReadmeFiles(version);

    const releaseOptions = {
      increment: version,
      requireCleanWorkingDir: false,
      npm: {
        publish: false,
      },
      git: {
        requireCleanWorkingDir: false,
      },
      prompt: {
        src: {
          release: true,
        },
      },
    };
    await releaseit(releaseOptions);
    const publishTask = ':Backpack:publishMavenPublicationToSonatypeRepository';
    const releaseTask = 'closeAndReleaseSonatypeStagingRepository';
    childProcess.execSync(`./gradlew ${publishTask} ${releaseTask}`);
  } catch (exc) {
    console.error(exc);
    process.exit(1);
  }
}

release();
