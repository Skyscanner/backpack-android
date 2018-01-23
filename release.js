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

'use strict';
const inquirer = require('inquirer');
const pkg = require("./package.json");
const semver = require("semver");
const replace = require('replace-in-file');
const releaseit = require("release-it");

const major = semver.inc(pkg.version, "major");
const minor = semver.inc(pkg.version, "minor");
const patch = semver.inc(pkg.version, "patch");

const gradleFiles = [
    `${__dirname}/Backpack/build.gradle`
]

const questions = [{
    type: 'list',
    name: 'version',
    message: "What version do you want to release?",
    choices: [{
        major
    }, {
        minor
    }, {
        patch
    }].map(i => {
        let key = Object.keys(i)[0];
        return {
            key: key,
            name: `${key} (${i[key]})`,
            value: `${i[key]}`
        }
    })
}];

release();

async function release() {
    const {
        version
    } = await inquirer.prompt(questions);
    await amendGradleFiles(version);

    const releaseOptions = {
        increment: version,
        npm: {
            publish: false
        },
        github: {
            release: false
        },
        "prompt": {
            "src": {
                "release": false,
            }
        },
        src: {
            afterReleaseCommand: "./gradlew bintrayUpload"
        }
    }
    releaseit(releaseOptions);
}

async function amendGradleFiles(version) {
    const options = {
        files: gradleFiles,
        from: /version = '.+'/g,
        to: `version = '${version}'`,
    };

    try {
        console.log("Version amended in", await replace(options));
        return true;
    } catch (exc) {
        throw new Error(exc);
    }
}
