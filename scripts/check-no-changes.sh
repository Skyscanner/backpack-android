#!/usr/bin/env bash
if [[ $(git status --porcelain) ]]; then
  >&2 echo "Some files have been changed!"
  >&2 git status --porcelain
  exit -1
else
    echo "No changes detected";
fi
