#!/bin/bash
comment="Thank you for your contribution.\nYou can use the following dependency to test your application while we review your pull request:\n\n\`com.github.${2%/*}:backpack-android:${4}\`"
curl -H "Authorization: token $1" -X POST -d "{\"body\":\" $comment \"}" https://api.github.com/repos/$2/issues/$3/comments
