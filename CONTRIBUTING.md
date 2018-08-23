# Contributing

In this document we describe how to setup this repository for development and the release process.

## Environment

We use Node in this project. To manage the language runtime we recommend using [`nvm`][1] respectively. The required Node version is in `.nvmrc`.

With `nvm` use `nvm use` to set the correct node version in your machine.

## Setup

Given that you have a compatible environment as stated above you can now setup the project.

+ `npm install` to install npm dependencies
+  Open the project in Android studio

## Adding a new component

A [cookiecutter][2] app is a part of the repository. This generates the proper kotlin classes for the component and test cases. To use this, run the following command and follow the process.

`cookiecutter .`

Once the component it generated, add it to the `settings.gradle` file to start using.

## Releasing

> Backpack team only

To issue a new release make sure you've set the project up as above, that you have push acess to the Backpack Bintray repo and the `BINTRAY_USER` and `BINTRAY_KEY` env vars are set

Run `npm run release` and follow the process through, you'll be asked which semantic version the release

[1]: https://github.com/creationix/nvm
