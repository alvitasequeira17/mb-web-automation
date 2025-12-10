package org.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object Model for Why MultiBank Page
 */
public class WhyMultiBankPage extends BasePage {

    public WhyMultiBankPage(WebDriver driver) {
        super(driver);
    }

    // Banner Section
    private final By bannerTitle = By.xpath("/html/body/div[1]/div[2]/div/div/div[1]/div/section/div/div[1]/div[3]/div[1]/div/h2");
    private final By bannerDescription = By.xpath("/html/body/div[1]/div[2]/div/div/div[1]/div/section/div/div[1]/div[3]/div[1]/div/p");

    // Portfolio Section
    private final By portfolioTitle = By.cssSelector("h2[class='text-white heading-2'] div p");
    private final By portfolioDescription = By.xpath("//p[@class='text-secondary font-size-4 paragraph']");
    private final By portfolioButton = By.xpath("//div[1]//div[1]//div[1]//div[1]//div[1]//div[1]//div[1]//div[1]//div[1]//div[1]//div[1]//div[1]//a[1]//button[1]");

    // Trading Section
    private final By tradingTitle = By.xpath("//h3[normalize-space()='Catch Your Next Trading Opportunity']");
    private final By tradingButton = By.xpath("(//span[contains(text(),'See More')])[1]");
    private final By tradingSpeedTitle = By.xpath("(//h2[@class='heading-2 text-white'])[1]");
    private final By tradingSpeedDescription = By.xpath("//p[contains(text(),'Experience lightning-fast trading speed with Multi')]");

    // Payment Methods Section
    private final By paymentMethodsTitle = By.xpath("//div[1]//div[1]//div[1]//div[1]//div[1]//div[3]//div[1]//div[1]//div[2]//div[1]//div[1]//h2[1]//div[1]//p[1]");
    private final By paymentMethodsDescription = By.xpath("//p[contains(text(),'At MultiBank io you can deposit USD and EURO, into')]");

    // Panic Sell Section
    private final By panicSellTitle = By.xpath("//div[1]//div[1]//div[1]//div[1]//div[1]//div[3]//div[1]//div[1]//div[3]//div[1]//div[1]//h2[1]//div[1]//p[1]");
    private final By panicSellDescription = By.xpath("//p[contains(text(),'Quickly sell all your low value cryptocurrencies t')]");

    // Convert Section
    private final By convertTitle = By.xpath("//p[contains(@class,'text-gold')]");
    private final By convertDescription = By.xpath("//div[contains(@class,'font-size-4 text-secondary main-cards_customDescription__Tzkws')]");

    // Advantages Section
    private final By advantagesTitle = By.xpath("//h2[normalize-space()='Our Advantages']");
    private final By advantagesDescription = By.xpath("//p[contains(text(),'At MultiBank, we put our clients first, offering p')]");
    private final By advantageFiatTitle = By.xpath("//h3[contains(text(),'Fiat On and Off')]");
    private final By advantageFiatDescription = By.xpath("//p[contains(text(),'Seamlessly convert between FIAT and crypto with Mu')]");
    private final By advantageRegulatedTitle = By.xpath("//h3[normalize-space()='Heavily Regulated']");
    private final By advantageRegulatedDescription = By.xpath("//p[contains(text(),'MultiBank Group is comprised of several entities t')]");
    private final By advantageSecurityTitle = By.xpath("//h3[normalize-space()='Security of Funds']");
    private final By advantageSecurityDescription = By.xpath("//p[contains(text(),'Secure digital asset custody platform with multi-p')]");
    private final By advantageCryptoTitle = By.xpath("//h3[normalize-space()='Crypto Currencies']");
    private final By advantageCryptoDescription = By.xpath("//p[contains(text(),'Buy and sell your selection of over 20 of the top ')]");
    private final By advantageSupportTitle = By.xpath("//h3[normalize-space()='Customer Service']");
    private final By advantageSupportDescription = By.xpath("//p[contains(text(),'Enjoy assistance from our 24/7/365 customer servic')]");

    // Start Trading Section
    private final By startTradingDescription = By.xpath("//div[@class='StartTradingNow_startTradingDescription__e_Xv7']");

