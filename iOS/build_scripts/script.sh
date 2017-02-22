#!/bin/bash

set -e

if [ "${TRAVIS_BRANCH}" == "ios-app" ] ; then 
	xcodebuild -workspace iOS/LTC\ Safety/LTC\ Safety.xcworkspace -scheme LTC\ Safety -sdk iphonesimulator build-for-testing CODE_SIGN_IDENTITY="" CODE_SIGNING_REQUIRED=NO
else if [ "${TRAVIS_BRANCH}" == "ios-app-build" ] ; then 
	xcodebuild \
	-workspace iOS/LTC\ Safety/LTC\ Safety.xcworkspace \
	-scheme LTC\ Safety \
	-sdk iphonesimulator \
	test \
	CODE_SIGN_IDENTITY="" CODE_SIGNING_REQUIRED=NO
	
	#xctool \
	#-workspace iOS/LTC\ Safety/LTC\ Safety.xcworkspace \
	#-scheme LTC\ Safety \
	#-sdk iphonesimulator \
	#run-tests
fi
fi