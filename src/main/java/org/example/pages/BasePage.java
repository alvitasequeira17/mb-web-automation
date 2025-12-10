package org.example.pages;

import io.qameta.allure.Step;
import org.example.config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Base Page class with common page operations and wait strategies
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        int explicitWait = ConfigReader.getConfig().getTimeouts().getExplicitWait();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
    }

    protected WebElement waitForElementVisible(By locator) {
        return waitFor(ExpectedConditions.visibilityOfElementLocated(locator), "Element not visible: " + locator);
    }

    protected WebElement waitForElementClickable(By locator) {
        return waitFor(ExpectedConditions.elementToBeClickable(locator), "Element not clickable: " + locator);
    }

    protected List<WebElement> waitForElementsVisible(By locator) {
        return waitFor(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator), "Elements not visible: " + locator);
    }

    protected WebElement waitForElementPresent(By locator) {
        return waitFor(ExpectedConditions.presenceOfElementLocated(locator), "Element not present: " + locator);
    }

    private <T> T waitFor(org.openqa.selenium.support.ui.ExpectedCondition<T> condition, String errorMsg) {
        try {
            return wait.until(condition);
        } catch (TimeoutException e) {
            logger.error(errorMsg);
            throw e;
        }
    }

    @Step("Check if element is displayed: {locator}")
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            logger.debug("Element not found: {}", locator);
            return false;
        }
    }

    @Step("Check if element is displayed with wait: {locator}")
    protected boolean isElementDisplayedWithWait(By locator, int timeoutSeconds) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            logger.debug("Element not displayed: {}", locator);
            return false;
        }
    }

    @Step("Click element: {locator}")
    protected void click(By locator) {
        waitForElementClickable(locator).click();
        logger.info("Clicked element: {}", locator);
    }

    @Step("Get text from element: {locator}")
    protected String getText(By locator) {
        String text = waitForElementVisible(locator).getText();
        logger.info("Got text '{}' from: {}", text, locator);
        return text;
    }

    @Step("Enter text '{text}' into element: {locator}")
    protected void enterText(By locator, String text) {
        waitForElementVisible(locator).sendKeys(text);
        logger.info("Entered text '{}' into: {}", text, locator);
    }

    @Step("Get attribute '{attribute}' from element: {locator}")
    protected String getAttribute(By locator, String attribute) {
        return waitForElementPresent(locator).getAttribute(attribute);
    }

    protected List<WebElement> getElements(By locator) {
        return waitForElementsVisible(locator);
    }

    @Step("Scroll to element: {locator}")
    protected void scrollToElement(By locator) {
        WebElement element = waitForElementPresent(locator);
        scrollToElement(element);
    }

    @Step("Scroll to element")
    protected void scrollToElement(WebElement element) {
        executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        logger.debug("Scrolled to element");
    }

    @Step("Scroll to bottom of page")
    protected void scrollToBottom() {
        executeScript("window.scrollTo(0, document.body.scrollHeight)");
        logger.debug("Scrolled to bottom of page");
    }

    @Step("Wait for page to load completely")
    protected void waitForPageLoad() {
        wait.until(driver -> executeScript("return document.readyState").equals("complete"));
        logger.debug("Page loaded completely");
    }

    private Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    @Step("Check if text is displayed: {text}")
    public boolean isTextDisplayed(String text) {
        try {
            // Use XPath to find any element containing the text
            String xpath = String.format("//*[contains(text(), '%s')]", text.replace("'", "\\'"));
            WebElement element = driver.findElement(By.xpath(xpath));
            // Scroll to the element
            scrollToElement(element);
            boolean isDisplayed = element.isDisplayed();
            logger.debug("Text '{}' - displayed: {}", text, isDisplayed);
            return isDisplayed;
        } catch (NoSuchElementException e) {
            logger.debug("Text '{}' not found on page", text);
            return false;
        } catch (Exception e) {
            logger.warn("Error checking if text '{}' is displayed: {}", text, e.getMessage());
            return false;
        }
    }
}

