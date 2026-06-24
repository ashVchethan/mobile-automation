package pages.login;

import global.PageUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;

public class Login extends PageUtils {

    @AndroidFindBy(id = "inputText")
    @iOSXCUITFindBy(id = "inputText")
    private WebElement mobileInput;

    @AndroidFindBy(id = "password")
    @iOSXCUITFindBy(id = "password")
    private WebElement password;

    @AndroidFindBy(id = "continue")
    @iOSXCUITFindBy(id = "continue")
    private WebElement continueButton;

    public boolean login() {
        return true;
    }

    public boolean isLoginPageOpened(){ return isElementVisible(mobileInput,"Navigated mobile number");}

    public void inputMobileNumber(String mobileNum) {
        mobileInput.sendKeys(mobileNum);
    }
    public void clickContinue() {
        isElementVisibleThenClick(continueButton);
    }
    public boolean isPasswordPageOpened(){ return isElementVisible(password,"Navigated mobile number");}

}
