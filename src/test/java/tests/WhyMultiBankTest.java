package tests;

import io.qameta.allure.*;
import org.example.pages.HomePage;
import org.example.pages.WhyMultiBankPage;
import org.example.utils.TestDataManager;
import org.example.utils.TestDataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Why MultiBank Test Suite
 * Verifies all expected text content from why-multibank-data.json
 */
@Epic("MultiBank Trading Platform")
@Feature("About Us - Why MultiBank")
public class WhyMultiBankTest extends BaseTest {

    private Map<String, Object> testData;
    private WhyMultiBankPage whyMultiBankPage;


    @BeforeMethod
    public void setup() {
        HomePage homePage = new HomePage(driver);
        homePage.open(getBaseUrl());
        whyMultiBankPage = homePage.navigateToWhyMultiBankLink();
    }

    @BeforeClass
    public void loadTestData() {
        testData = TestDataManager.loadTestData("why-multibank-data.json");
    }

    @Test(priority = 1, description = "Navigate to Why MultiBank page")
    @Story("Page Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify navigation to Why MultiBank page from About Us section")
    public void navigateToWhyMultiBankPage() {
        boolean isLoaded = whyMultiBankPage.isPageLoaded();
        assertThat(isLoaded)
                .as("Why MultiBank page should be loaded")
                .isTrue();

        String currentUrl = whyMultiBankPage.getPageUrl();
        logger.info("Navigated to Why MultiBank page: {}", currentUrl);
    }

    @Test(priority = 2, description = "Verify banners in header")
    @Story("Header Banners")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that Header Banners displays title and button text from JSON")
    public void verifyHeaderBanners() {
        @SuppressWarnings("unchecked")
        Map<String, Object> banners = (Map<String, Object>) testData.get("banners");
        String expectedBanner1Title = (String) banners.get("title1");
        String expectedBanner2Title = (String) banners.get("title2");
        String expectedBanner3Title = (String) banners.get("title3");
        String expectedBanner1Description = (String) banners.get("description1");
        String expectedBanner2Description = (String) banners.get("description2");
        String expectedBanner3Description = (String) banners.get("description3");

        String actualBannerTitle = whyMultiBankPage.getActiveBannerTitle();
        String actualBannerDescription = whyMultiBankPage.getActiveBannerDescription();

        // Verify banner
        assertThat(actualBannerTitle)
                .as("Active banner should match either banners in expectd data")
                .isIn(expectedBanner1Title, expectedBanner2Title, expectedBanner3Title);
        assertThat(actualBannerDescription)
                .as("Active banner should match either banners in expectd data")
                .isIn(expectedBanner1Description, expectedBanner2Description, expectedBanner3Description);

        logger.info("Header banner verified successfully");
    }

    @Test(priority = 3, description = "Verify portfolio section content")
    @Story("Portfolio Section")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify portfolio section title and description match expected values from JSON")
    public void verifyPortfolioSection() {
        boolean isLoaded = whyMultiBankPage.isPageLoaded();
        assertThat(isLoaded)
                .as("Why MultiBank page should be loaded")
                .isTrue();

        @SuppressWarnings("unchecked")
        Map<String, String> portfolioData = (Map<String, String>) ((Map<String, Object>) testData.get("sections")).get("portfolio");

        String expectedTitle = portfolioData.get("title");
        String expectedDescription = portfolioData.get("description");
        String expectedButtonText = portfolioData.get("buttonText");

        String actualTitle = whyMultiBankPage.getPortfolioTitle();
        assertThat(actualTitle)
                .as("Portfolio section title should match expected value")
                .isEqualTo(expectedTitle);

        String actualDescription = whyMultiBankPage.getPortfolioDescription();
        assertThat(actualDescription)
                .as("Portfolio section description should match expected value")
                .isEqualTo(expectedDescription);

        String actualButtonText = whyMultiBankPage.getPortfolioButton();
        assertThat(actualButtonText)
                .as("Portfolio section description should match expected value")
                .isEqualTo(expectedButtonText);

        logger.info("Portfolio section verified - Title and Description match expected values");
    }

