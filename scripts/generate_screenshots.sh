#!/bin/bash

test_instrumentation_runner='net.skyscanner.backpack.BpkTestRunner'
tests_apk_path='app/build/outputs/apk/androidTest/internal/debug/app-internal-debug-androidTest.apk'
app_apk_path='app/build/outputs/apk/internal/debug/app-internal-debug.apk'
app_package_name='net.skyscanner.backpack'

docs_avd="bpk-droid-docs-avd"

apk=app/build/outputs/apk/base/debug/Go.Android.App-base-debug.apk

function getActiveNetwork() {
	services=$(networksetup -listnetworkserviceorder | grep 'Hardware Port')

	while read line; do
    sname=$(echo $line | awk -F  "(, )|(: )|[)]" '{print $2}')
    sdev=$(echo $line | awk -F  "(, )|(: )|[)]" '{print $4}')
    #echo "Current service: $sname, $sdev, $currentservice"
    if [ -n "$sdev" ]; then
        ifout="$(ifconfig $sdev 2>/dev/null)"
        echo "$ifout" | grep 'status: active' > /dev/null 2>&1
        rc="$?"
        if [ "$rc" -eq 0 ]; then
            currentservice="$sname"
            currentdevice="$sdev"
            currentmac=$(echo "$ifout" | awk '/ether/{print $2}')
        fi
    fi
	done <<< "$(echo "$services")"

	if [ -z "$currentservice" ]; then
			>&2 echo "Could not find any active network. Are you connected to the internet?"
			exit 1
	fi

	ipconfig getifaddr $currentdevice
}

function checkStatus() {
	if [ $? -ne 0 ]; then
		echo "Something wrong :/"
		exit $?
	fi
}

function buildApp() {
	echo "🏢  Building the app..."
	./gradlew :app:assembleInternalDebug app:assembleAndroidTest
	checkStatus
}

function installApp() {
	echo "📥  Installing the app"
	adb install -t -r "$app_apk_path"
	adb install -t -r "$tests_apk_path"
	checkStatus
}

function clearData() {
	echo "😉  Cleaning up"
	adb shell pm clear $app_package_name
	checkStatus
}

function run() {
	echo "📸  Taking screenshots..."
	python ./scripts/screenshot-server.py &
	echo $! > ss-server.pid
	ipAddr=$(getActiveNetwork)
	~/Library/Android/sdk/platform-tools/adb shell am instrument --no-window-animation -w \
		-e class net.skyscanner.backpack.docs.GenerateScreenshots \
		-e screenshotServer $ipAddr \
		net.skyscanner.backpack.test/$test_instrumentation_runner

	cat ss-server.pid | xargs kill -9 2> /dev/null
	checkStatus
}

function waitUntil() {
	echo "🕙  Waiting $1 seconds for $2"
	sleep $1
}

function section() {
	echo ""
	echo "🎬  $1"
}

function start() {
	if [ `ps aux | grep qemu | wc -l` -gt 1 ]; then
		adb reconnect > /dev/null
		waitUntil 2 "the emulator to reconnect"
	else
		echo "emulator not started, please start 'bpk-droid-docs-avd'"
		exit 1
	fi
	installApp
	clearData
	waitUntil 2 "the device after the cleanup"
}

function finish() {
	echo "✅  All done. Please review the new screenshots"
}

function generate() {
	locale=$1

	echo ""
	echo "-----------------------"
	echo "🚩  Taking screenshots "
	echo "-----------------------"
	echo ""

	start
	run
	finish
}

buildApp
generate
