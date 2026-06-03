package capabilities;

import constants.PathConstants;

import global.AppCommons;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.options.BaseOptions;

import java.util.Map;

public class CapabilitiesManager {
    public BaseOptions<?> getCapabilities(
            String platform, Map<String, Object>... dynamicCapabilities) {
        Map<String, Object> selectedCapabilities =
                new CapabilitiesLoader().getCapabilities(platform);
        BaseOptions<?> options;
        switch (platform.toLowerCase()) {
            case "android":
                options = configureAndroidOptions(selectedCapabilities);
                break;
            case "ios":
                options = configureIosOptions(selectedCapabilities, platform);
                break;
            default:
                throw new IllegalArgumentException("Invalid platform: " + platform);
        }
        if (dynamicCapabilities != null && dynamicCapabilities.length > 0) {
            setDynamicCapabilities(options, dynamicCapabilities);
        }
        return options;
    }

    private UiAutomator2Options configureAndroidOptions(Map<String, Object> selectedCapabilities) {
        UiAutomator2Options options = new UiAutomator2Options();
        String androidBuild = PathConstants.ANDROID_APP;
        String appPackage = AppCommons.getAppPkg("android");
        setCommonCapabilities(options, selectedCapabilities);
        options.setCapability("app", androidBuild);
        options.setCapability("appPackage", appPackage);
        return options;
    }

    private XCUITestOptions configureIosOptions(
            Map<String, Object> selectedCapabilities, String platform) {
        XCUITestOptions options = new XCUITestOptions();
        String iosBuild = PathConstants.IOS_APP;
        setCommonCapabilities(options, selectedCapabilities);
        options.setCapability("app", iosBuild);
        return options;
    }

    private void setCommonCapabilities(
            BaseOptions<?> options, Map<String, Object> selectedCapabilities) {
        selectedCapabilities.forEach(options::setCapability);
    }

    @SafeVarargs
    private final void setDynamicCapabilities(
            BaseOptions<?> options, Map<String, Object>... dynamicCapabilities) {
        for (Map<String, Object> capabilitiesMap : dynamicCapabilities) {
            capabilitiesMap.forEach(options::setCapability);
        }
    }
}
