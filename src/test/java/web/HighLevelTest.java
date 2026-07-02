package web;

import api.ContactApi;
import api.OpportunityApi;
import global.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class HighLevelTest extends BaseTest {

    private String contactId;
    private String opportunityId;

    private static final String CONTACT_FIRST_NAME = "TestUser";

    @Test(priority = 1, description = "Create contact via API and validate contact ID is returned")
    public void createContact() throws Exception {
        contactId = ContactApi.createContact(CONTACT_FIRST_NAME);
        Assert.assertNotNull(contactId, "Contact ID should not be null after creation");
    }

    @Test(priority = 2, dependsOnMethods = "createContact",
            description = "Create opportunity for the created contact via API")
    public void createOpportunity() throws Exception {
        opportunityId = OpportunityApi.createOpportunity(CONTACT_FIRST_NAME, contactId);
        Assert.assertNotNull(opportunityId, "Opportunity ID should not be null after creation");
    }

    @Test(priority = 3, dependsOnMethods = "createOpportunity",
            description = "Validate contact and opportunity appear correctly in the UI")
    public void validateUI() {
        SoftAssert softAssert = new SoftAssert();
        // TODO: Add UI page interactions and assertions here
        softAssert.assertAll();
    }
}