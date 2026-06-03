#! /bin/bash
# (@) start-android
# If the emulator command exists on this device, displays a list of emulators
# and prompts the user to start one

platformName=$1
DEVICE_INCREMENT=1

# Check if the emulator command exists first
if ! type emulator > /dev/null; then
  echo "emulator command not found"
  exit 1
fi

# Gather emulators that exist on this computer
DEVICES=( $(emulator -list-avds 2>&1 ) )

#To be fixed later
if [[ "${DEVICES[0]}" == *"INFO"* ]]; then
    # Remove the first element from the array
    DEVICES=("${DEVICES[@]:12}")
fi


# Display list of emulators
echo "Available Emulators
----------------------------------------"
EMULATORS_AVAILABLE=0
for DEVICE in "${DEVICES[@]}"
do
  echo "$DEVICE_INCREMENT) $DEVICE"
  let EMULATORS_AVAILABLE=$EMULATORS_AVAILABLE+1
  let DEVICE_INCREMENT=$DEVICE_INCREMENT+1
done

THREAD_COUNT=$2

if [ $THREAD_COUNT -ge $EMULATORS_AVAILABLE ];
then
  THREAD_COUNT=$EMULATORS_AVAILABLE
fi

# Request an emulator to start
#read -p "
#Choose an emulator: " num

echo "Online emulators: $EMULATORS_AVAILABLE"
echo "Emulators to boot: $THREAD_COUNT"
# If the input is valid, launch our emulator on a separate PID and exit
while [ $THREAD_COUNT -le $EMULATORS_AVAILABLE ] && [ $THREAD_COUNT -gt 0 ]
do
  DEVICE=${DEVICES[$THREAD_COUNT-1]}
  cd ~/Library/Android/sdk/emulator/
  sleep 1
  if ! grep -q "INFO" "$1"; then
      echo "No error while listing emulators"
  else
      echo "Error occurred while listing emulators"
  fi

  ./emulator -avd "$DEVICE" >/dev/null 2>&1 &
  echo "Launched emulator - $DEVICE"
  THREAD_COUNT=$(($THREAD_COUNT - 1))
  sleep 3
done

sleep 30
