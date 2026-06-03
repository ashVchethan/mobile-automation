#!/usr/bin/env bash

# Initialize an empty array
deviceArray=()
DEVICE_COUNT="$(adb devices | grep -wc "device")"

# Get the list of devices
devices=$(adb devices | grep 'devices$' | grep -v 'List' | awk '{print $1}')

# Loop through the list and add each device to the array
for device in $devices
do
    deviceArray+=("$device")
done

index=0

while [ $index -le $DEVICE_COUNT ]; do
    udid=${deviceArray[index]}
    echo $udid
    echo "Uninstalling uiautomator2 server app"
    adb -s $udid uninstall io.appium.uiautomator2.server
    adb -s $udid uninstall io.appium.uiautomator2.server.test
    ((index += 1))
done

sleep 5