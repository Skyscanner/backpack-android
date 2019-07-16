#!/bin/sh

# Compile native-bridges with the latest version of backpack to make 
# sure it's all working

tmp_dir=$(mktemp -d -t backpack-android-XXXXXXXXXX)
bpk_version=$(grep "BpkVersion =" ./build.gradle | awk -F " = " '{print $2}')


echo "\nWorking dir: ${tmp_dir}\n"

cd "${tmp_dir}"
git clone --single-branch https://github.com/Skyscanner/backpack-react-native

cd backpack-react-native

# Only react native is required to compile de Android code
rn_version=$(node -p -e "require('./package.json').devDependencies['react-native']")
npm install "react-native@$rn_version"

all_bridges=$(find packages -name "build.gradle" | grep -v node_modules | awk -F "/" '{print $2}')

# TODO: First we need to add tests to the native bridges in order to simulate running the code using
# precompiled versions of code agains the new backpack-android version and check if it still works.

# First build the bridges with the current version

# build_all_command=""
# for bridge in $all_bridges; do
#   build_all_command="$build_all_command:$bridge:assembleRelease "
# done

# cd android

# echo "\nRunning ./gradlew "$build_all_command"\n"
# eval "./gradlew $build_all_command"

# cd ..

# Replace the dependency in the app project to use the pre compiled bridges and 
# then run tests using the new version of backpack-android

# # Replace dependencies to simulate running the project with pre compiled versions
# for bridge in $all_bridges; do
#   file="../../packages/${bridge}/src/android/build/outputs/aar/${bridge}-release.aar"
#   sed -i "s/project(':${bridge}')/files('${file}')/g" android/app/build.gradle
# done

compile_command=""

for bridge in $all_bridges; do
  compile_command="$compile_command:$bridge:compileDebugKotlin :$bridge:compileDebugJava "
done

cd android

echo "\nRunning ./gradlew -Pbackpack-version=+ "$compile_command"\n"

# Compile bridges using the latest version of backpack-android
eval "./gradlew -Pbackpack-version=+ $compile_command"

if [ $? -ne 0 ]; then
  # From here on any failure should break the build
  set -e

  echo "\nCompilation failed, adding new issue...\n"

  issue_title="Version $bpk_version of backpack-android is failing to compile with native bridges"
  issue_body="Version $bpk_version of backpack-android failed to compile with latest bridges, check out this build for for information $TRAVIS_BUILD_WEB_URL"
  
  curl -f -H "Authorization: token $BACKPACK_BOT_COMMENT_TOKEN" -X POST -d "{\"body\":\"$issue_body\", \"title\":\"$issue_title\"}" https://api.github.com/repos/Skyscanner/backpack-react-native/issues > /dev/null
else
  echo "\nAll good!\n"
fi