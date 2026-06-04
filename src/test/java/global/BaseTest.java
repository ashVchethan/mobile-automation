package global;

import constants.PathConstants;

import driver.DriverFactory;
import driver.DriverManager;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected String platformName = PathConstants.PLATFORM;

    @BeforeMethod(alwaysRun = true)
    public void initializeDriver() {
        DriverFactory.createDriver(platformName);
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        // TODO: Add code to reset user session
        // quit the driver
        // new driver
        DriverManager.quitDriver();
    }
}
