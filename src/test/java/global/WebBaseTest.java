package global;

import driver.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class WebBaseTest {

    @BeforeClass(alwaysRun = true)
    public void initDriver() {
        WebDriverManager.setDriver(new ChromeDriver());
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        WebDriverManager.quitDriver();
    }
}