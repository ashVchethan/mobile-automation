package constants;

public class AppConstants {
    public static final String ANDROID_APP_PKG_QA = "com.androidapp.dev";
    public static final String ANDROID_APP_PKG_PROD = "com.androidapp";

    public static final String IOS_BUNDLE_ID_QA = "com.ios.dev";
    public static final String IOS_BUNDLE_ID_PROD = "com.ios";

    // API endpoints
    public static final String CONTACT_API_URL = "https://backend.leadconnectorhq.com/contacts/";
    public static final String OPPORTUNITY_API_URL = "https://services.leadconnectorhq.com/opportunities/";
    public static final String API_VERSION = "2021-07-28";

    // Pass via -Dapi.token=<token> at runtime (token expires hourly)
    public static final String API_TOKEN = System.getProperty("api.token", "");

    // GoHighLevel environment identifiers
    public static final String LOCATION_ID = "70gv3VUrzOCIk577VcXP";
    public static final String USER_ID = "tkgswVqIasjwM4Ub5amo";
    public static final String USER_NAME = "Candidate test";

    // Opportunity pipeline config
    public static final String PIPELINE_ID = "8kvuePHJl8lpmQ5z7hki";
    public static final String PIPELINE_STAGE_ID = "aa08e706-4b54-4d51-899b-221d556884b3";
    public static final String ASSIGNED_TO = "VQrsb6bc649UjcbH1sG1";

    // Test data
    public static final String CONTACT_NAME = "ashwi4";

    // Deeplinks
    public static final String OPPORTUNITIES_DEEPLINK =
            "https://app.gohighlevel.com/v2/location/70gv3VUrzOCIk577VcXP/opportunities";

    public static final String kotaklink =
            "https://www.kotak.bank.in/en/personal-banking/cards.html";
}
