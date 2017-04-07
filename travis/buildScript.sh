#!/bin/bash

if [ "${TRAVIS_OS_NAME}" == "osx" ] ; then

	if [ "${TRAVIS_LANGUAGE}" == "objective-c" ] ; then
		# iOS app
		chmod +x iOS/build_scripts/script.sh
		./iOS/build_scripts/script.sh
		
	elif [ "${TRAVIS_LANGUAGE}" == "java" ] ; then
		# Maven - Backend
		mvn clean package -f LTCSafety/pom.xml
		# All tests
		#mvn test -f LTCSafetySystemTesting/pom.xml
		# All tests except automated selenium tests (for website)
		mvn test -f LTCSafetySystemTesting/pom.xml -Dgroups="com.cs371group2.system.AdminAPISystemTests, com.cs371group2.system.ClientAPISystemTests"
	fi
	
elif [ "${TRAVIS_OS_NAME}" == "linux"  ] ; then

	if [ "${TRAVIS_LANGUAGE}" == "android" ] ; then
		# Android app
		chmod +x Android/build_scripts/beforeScript.sh
		chmod +x Android/build_scripts/script.sh
		./Android/build_scripts/beforeScript.sh
		./Android/build_scripts/script.sh
	fi	
	
fi