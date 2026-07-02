package highlevel;

import api.ContactApi;
import api.OpportunityApi;
import constants.AppConstants;
import global.WebBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.highlevel.HighLevel;

public class HighLevelTest extends WebBaseTest {

    private String contactId;
    private String opportunityId;

//    @Test(priority = 1, description = "Create contact via API and validate contact ID is returned")
//    public void createContact() throws Exception {
//        contactId = ContactApi.createContact(AppConstants.CONTACT_NAME);
//    }

//    @Test(priority = 2, dependsOnMethods = "createContact",
//            description = "Create opportunity for the created contact via API")
//    public void createOpportunity() throws Exception {
//        opportunityId = OpportunityApi.createOpportunity(AppConstants.CONTACT_NAME, contactId);
//    }

    @Test(priority = 1,
            description = "Navigate to opportunities page in Chrome and validate contact name is visible")
    public void validateUI() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        HighLevel highLevel = new HighLevel();
        highLevel.navigateTo(AppConstants.kotaklink);
        highLevel.openCategory();
        Thread.sleep(3000);
        highLevel.clickTravel();
        Thread.sleep(2000);
        highLevel.clickShowItems();
        Thread.sleep(1000);
        softAssert.assertTrue(highLevel.isIndigoCardVisible(), " ashwi test 1");
        softAssert.assertTrue(highLevel.isIndigoTextVisible(), " ashwi test 2 ");
        Thread.sleep(10000);
        softAssert.assertAll();
    }
}