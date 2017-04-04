#!/bin/bash

set -e

if [ "${TRAVIS_BRANCH}" == "ios-app" ] ; then 
	xcodebuild \
	-workspace iOS/LTC\ Safety/LTC\ Safety.xcworkspace \
	-scheme LTC\ Safety \
	-sdk iphonesimulator \
	build \
	CODE_SIGN_IDENTITY="" CODE_SIGNING_REQUIRED=NO
	
else if [ "${TRAVIS_BRANCH}" == "ios-app-build" ] ; then 
	xcodebuild \
	-workspace iOS/LTC\ Safety/LTC\ Safety.xcworkspace \
	-scheme LTC\ Safety \
	-destination 'platform=iOS Simulator, id=DB794781-65A7-4884-8D00-AAC3CBD39A44, OS=10.2, name=iPhone SE' \
	test

	#xcodebuild \
	#-workspace iOS/LTC\ Safety/LTC\ Safety.xcworkspace \
	#-scheme LTC\ Safety \
	#-sdk iphonesimulator \
	#build-for-testing
	#CODE_SIGN_IDENTITY="" CODE_SIGNING_REQUIRED=NO
	
	#xctool \
	#-workspace iOS/LTC\ Safety/LTC\ Safety.xcworkspace \
	#-scheme LTC\ Safety \
	#-sdk iphonesimulator \
	#run-tests
fi
fi