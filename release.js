'use strict';
const inquirer = require('inquirer');
const pkg = require("./package.json");
const semver = require("semver");
const replace = require('replace-in-file');
const gradleFiles = [
    `${__dirname}/Backpack/build.gradle`
]
const releaseit = require("release-it");
const major = semver.inc(pkg.version, "major");
const minor = semver.inc(pkg.version, "minor");
const patch = semver.inc(pkg.version, "patch");

const questions = [
    {
        type: 'list',
        name: 'version',
        message: "What version do you want to release?",
        choices: [{ major }, { minor }, { patch }].map(i => {
            let key = Object.keys(i)[0];
            return {
                key: key,
                name: `${key} (${i[key]})`,
                value: `${i[key]}`
            }
        })
    }
];

release();

async function release() {
    const { version } = await inquirer.prompt(questions);
    await amendGradleFiles(version);
    const releaseOptions = {
        increment: version,
        npm: {
            publish: false
        },
        github: {
            release: false
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
