package global;

import static java.time.Duration.ofSeconds;

import driver.DriverManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected AppiumDriver driver;

    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, ofSeconds(10)), this);
        wait = new WebDriverWait(this.driver, ofSeconds(15));
    }
}
