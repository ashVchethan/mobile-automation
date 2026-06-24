package android;

import br.com.six2six.fixturefactory.Fixture;

import dataTemplatesPojo.login.LoginCredentials;

import global.BaseTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.login.Login;

public class LoginTest extends BaseTest {
    private LoginCredentials credentials;

    @BeforeClass(alwaysRun = true)
    public void initializeFixtures(){
        credentials = Fixture.from(LoginCredentials.class).gimme("LoginCredentials");
    }

    @Test
    public void verifyAndroidValidLogin() {
        Login login = new Login();
        login.inputMobileNumber(credentials.getMobileNumber());
    }
}
