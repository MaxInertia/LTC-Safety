#!/bin/bash

set -e

xcodebuild \
-quiet \
-workspace iOS/LTC\ Safety/LTC\ Safety.xcworkspace \
-scheme LTC\ Safety \
-destination 'platform=iOS Simulator,name=iPhone 6,OS=9.3' \
test