    @Step("Verify Why MultiBank page is loaded")
    public boolean isPageLoaded() {
        waitForPageLoad();
        return driver.getCurrentUrl().contains("multibank.io") || driver.getCurrentUrl().contains("/en-");
    }

    @Step("Get current page URL")
    public String getPageUrl() {
        return driver.getCurrentUrl();
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

    @Step("Get active banner title")
    public String getActiveBannerTitle() {
        return getText(bannerTitle);
    }

    @Step("Get active banner description")
    public String getActiveBannerDescription() {
        return getText(bannerDescription);
    }

    @Step("Get portfolio title")
    public String getPortfolioTitle() {
        scrollToElement(portfolioTitle);
        return getText(portfolioTitle);
    }

    @Step("Get portfolio description")
    public String getPortfolioDescription() {
        return getText(portfolioDescription);
    }

    @Step("Get portfolio button")
    public String getPortfolioButton() {
        return getText(portfolioButton);
    }

    @Step("Get trading title")
    public String getTradingTitle() {
        return getText(tradingTitle);
    }

    @Step("Get trading button")
    public String getTradingButton() {
        return getText(tradingButton);
    }

    @Step("Get trading speed title")
    public String getTradingSpeedTitle() {
        scrollToElement(tradingSpeedTitle);
        return getText(tradingSpeedTitle);
    }

    @Step("Get trading speed description")
    public String getTradingSpeedDescription() {
        return getText(tradingSpeedDescription);
    }

    @Step("Get payment methods title")
    public String getPaymentMethodsTitle() {
        scrollToElement(paymentMethodsTitle);
        return getText(paymentMethodsTitle);
    }

    @Step("Get payment methods description")
    public String getPaymentMethodsDescription() {
        return getText(paymentMethodsDescription);
    }

    @Step("Get panic sell title")
    public String getPanicSellTitle() {
        return getText(panicSellTitle);
    }

    @Step("Get panic sell description")
    public String getPanicSellDescription() {
        return getText(panicSellDescription);
    }

    @Step("Get convert title")
    public String getConvertTitle() {
        return getText(convertTitle);
    }

    @Step("Get convert description")
    public String getConvertDescription() {
        return getText(convertDescription);
    }

    // Advantages Section Methods
    @Step("Get advantages title")
    public String getAdvantagesTitle() {
        scrollToElement(advantagesTitle);
        return getText(advantagesTitle);
    }

    @Step("Get advantages description")
    public String getAdvantagesDescription() {
        return getText(advantagesDescription);
    }

    @Step("Get advantage Fiat title")
    public String getAdvantageFiatTitle() {
        scrollToElement(advantageFiatTitle);
        return getText(advantageFiatTitle);
    }

    @Step("Get advantage Fiat description")
    public String getAdvantageFiatDescription() {
        return getText(advantageFiatDescription);
    }

    @Step("Get advantage Regulated title")
    public String getAdvantageRegulatedTitle() {
        scrollToElement(advantageRegulatedTitle);
        return getText(advantageRegulatedTitle);
    }

    @Step("Get advantage Regulated description")
    public String getAdvantageRegulatedDescription() {
        return getText(advantageRegulatedDescription);
    }

    @Step("Get advantage Security title")
    public String getAdvantageSecurityTitle() {
        scrollToElement(advantageSecurityTitle);
        return getText(advantageSecurityTitle);
    }

    @Step("Get advantage Security description")
    public String getAdvantageSecurityDescription() {
        return getText(advantageSecurityDescription);
    }

    @Step("Get advantage Crypto title")
    public String getAdvantageCryptoTitle() {
        scrollToElement(advantageCryptoTitle);
        return getText(advantageCryptoTitle);
    }

    @Step("Get advantage Crypto description")
    public String getAdvantageCryptoDescription() {
        return getText(advantageCryptoDescription);
    }

    @Step("Get advantage Support title")
    public String getAdvantageSupportTitle() {
        scrollToElement(advantageSupportTitle);
        return getText(advantageSupportTitle);
    }

    @Step("Get advantage Support description")
    public String getAdvantageSupportDescription() {
        return getText(advantageSupportDescription);
    }

    @Step("Get start trading description")
    public String getStartTradingDescription() {
        scrollToElement(startTradingDescription);
        return getText(startTradingDescription);
    }
}