    @Test(priority = 4, description = "Verify trading sections are displayed with content")
    @Story("Trading Section")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify trading section title and descriptions match expected values from JSON")
    public void verifyTradingSections() {
        boolean isLoaded = whyMultiBankPage.isPageLoaded();
        assertThat(isLoaded)
                .as("Why MultiBank page should be loaded")
                .isTrue();

        Map<String, String> tradingData = getSectionData("trading");
        Map<String, String> tradingSpeedData = getSectionData("tradingSpeed");
        Map<String, String> paymentMethodsData = getSectionData("paymentMethods");
        Map<String, String> panicSellData = getSectionData("panicSell");
        Map<String, String> convertData = getSectionData("convert");

        String expectedTitle = tradingData.get("title");
        String expectedButtonText = tradingData.get("buttonText");
        String expectedSpeedTitle = tradingSpeedData.get("title");
        String expectedSpeedDescription = tradingSpeedData.get("description");
        String expectedPaymentTitle = paymentMethodsData.get("title");
        String expectedPaymentDescription = paymentMethodsData.get("description");
        String expectedPanicTitle = panicSellData.get("title");
        String expectedPanicDescription = panicSellData.get("description");
        String expectedConvertTitle = convertData.get("title");
        String expectedConvertDescription = convertData.get("description");


        String actualTitle = whyMultiBankPage.getTradingTitle();
        assertThat(actualTitle)
                .as("Portfolio section title should match expected value")
                .isEqualTo(expectedTitle);
        String actualButtonText = whyMultiBankPage.getTradingButton();
        assertThat(actualButtonText)
                .as("Portfolio section description should match expected value")
                .isEqualTo(expectedButtonText);
        String actualSpeedTitle = whyMultiBankPage.getTradingSpeedTitle();
        assertThat(actualSpeedTitle)
                .as("Trading Speed title should match expected value")
                .isEqualTo(expectedSpeedTitle);
        String actualSpeedDescription = whyMultiBankPage.getTradingSpeedDescription();
        assertThat(actualSpeedDescription)
                .as("Trading Speed description should match expected value")
                .isEqualTo(expectedSpeedDescription);
        String actualPaymentTitle = whyMultiBankPage.getPaymentMethodsTitle();
        assertThat(actualPaymentTitle)
                .as("Payment Methods title should match expected value")
                .isEqualTo(expectedPaymentTitle);
        String actualPaymentDescription = whyMultiBankPage.getPaymentMethodsDescription();
        assertThat(actualPaymentDescription)
                .as("Payment Methods description should match expected value")
                .isEqualTo(expectedPaymentDescription);
        String actualPanicTitle = whyMultiBankPage.getPanicSellTitle();
        assertThat(actualPanicTitle)
                .as("Panic Sell title should match expected value")
                .isEqualTo(expectedPanicTitle);
        String actualPanicDescription = whyMultiBankPage.getPanicSellDescription();
        assertThat(actualPanicDescription)
                .as("Panic Sell description should match expected value")
                .isEqualTo(expectedPanicDescription);
        String actualConvertTitle = whyMultiBankPage.getConvertTitle();
        assertThat(actualConvertTitle)
                .as("Convert title should match expected value")
                .isEqualTo(expectedConvertTitle);
        String actualConvertDescription = whyMultiBankPage.getConvertDescription();
        assertThat(actualConvertDescription)
                .as("Convert description should match expected value")
                .isEqualTo(expectedConvertDescription);
        logger.info("Trading section verified - Title and Button match expected values");
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getSectionData(String sectionName) {
        return (Map<String, String>) ((Map<String, Object>) testData.get("sections")).get(sectionName);
    }

    @Test(priority = 5, description = "Verify advantages section header")
    @Story("Advantages Section")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify advantages section title, subtitle, and all advantage items match JSON data")
    public void verifyAdvantages() {
        @SuppressWarnings("unchecked")
        Map<String, Object> advantages = (Map<String, Object>) testData.get("advantages");
        String expectedTitle = (String) advantages.get("title");
        String expectedSubtitle = (String) advantages.get("subtitle");

        // Verify title
        String actualTitle = whyMultiBankPage.getAdvantagesTitle();
        assertThat(actualTitle)
                .as("Advantages title should match")
                .isEqualTo(expectedTitle);

        // Verify subtitle
        String actualSubtitle = whyMultiBankPage.getAdvantagesDescription();
        assertThat(actualSubtitle)
                .as("Advantages subtitle should match")
                .isEqualTo(expectedSubtitle);
    }

    @Test(priority = 5, dataProvider = "advantageItemsData", dataProviderClass = TestDataProvider.class,
            description = "Verify advantage item {0} with title and description")
    @Story("Advantages Items")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Parameterized test: Verify each advantage item title and description from JSON")
    public void verifyAdvantageItem(int itemIndex, Map<String, String> itemData) {
        String expectedTitle = itemData.get("title");
        String expectedDescription = itemData.get("description");

        String actualTitle = "";
        String actualDescription = "";

        // Get actual values based on index
        switch (itemIndex) {
            case 0:
                actualTitle = whyMultiBankPage.getAdvantageFiatTitle();
                actualDescription = whyMultiBankPage.getAdvantageFiatDescription();
                break;
            case 1:
                actualTitle = whyMultiBankPage.getAdvantageRegulatedTitle();
                actualDescription = whyMultiBankPage.getAdvantageRegulatedDescription();
                break;
            case 2:
                actualTitle = whyMultiBankPage.getAdvantageSecurityTitle();
                actualDescription = whyMultiBankPage.getAdvantageSecurityDescription();
                break;
            case 3:
                actualTitle = whyMultiBankPage.getAdvantageCryptoTitle();
                actualDescription = whyMultiBankPage.getAdvantageCryptoDescription();
                break;
            case 4:
                actualTitle = whyMultiBankPage.getAdvantageSupportTitle();
                actualDescription = whyMultiBankPage.getAdvantageSupportDescription();
                break;
        }

        // Verify title
        assertThat(actualTitle)
                .as("Advantage item %d title should match", itemIndex + 1)
                .containsIgnoringCase(expectedTitle);

        // Verify description (partial match)
        assertThat(actualDescription)
                .as("Advantage item %d title should match", itemIndex + 1)
                .isEqualTo(expectedDescription);

        logger.info("Advantage item {} verified: {}", itemIndex + 1, expectedTitle);
    }

    @Test(priority = 6, description = "Verify Spot Trading and RWA section with all content")
    @Story("Spot Trading Section")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that Spot Trading and RWA section displays title, subtitle, description and button text from JSON")
    public void verifySpotTrading() {
        @SuppressWarnings("unchecked")
        Map<String, Object> spotTrading = (Map<String, Object>) testData.get("spotTrading");

        String expectedTitle = (String) spotTrading.get("title");
        String expectedSubtitle = (String) spotTrading.get("subtitle");
        String expectedDescription = (String) spotTrading.get("description");
        String expectedButtonText = (String) spotTrading.get("buttonText");

        @SuppressWarnings("unchecked")
        Map<String, Object> rwa = (Map<String, Object>) testData.get("rwa");
        String expectedRWATitle = (String) rwa.get("title");
        String expectedRWADescription = (String) rwa.get("description");
        String expectedRWAButtonText = (String) rwa.get("buttonText");

        // Verify Spot Trading Section
        assertThat(whyMultiBankPage.isTextDisplayed(expectedTitle))
                .as("Spot Trading title should be displayed")
                .isTrue();
        assertThat(whyMultiBankPage.isTextDisplayed(expectedSubtitle))
                .as("Spot Trading subtitle should be displayed")
                .isTrue();
        assertThat(whyMultiBankPage.isTextDisplayed(expectedDescription))
                .as("Spot Trading description should be displayed")
                .isTrue();
        assertThat(whyMultiBankPage.isTextDisplayed(expectedButtonText))
                .as("Spot Trading button should be displayed")
                .isTrue();

        //Verify RWA Section
        assertThat(whyMultiBankPage.isTextDisplayed(expectedRWATitle))
                .as("RWA title should be displayed")
                .isTrue();
        assertThat(whyMultiBankPage.isTextDisplayed(expectedRWADescription))
                .as("RWA description should be displayed")
                .isTrue();
        assertThat(whyMultiBankPage.isTextDisplayed(expectedRWAButtonText))
                .as("RWA button text should be displayed")
                .isTrue();

        logger.info("Spot Trading and RWA section verified successfully");
    }

    @Test(priority = 6, dataProvider = "spotTradingFeaturesData", dataProviderClass = TestDataProvider.class,
            description = "Verify spot trading feature {0} is displayed")
    @Story("Spot Trading Features")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Parameterized test: Verify each spot trading feature from JSON")
    public void verifySpotTradingFeature(int featureNumber, String feature) {
        assertThat(whyMultiBankPage.isTextDisplayed(feature))
                .as("Spot Trading feature %d should be displayed: '%s'", featureNumber, feature)
                .isTrue();

        logger.info("Spot Trading feature {} verified: {}", featureNumber, feature);
    }

    @Test(priority = 7, description = "Verify Start Trading section with all content")
    @Story("Start Trading Section")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that Start Trading section displays title and button text from JSON")
    public void verifyStartTradingSection() {
        @SuppressWarnings("unchecked")
        Map<String, Object> startTrading = (Map<String, Object>) testData.get("startTrading");

        String expectedTitle = (String) startTrading.get("title");
        String expectedDescription = (String) startTrading.get("description");
        String expectedButtonText = (String) startTrading.get("buttonText");

        String actualDescription = whyMultiBankPage.getStartTradingDescription();

        // Verify Start Trading Section
        assertThat(whyMultiBankPage.isTextDisplayed(expectedTitle))
                .as("Start Trading title should be displayed")
                .isTrue();
        assertThat(actualDescription)
                .as("Start Trading description should match expected value")
                .isEqualTo(expectedDescription);
        assertThat(whyMultiBankPage.isTextDisplayed(expectedButtonText))
                .as("Start Trading button should be displayed")
                .isTrue();

        logger.info("Start Trading section verified successfully");
    }
}

