package pages.highlevel;

import global.WebBasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HighLevel extends WebBasePage {
    private static final Logger LOGGER = LogManager.getLogger(HighLevel.class);

    @FindBy(xpath = "(//div[@class=\"dropdown-toggle af-dropdown-toggle\"]/span[contains(text(),'Categories')])[1]")
    private WebElement categories;

    @FindBy(xpath = "//a[@id='tab2']")
    private WebElement debitCard;

    @FindBy(xpath = "//input[@id=\"credit-card-category+travel\"]")
    private WebElement travel;

    @FindBy(xpath = "//a[@class=\"af-dropdownmenu-btn-2\" and contains(text(),'Show 3 Items') and @data-category=\"Categories\"]")
    private WebElement showItems;

    @FindBy(xpath = "//div[@class=\"sa-img-text\"]/p[contains(text(),'Indigo Kotak Premium Credit Card')]")
    private WebElement indigoCard;

    @FindBy(xpath = "//li[contains(text(),'Welcome benefit of 4,000 IndiGo BluChips Voucher & 6E Eats voucher')]")
    private WebElement indigoText;

    public void navigateTo(String url) {
        LOGGER.info("Navigating to: {}", url);
        driver.get(url);
    }

    public void openCategory() {
        LOGGER.info("Scrolling to and clicking Categories");
        scrollToElement(categories);
        isElementVisibleThenClick(categories);
        LOGGER.info("Clicked Categories");
    }

    public void clickTravel() {
        LOGGER.info("Scrolling to and clicking Travel");
        scrollToElement(travel);
        isElementVisibleThenClick(travel);
        LOGGER.info("Clicked Travel");
    }

    public void clickShowItems() {
        LOGGER.info("Waiting for and clicking 'Show 3 Items'");
        isElementVisibleThenClick(showItems);
        LOGGER.info("Clicked 'Show 3 Items'");
    }

    public boolean isIndigoCardVisible() {
        return isElementVisible(indigoCard, "Indigo Kotak Premium Credit Card");
    }

    public boolean isIndigoTextVisible() {
        return isElementVisible(indigoText, "Indigo welcome benefit text");
    }
}