package utilities;

import constants.PathConstants;

import io.appium.java_client.AppiumDriver;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CommonUtils {
    private static final String scriptsDirectoryPath = PathConstants.GRID_SCRIPT_PATH;

    private static final Logger LOGGER = LogManager.getLogger(CommonUtils.class.getName());

    public static Properties ReadFile(String path) throws IOException {
        File configFile = new File(path);
        FileReader reader = new FileReader(configFile);
        Properties props = new Properties();
        props.load(reader);
        return props;
    }

    /**
     * @param driver
     * @param path
     * @param screenshotName
     * @return
     */
    public static String takeScreenshotAndSave(
            AppiumDriver driver, String path, String screenshotName) throws Exception {
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destinationPath = path + "/" + screenshotName + ".png";
        File destination = new File(destinationPath);
        FileUtils.copyFile(source, destination);
        LOGGER.info("Screenshot path " + destinationPath);
        return destinationPath;
    }

    /**
     * Gets current system time in seconds
     *
     * @return
     */
    public static long getSystemTimeInSeconds() {
        long launchTime = System.currentTimeMillis();
        launchTime = TimeUnit.MILLISECONDS.toSeconds(launchTime);
        return launchTime;
    }

    /**
     * Method to return local time stamp
     *
     * @return time
     */
    public static String getCurrentTime() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String getTime = df.format(date);
        return getTime;
    }

    /**
     * Method takes shell file with arguments to execute as processes
     *
     * @param shellFileWithArgs
     * @throws IOException
     * @throws InterruptedException
     */
    public static void executeShellFile(String shellFileWithArgs)
            throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("/bin/bash", "-c", scriptsDirectoryPath + shellFileWithArgs);
        Process result = builder.start();
        int exitCode = result.waitFor();
        if (exitCode != 0) {
            LOGGER.info("Error occurred while executing shell");
            throw new RuntimeException();
        }
        BufferedReader stdInput =
                new BufferedReader(new InputStreamReader(result.getInputStream()));
        String output;
        while ((output = stdInput.readLine()) != null) {
            LOGGER.info(output);
        }
        if (stdInput != null) stdInput.close();
        if (result != null) result.destroy();
    }

    /**
     * Executes any shell command passed as an argument
     *
     * @param shellCommand
     * @throws IOException
     * @throws InterruptedException
     */
    public static void executeShellCommand(String shellCommand)
            throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("/bin/bash", "-c", shellCommand);
        Process result = builder.start();
        int exitCode = result.waitFor();
        if (exitCode != 0) {
            LOGGER.info("Error occurred while executing shell");
            throw new RuntimeException();
        }
        BufferedReader stdInput =
                new BufferedReader(new InputStreamReader(result.getInputStream()));
        String output;
        while ((output = stdInput.readLine()) != null) {
            LOGGER.info(output);
        }
        stdInput.close();
        if (result != null) result.destroy();
    }

    public static List<String> runTerminalProcess(String command) {
        List<String> processOp = new ArrayList<>();
        File file = new File(System.getProperty("user.dir") + "/output.txt");
        Process process;
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.redirectOutput(file);
        processBuilder.redirectError(file);
        processBuilder.command("sh", "-c", command);
        try {
            process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                processOp.add(line);
                output.append(line + "\n");
            }
            process.waitFor(25, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(System.getProperty("user.dir") + "/output.txt"));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                processOp.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processOp;
    }

    public static void executePermissionToScripts() throws IOException, InterruptedException {
        String shellCommand = "chmod -R 777 " + scriptsDirectoryPath;
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("/bin/bash", "-c", shellCommand);
        Process result = builder.start();
        int exitCode = result.waitFor();
        if (exitCode == 0) {
            LOGGER.info("Executed shell: " + shellCommand);
        } else {
            LOGGER.info("Error occurred while executing shell");
            throw new RuntimeException();
        }
        BufferedReader stdInput =
                new BufferedReader(new InputStreamReader(result.getInputStream()));
        String output;
        while ((output = stdInput.readLine()) != null) {
            LOGGER.info(output);
        }
        stdInput.close();
        if (result != null) result.destroy();
    }

    /**
     * This method is to sleep the UI thread(UIAutomator or XCUITest) for 'x' seconds because
     * Thread.sleep sleeps Appium Server thread and there could be a chance that multiple
     * Thread.sleep could cause server to not come back up at the correct time thereby failing the
     * test case each time
     *
     * @param seconds
     */
    public static void pauseInSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean reportOutput(boolean success, String successMsg, String failureMsg) {
        if (success) LoggerUtils.ReportLog(LoggerUtils.LogsType.INFO, successMsg);
        else LoggerUtils.ReportLog(LoggerUtils.LogsType.INFO, failureMsg);
        return success;
    }

    /**
     * Revokes a specified Android app's permission using the adb shell command.
     *
     * @param packageName package name of the Android app.
     * @param permissionName name of the permission to be revoked.
     * @throws IOException If an I/O error occurs while executing the command.
     */
    public static void revokeAndroidAppPermission(String packageName, String permissionName) {
        String revokeAppPermissionCommand =
                "adb shell pm revoke " + packageName + " android.permission." + permissionName;
        try {
            Runtime.getRuntime().exec(revokeAppPermissionCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Grants a specified Android app a permission using the adb shell command.
     *
     * @param packageName The package name of the Android app.
     * @param permissionName The name of the permission to be granted.
     * @throws IOException If an I/O error occurs while executing the command.
     */
    public static void grantAndroidAppPermission(String packageName, String permissionName) {
        String grantAppPermissionCommand =
                "adb shell pm grant " + packageName + " android.permission." + permissionName;
        try {
            Runtime.getRuntime().exec(grantAppPermissionCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is for implicit wait
     *
     * @param milliseconds
     * @throws InterruptedException
     */
    public static void implicitWait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
