package org.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Page Object Model for MultiBank Home/Trading Page
 */
public class HomePage extends BasePage {

    // Navigation Menu Locators
    private final By topNavigation = By.cssSelector("header, nav, [role='navigation']");

    // Trading Section Locators
    private final By spotTradingSection = By.cssSelector("div.home_section-wrapper__3AINE, [class*='home_section-wrapper']");
    private final By spotTradingHeader = By.xpath("//span[@class='style_label-badge-wrapper__MWCxl style_active__Yuxzy']");
    private final By tradingTable = By.cssSelector("table.style_table__kBCjf, table[class*='style_table']");
    private final By tradingPairs = By.cssSelector("tr.style_row__BPgMJ.style_selectable__mQK12");
    private final By tradingCategories = By.cssSelector("button.style_tab__xRtAF");
    private final By tradingCategoryDropdown = By.cssSelector("button[id*='headlessui-menu-button'], button[aria-haspopup='true'], button[aria-expanded]");
    private final By tradingTableSearchIcon = By.cssSelector("button.style_search-button__5yx5v, input[placeholder='Search'], input[type='search']");
    private final By tradingTableSearchInput = By.cssSelector("input.style_search-input__ZcX1j, input[placeholder='Search Pairs']");

    // Footer/Bottom Section Locators
    private final By marketingBanners = By.cssSelector("[class*='banner-container'], [class*='banner-title'], [class*='hero-banner']");
    private final By downloadSection = By.cssSelector("[class*='download'], [class*='app-download']");
    private final By appStoreLink = By.cssSelector("a[href*='apple.com'], a[href*='apps.apple'], img[alt*='App Store']");
    private final By googlePlayLink = By.cssSelector("a[href*='play.google'], a[href*='android'], img[alt*='Google Play']");

    // About Section
    private final By aboutUsLink = By.id("about-header-option-open-button");
    private final By whyMultiBankLink = By.xpath("//a[contains(@class, 'style_trade-link__m4JnR') and contains(@href, 'multibank')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Open MultiBank trading platform")
    public HomePage open(String url) {
        logger.info("Opening URL: {}", url);
        driver.get(url);
        waitForPageLoad();
        return this;
    }

    // Navigation Methods
    @Step("Check if top navigation is displayed")
    public boolean isTopNavigationDisplayed() {
        return isElementDisplayedWithWait(topNavigation, 10);
    }

    @Step("Verify navigation item is functional: {linkText}")
    public boolean isNavigationLinkFunctional(String linkText) {
        try {
            // Find the navigation item in the menu container
            WebElement navItem = driver.findElement(By.xpath(
                    String.format("//*[contains(text(), '%s')]", linkText.replace("'", "\\'"))));
            // Check if it's displayed and enabled
            if (!navItem.isDisplayed() || !navItem.isEnabled()) {
                return false;
            }
            // For <a> tags, verify href attribute
            if (navItem.getTagName().equals("a")) {
                String href = navItem.getAttribute("href");
                return href != null && !href.isEmpty() && !href.equals("#");
            }
            // For dropdown menus (div/span), they're functional if clickable
            return true;

        } catch (Exception e) {
            logger.error("Failed to check if navigation item '{}' is functional", linkText, e);
            return false;
        }
    }

    // Trading Section Methods
    @Step("Check if spot trading section is displayed")
    public boolean isSpotTradingSectionDisplayed() {
        scrollToElement(spotTradingSection);
        return isElementDisplayed(spotTradingSection);
    }

    @Step("Verify spot trading section header")
    public String getSpotTradingHeader() {
        return getText(spotTradingHeader);
    }

    @Step("Get trading categories")
    public List<WebElement> getTradingCategories() {
        scrollToElement(tradingCategories);
        return getElements(tradingCategories);
    }

    @Step("Check if trading pairs are present")
    public boolean hasTradingPairs() {
        try {
            waitForElementVisible(tradingTable);
            List<WebElement> pairs = driver.findElements(tradingPairs);
            logger.info("Trading pairs check: {} pairs found", pairs.size());
            return !pairs.isEmpty();
        } catch (Exception e) {
            logger.error("Error checking trading pairs: {}", e.getMessage());
            return false;
        }
    }

