#!/bin/bash

if [ "${TRAVIS_BRANCH}" == "android-app" ] ; then 
	cd Android/LTC-Safety && chmod +x gradlew && ./gradlew build -x test
else if [ "${TRAVIS_BRANCH}" == "android-app-build" ] ; then 
	cd Android/LTC-Safety && chmod +x gradlew && android list target &&./gradlew --continue test connectedAndroidTest
fi