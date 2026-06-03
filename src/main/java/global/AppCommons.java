package global;

import constants.AppConstants;
import constants.PathConstants;

public class AppCommons {
    public static String getAppPkg(String platform) {
        String appId = null;
        switch (platform) {
            case "ios":
                appId =
                        PathConstants.env.equalsIgnoreCase("qa")
                                ? AppConstants.IOS_BUNDLE_ID_QA
                                : AppConstants.IOS_BUNDLE_ID_PROD;
                break;
            case "android":
                appId =
                        PathConstants.env.equalsIgnoreCase("qa")
                                ? AppConstants.ANDROID_APP_PKG_QA
                                : AppConstants.ANDROID_APP_PKG_PROD;
                break;
        }
        return appId;
    }
}
