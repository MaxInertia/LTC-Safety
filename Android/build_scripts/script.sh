#!/bin/bash

# Move to project directory, allow execution of gradle wrapper
cd Android/LTC-Safety && chmod +x gradlew

if [ "${TRAVIS_BRANCH}" == "android-app" ] ; then 
	# Build, run no Tests
	./gradlew build -x test 
else 
	if [ "${TRAVIS_BRANCH}" == "android-app-build" ] ; then 
		# Run all JVM and Instrumented Unit Tests
		android list target && ./gradlew --continue test connectedAndroidTest
	fi
fi