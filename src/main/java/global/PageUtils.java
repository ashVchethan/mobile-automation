package global;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;

public class PageUtils extends BasePage {
    private void performScroll(int startX, int startY, int endX, int endY) {
        PointerInput finger;
        Sequence swipe;
        try {
            finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            swipe =
                    new Sequence(finger, 1)
                            .addAction(
                                    finger.createPointerMove(
                                            Duration.ZERO,
                                            PointerInput.Origin.viewport(),
                                            startX,
                                            startY))
                            .addAction(
                                    finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                            .addAction(new Pause(finger, Duration.ofMillis(600)))
                            .addAction(
                                    finger.createPointerMove(
                                            Duration.ofMillis(2000),
                                            PointerInput.Origin.viewport(),
                                            endX,
                                            endY))
                            .addAction(
                                    finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Collections.singletonList(swipe));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void performScrollScreenWise(
            double startXPercentage,
            double startYPercentage,
            double endXPercentage,
            double endYPercentage) {
        Dimension size = driver.manage().window().getSize();
        int height = size.getHeight();
        int width = size.getWidth();
        int startX = (int) (width * startXPercentage);
        int startY = (int) (height * startYPercentage);
        int endX = (int) (width * endXPercentage);
        int endY = (int) (height * endYPercentage);
        performScroll(startX, startY, endX, endY);
    }

    public void scrollDown(int noOfScrolls) {
        for (int scrollCount = 0; scrollCount < noOfScrolls; scrollCount++) {
            performScrollScreenWise(0.5, 0.8, 0.5, 0.2);
        }
    }

    public void scrollUp(int noOfScrolls) {
        for (int scrollCount = 0; scrollCount < noOfScrolls; scrollCount++) {
            performScrollScreenWise(0.5, 0.2, 0.5, 0.8);
        }
    }

    public boolean scrollDownTillElementVisible(WebElement element, int... noOfScrolls) {
        int scrollCount = (noOfScrolls.length > 0) ? noOfScrolls[0] : 5;
        for (int scrollTimes = 0; scrollTimes < scrollCount; scrollTimes++) {
            try {
                if (element.isDisplayed()) {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Element not found: " + element.toString());
                scrollDown(noOfScrolls[0]);
            }
        }
        return false;
    }

    public boolean isElementVisible(WebElement element, String... customMsg) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            System.out.println("WebElement is visible: " + element.toString());
            return true;
        } catch (Exception e) {
            System.out.println("Element not found: " + element.toString());
            return false;
        }
    }

    public boolean isElementVisibleThenClick(WebElement element) {
        try {
            if (this.isElementVisible(element)) {
                element.click();
                System.out.println("Clicked on element: " + element);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Waits for the specified WebElement to become visible within the given time.
     *
     * <p>This method does not throw an exception; it returns true if the element becomes visible,
     * or false if it does not.
     *
     * @param element The WebElement to wait for.
     * @param timeoutInSec The maximum time, in seconds, to wait for the WebElement to become
     *     visible.
     * @return true if the WebElement becomes visible within the timeout, false otherwise.
     */
    public boolean checkElementVisibilitySafely(WebElement element, int timeoutInSec) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSec));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            System.out.println(
                    "Element identified by "
                            + element.toString()
                            + " was not visible in "
                            + timeoutInSec
                            + " Sec");
            return false;
        }
    }
}
