#!/bin/bash

set -e

if [ "${TRAVIS_BRANCH}" == "ios-app" ] ; then 
	xcodebuild -workspace iOS/LTC\ Safety/LTC\ Safety.xcworkspace -scheme LTC\ Safety build-for-testing 
else if [ "${TRAVIS_BRANCH}" == "ios-app-build" ] ; then 
	xcodebuild -workspace iOS/LTC\ Safety/LTC\ Safety.xcworkspace -scheme LTC\ Safety build-for-testing
	xctool -workspace iOS/LTC\ Safety/LTC\ Safety.xcworkspace -scheme LTC\ Safety -sdk iphonesimulator test
fi