    @Step("Get trading pairs count")
    public int getTradingPairsCount() {
        try {
            waitForElementVisible(tradingTable);
            List<WebElement> pairs = driver.findElements(tradingPairs);
            logger.info("Found {} trading pairs", pairs.size());
            return pairs.size();
        } catch (Exception e) {
            logger.error("Failed to count trading pairs: {}", e.getMessage());
            return 0;
        }
    }

    @Step("Wait for trading pairs to load")
    public void waitForTradingPairsToLoad() {
        waitForElementVisible(tradingTable);
    }

    @Step("Click trading category dropdown")
    public void clickTradingCategoryDropdown() {
        click(tradingCategoryDropdown);
        logger.info("Clicked trading category dropdown");
    }

    @Step("Click trading category by ID: {categoryId}")
    public void clickTradingCategoryById(String categoryId) {
        By categoryLocator = By.cssSelector(String.format("#%s, [id='%s']", categoryId, categoryId));
        click(categoryLocator);
        logger.info("Clicked trading category: {}", categoryId);
    }

    @Step("Get trading pair data for: {pairName}")
    public Map<String, String> getTradingPairData(String pairName) {
        waitForElementVisible(tradingTable);

        //Search table for the pair
        click(tradingTableSearchIcon);
        enterText(tradingTableSearchInput, pairName);
        waitForElementVisible(tradingPairs);

        // Find the row containing the searched pair
        WebElement pairRow = driver.findElement(By.xpath(String.format("//td[contains(@id, '%s')]/ancestor::tr", pairName)));
        List<WebElement> cells = pairRow.findElements(By.tagName("td"));

        Map<String, String> pairData = new HashMap<>();

        // Assuming table structure: Pair | Price | 24h Change | High | Low | Last 7 days
        if (cells.size() >= 6) {
            pairData.put("pair", cells.get(1).getText().trim());
            pairData.put("price", cells.get(2).getText().trim());
            pairData.put("change", cells.get(3).getText().trim());
            pairData.put("high", cells.get(4).getText().trim());
            pairData.put("low", cells.get(5).getText().trim());

            // Check if chart/last 7 days column exists
            if (cells.size() >= 6) {
                pairData.put("chart", "Present");
            }
        }
        logger.info("Extracted data for {}: {}", pairName, pairData);
        return pairData;
    }

    @Step("Check if marketing banners are displayed")
    public boolean areMarketingBannersDisplayed() {
        scrollToBottom();
        return isElementDisplayed(marketingBanners);
    }

    @Step("Get marketing banners count")
    public int getMarketingBannersCount() {
        scrollToBottom();
        return getElements(marketingBanners).size();
    }

    @Step("Check if download section is displayed")
    public boolean isDownloadSectionDisplayed() {
        scrollToBottom();
        return isElementDisplayed(downloadSection);
    }

    @Step("Check if App Store link is present")
    public boolean isAppStoreLinkPresent() {
        scrollToBottom();
        return isElementDisplayed(appStoreLink);
    }

    @Step("Check if Google Play link is present")
    public boolean isGooglePlayLinkPresent() {
        scrollToBottom();
        return isElementDisplayed(googlePlayLink);
    }

    @Step("Get App Store link URL")
    public String getAppStoreLinkUrl() {
        return getAttribute(appStoreLink, "href");
    }

    @Step("Get Google Play link URL")
    public String getGooglePlayLinkUrl() {
        return getAttribute(googlePlayLink, "href");
    }

    @Step("Verify App Store link points to Apple")
    public boolean verifyAppStoreLinkValid() {
        String url = getAppStoreLinkUrl();
        return url != null && (url.contains("apple.com") || url.contains("app-store") || url.contains("ios"));
    }

    @Step("Verify Google Play link points to Google")
    public boolean verifyGooglePlayLinkValid() {
        String url = getGooglePlayLinkUrl();
        return url != null && (url.contains("google.com") || url.contains("play.google") || url.contains("android"));
    }

    // About/Why MultiBank Methods
    @Step("Click About Us link")
    public void clickAboutUs() {
        click(aboutUsLink);
    }

    @Step("Navigate to Why MultiBank page")
    public WhyMultiBankPage navigateToWhyMultiBankLink() {
        clickAboutUs();
        click(whyMultiBankLink);
        waitForPageLoad();
        return new WhyMultiBankPage(driver);
    }
}
