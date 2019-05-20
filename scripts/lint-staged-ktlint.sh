#!/bin/bash

# Transforms the list of files provided by lint-staged into a format ktlint understands

ALL_FILES=$*

./gradlew ktLintFormat -Pfiles="$ALL_FILES"
git add .