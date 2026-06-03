package driver;

import io.appium.java_client.AppiumDriver;

public class DriverManager {
    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public static void setDriver(AppiumDriver appiumDriver) {
        driver.set(appiumDriver);
    }

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
