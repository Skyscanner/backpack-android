# Contributing

In this document we describe how to setup this repository for development and the release process.

## Environment

We use Node in this project. To manage the language runtime we recommend using [`nvm`][1] respectively. The required Node version is in `.nvmrc`.

With `nvm` use `nvm use` to set the correct node version in your machine.

## Setup

Given that you have a compatible environment as stated above you can now setup the project.

+ `npm install` to install npm dependencies
+  open the project in andorid studio

## Releasing

> Backpack team only

To issue a new release make sure you've set the project up as above, that you have push acess to the Backpack Bintray repo and the `BINTRAY_USER` and `BINTRAY_USER` env vars are set

Run `npm run release` and follow the process through, you'll be asked which semantic version the release

[1]: https://github.com/creationix/nvm
