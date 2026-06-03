package driver;

import capabilities.CapabilitiesManager;

import global.AppCommons;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.options.BaseOptions;

import utilities.LoggerUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class DriverFactory {
    private static final String LOCAL_APPIUM_URL = "http://127.0.0.1:4723/";

    public static AppiumDriver createDriver(
            String platform, Map<String, Object>... dynamicCapabilities) {
        if (DriverManager.getDriver() == null) {
            AppiumDriver driver = initializeDriver(platform, dynamicCapabilities);
            DriverManager.setDriver(driver);
        } else {
            ((InteractsWithApps) DriverManager.getDriver())
                    .terminateApp(AppCommons.getAppPkg(platform));
            ((InteractsWithApps) DriverManager.getDriver())
                    .activateApp(AppCommons.getAppPkg(platform));
        }
        return DriverManager.getDriver();
    }

    public static AppiumDriver initializeDriver(
            String platform, Map<String, Object>... dynamicCapabilities) {
        String remoteUrl = LOCAL_APPIUM_URL;
        AppiumDriver driver = null;
        BaseOptions<?> capabilities =
                new CapabilitiesManager().getCapabilities(platform, dynamicCapabilities);
        try {
            switch (platform.toLowerCase()) {
                case "android":
                    driver = new AndroidDriver(new URL(remoteUrl), capabilities);
                    break;
                case "ios":
                    driver = new IOSDriver(new URL(remoteUrl), capabilities);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid platform: " + platform);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            LoggerUtils.ReportLog(LoggerUtils.LogsType.FAIL, "driver creation failed");
        }
        return driver;
    }
}
