#!/bin/bash

if [ "${TRAVIS_BRANCH}" == "android-app" ] ; then 
	git push origin android-app:android-app-build
fi