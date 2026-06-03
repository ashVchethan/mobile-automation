package pages.login;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;

public class Login extends global.BasePage {

    @AndroidFindBy(id = "inputText")
    @iOSXCUITFindBy(id = "inputText")
    private WebElement mobileInput;

    public boolean login() {
        return true;
    }

    public void inputMobileNumber(String mobileNum) {
        mobileInput.sendKeys(mobileNum);
    }
}
