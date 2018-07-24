# Author : Amol Gupta
# Date   : 20th July 2018
# This file creates the emulator based on standard settings.
# Use this emulator for all testing purposes including snapshot tests on CI.

rm  test-sd.img.*
mksdcard -l e 256M test-sd.img
sdkmanager "system-images;android-25;google_apis;armeabi-v7a"
echo no | $ANDROID_HOME/tools/bin/avdmanager create avd -f -n bpk-avd  -k "system-images;android-25;google_apis;armeabi-v7a" &
$ANDROID_HOME/emulator/emulator -avd bpk-avd -sdcard test-sd.img -skin 1080x1920 -wipe-data &
adb shell input keyevent 82 &
