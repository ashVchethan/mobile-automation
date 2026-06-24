package android;

import br.com.six2six.fixturefactory.Fixture;

import dataTemplatesPojo.login.LoginCredentials;

import global.BaseTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.testng.asserts.SoftAssert;
import pages.login.Login;

public class LoginTest extends BaseTest {
    private LoginCredentials credentials;

    @BeforeClass(alwaysRun = true)
    public void initializeFixtures(){
        credentials = Fixture.from(LoginCredentials.class).gimme("LoginCredentials");
    }


   // @Test(dataProvider = "LoginCredentials",  groups = { "login" }, priority = 1,dependsOnMethods = {"add DB connect here, method name"})
    @Test
    public void verifyAndroidValidLogin() {
        Login login = new Login();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(login.isLoginPageOpened() ,"Failed to open the LoginPage");
        login.inputMobileNumber(credentials.getMobileNumber());
        login.clickContinue();
        softAssert.assertTrue(login.isPasswordPageOpened() ,"Failed to open the password screen");
        softAssert.assertAll();
    }
}
