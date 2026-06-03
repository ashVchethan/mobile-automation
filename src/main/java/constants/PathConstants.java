package constants;

public class PathConstants {
    public static final String APP_PATH =
            System.getProperty("user.dir") + "/src/test/resources/app/";
    public static final String GRID_SCRIPT_PATH =
            System.getProperty("user.dir").replace(" ", "\\ ") + "/run/scripts/";
    public static String env = System.getProperty("env", "qa");
    public static String PLATFORM = System.getProperty("platform", "android");
    public static String ANDROID_APP =
            APP_PATH + System.getProperty("apkName", "test-qa.apk");
    public static String IOS_APP = APP_PATH + System.getProperty("ipaName", "test-qa.ipa");
    public static final String CAPABILITIES_FILE_PATH =
            System.getProperty("user.dir") + "/src/test/resources/capabilities.yml";
}
