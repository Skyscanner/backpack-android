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
const replace = require('replace-in-file');
const releaseit = require('release-it');

const versions = require('./versions.json');

const gradleFiles = [`${__dirname}/build.gradle`];
const readmeFile = [`${__dirname}/README.md`];
const versionsFile =  [`${__dirname}/versions.json`]

const compose = "compose"
const android = "android"

const package_questions = [
    {
      type: 'list',
      name: 'type',
      message: 'Which type do you want to release?',
      choices: [
        android,
        compose,
      ],
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

async function amendReadmeFiles(key, version) {
  const options = {
    files: readmeFile,
    from: new RegExp(`${key}:([0-9]+\\.[0-9]+\\.[0-9]+)`, 'g'),
    to: `${key}:${version}`,
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

async function versionPrompt(currentVersion) {
  const major = semver.inc(currentVersion, 'major');
  const minor = semver.inc(currentVersion, 'minor');
  const patch = semver.inc(currentVersion, 'patch');

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
  return await inquirer.prompt(questions);
}

async function release() {
  try {
    childProcess.execSync('./gradlew :Backpack:checkMavenCredentials');
    const packageResult = await inquirer.prompt(package_questions);
    const packageType = packageResult.type
    const packageVersion = (packageType == android) ? versions.android : versions.compose
    const { version } = await versionPrompt(packageVersion);
    const commonVersion = semver.inc(versions.common, 'minor')

    if (packageType == android) {
      await amendGradleFiles('BpkAndroidVersion', version);
      await amendVersionsFile('android', version);
    } else {
      await amendGradleFiles('BpkComposeVersion', version);
      await amendVersionsFile('compose', version);
    }
    await amendGradleFiles('BpkCommonVersion', commonVersion);
    await amendVersionsFile('common', commonVersion);

    if (packageType == android) {
      await amendReadmeFiles('backpack-android', version);
    } else {
      await amendReadmeFiles('backpack-compose', version);
    }

    const publishModule = (packageType == android) ? 'Backpack' : 'backpack-compose'
    const tagName = `${publishModule}@${version}`

    const releaseOptions = {
      increment: version,
      requireCleanWorkingDir: false,
      npm: false,
      git: {
        tagName: tagName,
        requireCleanWorkingDir: false,
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
    childProcess.execSync(`./gradlew :backpack-common:${publishTask} :${publishModule}:${publishTask} ${releaseTask}`);
  } catch (exc) {
    console.error(exc);
    process.exit(1);
  }
}

release();
