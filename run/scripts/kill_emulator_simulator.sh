#!/bin/bash
adb devices | grep emulator | cut -f1 | while read line; do adb -s $line emu kill; done
sleep 2
xcrun simctl shutdown all &
sleep 1