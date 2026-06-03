package helper;

import constants.PathConstants;

import utilities.CommonUtils;
import utilities.JsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeviceManager {
    private static String platformName;
    private static final String GRID_SCRIPT_PATH = PathConstants.GRID_SCRIPT_PATH;
    private static int emulatorToBoot = 0;
    private static int simulatorToBoot = 0;
    private static int threadCount = 0;
    private static int onlineAndroidDevicesCount, bootedSimulatorCount, connectedIOSDevicesCount;

    public static void launchEmulatorOrSimulator(String platformName, int threadCount)
            throws IOException, InterruptedException {
        DeviceManager.platformName = platformName;
        DeviceManager.threadCount = threadCount;
        String simulatorArray;
        // TODO: Solve for Active devices and Real Devices
        try {
            if (platformName.equals("android")) {
                if (isAndroidDeviceConnected()) {
                    System.out.println(
                            "Android device already connected, skipping emulator launch.");
                } else {
                    emulatorToBoot = DeviceManager.threadCount;
                    if (emulatorToBoot > 0) {
                        CommonUtils.executeShellFile(
                                "launch_emulator.sh\t" + platformName + "\t" + emulatorToBoot);
                    }
                }
            } else if (platformName.equals("ios")) {
                simulatorToBoot = DeviceManager.threadCount;
                if (simulatorToBoot > 0) {
                    CommonUtils.executeShellFile("list_simulator_details.sh");
                    JsonUtils.readJSONFile(GRID_SCRIPT_PATH + "/../temp/simulatorDetails.json");
                    simulatorArray = JsonUtils.getShutdownSimulatorUdidArray();
                    CommonUtils.executeShellFile(
                            "launch_simulator.sh\t"
                                    + platformName
                                    + "\t"
                                    + simulatorToBoot
                                    + "\t"
                                    + simulatorArray);
                }
            } else {
                System.out.println("Invalid platform name");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void shutdownEmulatorOrSimulator() throws IOException, InterruptedException {
        CommonUtils.executeShellFile("kill_emulator_simulator.sh");
    }

    private static boolean isAndroidDeviceConnected() throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec("adb devices");
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("\tdevice")) return true;
        }
        return false;
    }
}
