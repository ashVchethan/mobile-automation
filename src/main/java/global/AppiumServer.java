package global;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.IOException;

public class AppiumServer {
    // final static String scriptPath =
    // System.getProperty("user.dir") + "/run/scripts/";

    private static AppiumDriverLocalService service;

    public static void startServer() throws IOException, InterruptedException {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1")
                .usingPort(4723)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
        System.out.println("Appium server started successfully");
        // TODO: Run the server with script
        /*
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(
                "/bin/bash",
                "-c",
                scriptPath + "run_appium.sh"
        );
        Process process = builder.start();
        process.waitFor();
        */
    }

    public static void stopServer() throws IOException, InterruptedException {
        if (service != null) {
            service.stop();
            System.out.println("Appium server terminated successfully");
        }
        // TODO: Terminate the server with script
        /*
        String appiumServerKillCommand =
                "pkill -9 -f appium";
        ProcessBuilder builder =
                new ProcessBuilder();
        builder.command(
                "/bin/bash",
                "-c",
                appiumServerKillCommand
        );
        builder.start();
        */
    }
}
