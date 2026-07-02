package api;

import constants.AppConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class OpportunityApi {
    private static final Logger LOGGER = LogManager.getLogger(OpportunityApi.class);

    public static String createOpportunity(String name, String contactId) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", name);
        requestBody.put("pipelineId", AppConstants.PIPELINE_ID);
        requestBody.put("pipelineStageId", AppConstants.PIPELINE_STAGE_ID);
        requestBody.put("status", "open");
        requestBody.put("monetaryValue", 0L);
        requestBody.put("assignedTo", AppConstants.ASSIGNED_TO);
        requestBody.put("source", null);
        requestBody.put("contactId", contactId);
        requestBody.put("indexVersion", 1L);
        requestBody.put("isAttribute", false);
        requestBody.put("locationId", AppConstants.LOCATION_ID);

        JSONObject response = ApiClient.post(AppConstants.OPPORTUNITY_API_URL, requestBody.toJSONString(), true);
        JSONObject opportunity = (JSONObject) response.get("opportunity");
        if (opportunity == null) {
            throw new RuntimeException("Create opportunity API failed. Response: " + response.toJSONString());
        }
        String opportunityId = (String) opportunity.get("id");
        LOGGER.info("Created opportunity '{}' with ID: {}", name, opportunityId);
        return opportunityId;
    }
}