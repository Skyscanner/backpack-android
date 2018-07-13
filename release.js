/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-present Skyscanner Ltd
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

const inquirer = require('inquirer');
const semver = require('semver');
const replace = require('replace-in-file');
const releaseit = require('release-it');
const fetch = require('node-fetch');

const pkg = require('./package.json');

const major = semver.inc(pkg.version, 'major');
const minor = semver.inc(pkg.version, 'minor');
const patch = semver.inc(pkg.version, 'patch');

const gradleFiles = [`${__dirname}/Backpack/build.gradle`];

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

async function amendGradleFiles(version) {
  const options = {
    files: gradleFiles,
    from: /version = '.+'/g,
    to: `version = '${version}'`,
  };

  try {
    console.log('🎉  Version amended in', await replace(options));
    return true;
  } catch (exc) {
    throw new Error(exc);
  }
}
async function isMaintainer() {
  const organisation = 'skyscanner';
  const user = process.env.BINTRAY_USER;
  const key = process.env.BINTRAY_KEY;

  const url = `https://${user}:${key}@bintray.com/api/v1/users/${user}`;
  try {
    console.log('🔎  Checking user permission');
    const response = await fetch(url);
    const status = await response.status;
    const data = await response.json();

    if (status !== 200) {
      throw new Error(
        '😱  Auth check failed, the given user is either not authorised or a server side error occurred',
      );
    }
    if (data.organizations.indexOf(organisation) === -1) {
      throw new Error(
        `😱  The given user is not part of the ${organisation} organisation, permission denied!`,
      );
    }
    console.log(`✅  Hello ${user}, you're authorised for a new release`);
    return true;
  } catch (exc) {
    throw exc;
  }
}
async function release() {
  try {
    await isMaintainer();

    const { version } = await inquirer.prompt(questions);

    await amendGradleFiles(version);

    const releaseOptions = {
      increment: version,
      requireCleanWorkingDir: false,
      npm: {
        publish: false,
      },
      prompt: {
        src: {
          release: true,
        },
      },
      src: {
        afterReleaseCommand: './gradlew bintrayUpload',
      },
    };
    await releaseit(releaseOptions);
  } catch (exc) {
    console.error(exc);
    process.exit(1);
  }
}

release();
