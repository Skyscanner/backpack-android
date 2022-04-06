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

async function amendGradleFiles(version) {
  const options = {
    files: gradleFiles,
    from: new RegExp('BpkVersion = .+', 'g'),
    to: `BpkVersion = '${version}'`,
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
    from: new RegExp('bpkVersion = ".+"', 'g'),
    to: `bpkVersion = "${version}"`,
  };

  try {
    console.log('🎉  Version amended in', await replace(options));
    return true;
  } catch (exc) {
    throw new Error(exc);
  }
}

async function amendVersionsFile(version) {
  const options = {
    files: versionsFile,
    from: new RegExp('"backpack": ".+"', 'g'),
    to: `"backpack": "${version}"`,
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
    const packageVersion = versions.backpack
    const { version } = await versionPrompt(packageVersion);

    await amendGradleFiles(version);
    await amendVersionsFile(version);
    await amendReadmeFiles(version);

    const releaseOptions = {
      increment: version,
      requireCleanWorkingDir: false,
      npm: false,
      git: {
        tagName: version,
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
    childProcess.execSync(`./gradlew ${publishTask} ${releaseTask}`);
  } catch (exc) {
    console.error(exc);
    process.exit(1);
  }
}

release();
