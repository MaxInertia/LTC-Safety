#!/bin/bash

# Move to project directory, allow execution of gradle wrapper
cd Android/LTC-Safety && chmod +x gradlew

# Run all JVM and Instrumented Unit Tests
android list target && ./gradlew --continue test connectedAndroidTest
	