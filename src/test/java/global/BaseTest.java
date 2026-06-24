package global;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

import constants.PathConstants;

import driver.DriverFactory;
import driver.DriverManager;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected String platformName = PathConstants.PLATFORM;

    @BeforeSuite(alwaysRun = true)
    public void loadFixtureTemplates() {
        FixtureFactoryLoader.loadTemplates("dataTemplates");
    }

    @BeforeClass(alwaysRun = true)
    public void initializeDriver() {
        DriverFactory.createDriver(platformName);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        // TODO: Add code to reset user session
        // quit the driver
        // new driver
        DriverManager.quitDriver();
    }
}
