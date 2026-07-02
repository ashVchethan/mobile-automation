package api;

import constants.AppConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class ContactApi {
    private static final Logger LOGGER = LogManager.getLogger(ContactApi.class);

    public static String createContact(String firstName) throws Exception {
        JSONObject attributionSource = new JSONObject();
        attributionSource.put("sessionSource", "CRM UI");
        attributionSource.put("medium", "manual");
        attributionSource.put("mediumId", null);

        JSONObject internalSource = new JSONObject();
        internalSource.put("type", "manual_addition");
        internalSource.put("id", AppConstants.USER_ID);
        internalSource.put("userName", AppConstants.USER_NAME);

        JSONObject requestBody = new JSONObject();
        requestBody.put("customFields", new JSONArray());
        requestBody.put("firstName", firstName);
        requestBody.put("attributionSource", attributionSource);
        requestBody.put("internalSource", internalSource);
        requestBody.put("locationId", AppConstants.LOCATION_ID);

        JSONObject response = ApiClient.post(AppConstants.CONTACT_API_URL, requestBody.toJSONString(), false);
        JSONObject contact = (JSONObject) response.get("contact");
        if (contact == null) {
            throw new RuntimeException("Create contact API failed. Response: " + response.toJSONString());
        }
        String contactId = (String) contact.get("id");
        LOGGER.info("Created contact '{}' with ID: {}", firstName, contactId);
        return contactId;
    }
}