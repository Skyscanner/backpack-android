#!/usr/bin/env bash
if [[ $(git status --porcelain) ]]; then
  echo "Some files have been changed";
  git status --porcelain
  exit -1;
else
    echo "No changes detected";
fi
