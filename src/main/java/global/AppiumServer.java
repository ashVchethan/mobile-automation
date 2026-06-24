package global;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.IOException;

public class AppiumServer {
    private static final int PORT = 4723;

    private static final String DEVICE_FARM_CONFIG_PATH =
            System.getProperty("user.dir") + "/run/config/deviceFarmConfig/serverConfig.json";

    private static AppiumDriverLocalService service;

    public static void startServer() throws IOException, InterruptedException {
        killProcessOnPort(PORT);
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1")
                .usingPort(PORT)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                .withArgument(() -> "--config", DEVICE_FARM_CONFIG_PATH);
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
        System.out.println("Appium server started successfully (device-farm plugin enabled)");
    }

    public static void stopServer() throws IOException, InterruptedException {
        if (service != null) {
            service.stop();
            System.out.println("Appium server terminated successfully");
        }
    }

    private static void killProcessOnPort(int port) throws IOException, InterruptedException {
        ProcessBuilder builder =
                new ProcessBuilder(
                        "/bin/bash", "-c", "lsof -ti tcp:" + port + " | xargs kill -9");
        Process process = builder.start();
        process.waitFor();
    }
}
