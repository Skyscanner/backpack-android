# Contributing

In this document we describe how to setup this repository for development and the release process.

## Environment

We use Node in this project. To manage the language runtime we recommend using [`nvm`][1] respectively. The required Node version is in `.nvmrc`.

With `nvm` use `nvm use` to set the correct node version in your machine.

## Setup

Given that you have a compatible environment as stated above you can now setup the project.

+ `npm install` to install npm dependencies
+  Open the project in Android studio

## Releasing

> Backpack team only

Run `npm run release` and follow the process through, you'll be asked which semantic version the release.Once released verify the artifacts on [`jitpack`][3]

## Docs

Run `npm run docs` to generate API docs. Docs will be generated in the `build/docs` folder by default. To provide a different output folder use `npm run docs -- -PdokkaOutput=/path/to/folder`

Run `npm run docs:clean` to remove generated docs.

[1]: https://github.com/creationix/nvm
[3]: https://jitpack.io/#Skyscanner/backpack-android
