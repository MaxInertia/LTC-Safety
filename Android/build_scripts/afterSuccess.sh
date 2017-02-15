#!/bin/bash

if [ "${TRAVIS_BRANCH}" == "android-app" ] ; then 
	# Tests will be executed post-push by the Travis build triggered.
	git push origin android-app:android-app-build
fi