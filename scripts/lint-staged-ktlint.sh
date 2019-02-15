#!/bin/bash

# Transforms the list of files provided by lint-staged into a format ktlint understands

ALL_FILES=$*

./gradlew ktlint -Pfiles="$ALL_FILES"