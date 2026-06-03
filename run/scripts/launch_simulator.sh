#!/bin/bash

# Read arguments arg0=Platform arg1=thread count arg3=list of simulator uuid
args=($@)
argslength=${#args[@]}

# Command to print arguments passed to script
# echo "$@"

PLATFORM_NAME=${args[0]}
SIMULATORS_TO_BOOT=${args[1]}

UDID_LIST_ARGS=${args[@]:2:argslength}
UDID_ARRAY=(${UDID_LIST_ARGS[@]})
UDID_ARRAY_LENGTH=${#UDID_ARRAY[@]}

echo "Platform: $PLATFORM_NAME"
echo "Simulators to boot: $SIMULATORS_TO_BOOT"
# echo "UDID Argument list: $UDID_LIST_ARGS"

# shellcheck disable=SC2145
echo "Simulator UDID array: ${UDID_ARRAY[@]}"
echo "UDID array length: $UDID_ARRAY_LENGTH"

# OPTIONAL - Assign list of values to array using delimiter
# shellcheck disable=SC2068
# read -a myarray <<< ${args[@]}
# IFS=" " read -a SIMULATOR_UDID_ARRAY <<< $UDID_LIST_ARGS

# shellcheck disable=SC2145
# echo "Simulator UDID array: ${SIMULATOR_UDID_ARRAY[@]}"

# Launch simulator application
open -a Simulator.app &

# Use for loop to read all values and indexes
for ((i = 0; i < $SIMULATORS_TO_BOOT; i++)); do
    echo "index: $i, value: ${UDID_ARRAY[$i]}"
    xcrun simctl boot ${UDID_ARRAY[$i]} &
    sleep 2
done

sleep 20