package android;

import global.BaseTest;

import org.testng.annotations.Test;

import pages.login.Login;

public class LoginTest extends BaseTest {
    @Test
    public void verifyAndroidValidLogin() {
        Login login = new Login();
        login.inputMobileNumber("8867132477");
    }
}
