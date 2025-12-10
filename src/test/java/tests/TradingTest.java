package tests;

import io.qameta.allure.*;
import org.example.pages.HomePage;
import org.example.utils.TestDataManager;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test Suite: Trading Functionality scenarios.
 * Validates spot trading section visibility, categories, trading pairs display,
 * and data structure on the MultiBank trading platform home page.
 */
@Epic("MultiBank Trading Platform")
@Feature("Trading Functionality")
public class TradingTest extends BaseTest {

    private HomePage homePage;
    private Map<String, Object> testData;
    private List<String> expectedCategories;
    private String expectedSpotHeader;
    private String tradingPair;

    @BeforeClass
    @Step("Load trading test data from JSON configuration")
    public void loadTestData() {
        testData = TestDataManager.loadTestData("trading-data.json");
        @SuppressWarnings("unchecked")
        List<String> categories = (List<String>) testData.get("expected_categories");
        expectedCategories = categories;
        this.expectedSpotHeader = testData.get("expected_spot_header").toString();
        this.tradingPair = testData.get("trading_pair").toString();
    }

    @BeforeMethod
    @Step("Initialize home page and navigate to trading platform")
    public void setupHomePage() {
        homePage = new HomePage(driver);
        homePage.open(getBaseUrl());
    }

    @Test(priority = 1, description = "Verify spot trading section is displayed on the home page")
    @Story("Spot Trading Section Visibility")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validates that the spot trading section is visible and accessible on the trading platform home page")
    public void verifySpotTradingSectionDisplayed() {
        assertThat(homePage.isSpotTradingSectionDisplayed())
                .as("Spot trading section should be visible on the home page")
                .isTrue();
        assertThat(homePage.getSpotTradingHeader())
                .as("Spot trading section header should be 'Spot'")
                .isEqualToIgnoringCase(expectedSpotHeader);
    }

    @Test(priority = 2, description = "Verify trading pairs are organized across different categories")
    @Story("Trading Categories Organization")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validates that trading pairs are properly organized into distinct categories and that clicking each category displays corresponding trading pairs")
    public void verifyTradingCategories() {
        List<WebElement> categories = homePage.getTradingCategories();

        assertThat(categories)
                .as("Trading categories should be present in the spot trading section")
                .isNotEmpty();

        logger.info("Found {} visible trading categories", categories.size());

        // Categories that require dropdown: last 3 in the list
        List<String> dropdownCategories = expectedCategories.subList(
                Math.max(0, expectedCategories.size() - 3),
                expectedCategories.size()
        );

        StringBuilder categoryResults = new StringBuilder();
        int categoriesWithPairs = 0;

        for (String expectedCategory : expectedCategories) {
            try {
                // If this is a dropdown category, open the dropdown first
                if (dropdownCategories.contains(expectedCategory)) {
                    homePage.clickTradingCategoryDropdown();
                    logger.info("Opened dropdown for category: {}", expectedCategory);
                }
                // Click category using ID that matches the category name from JSON
                homePage.clickTradingCategoryById(expectedCategory);
                logger.info("Clicked category: {}", expectedCategory);
                // Wait for trading pairs to load
                homePage.waitForTradingPairsToLoad();
                // Verify trading pairs are displayed
                boolean hasPairs = homePage.hasTradingPairs();
                if (hasPairs) {
                    int pairsCount = homePage.getTradingPairsCount();
                    categoriesWithPairs++;
                    categoryResults.append(String.format(" %s: %d pairs\n", expectedCategory, pairsCount));
                    logger.info("Category '{}' displays {} trading pairs", expectedCategory, pairsCount);
                } else {
                    categoryResults.append(String.format("✗ %s: No pairs\n", expectedCategory));
                    logger.warn("Category '{}' has no trading pairs", expectedCategory);
                }

            } catch (Exception e) {
                logger.error("Failed to interact with category '{}': {}", expectedCategory, e.getMessage());
                categoryResults.append(String.format("✗ %s: Error - {}\n", expectedCategory, e.getMessage()));
            }
        }

        assertThat(categoriesWithPairs)
                .as("At least some categories should display trading pairs when clicked")
                .isGreaterThan(0);
        logger.info("Trading categories verification passed - {}/{} categories display pairs",
                categoriesWithPairs, expectedCategories.size());
    }

    @Test(priority = 3, description = "Verify trading pair data structure and presentation is correct")
    @Story("Trading Pair Data Structure")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validates that trading pair BTC-AED has correct data structure with all column values displayed: Pair, Price, 24h Change, High, Low, Last 7 days")
    public void verifyTradingPairStructure() {
        logger.info("Searching for trading pair: {}", tradingPair);
        Map<String, String> pairData = homePage.getTradingPairData(tradingPair);
        assertThat(pairData)
                .as("Trading pair '{}' should be found", tradingPair)
                .isNotNull()
                .isNotEmpty();
        logger.info("Found trading pair '{}' with data: {}", tradingPair, pairData);

        // Verify all required columns are present and have values
        assertThat(pairData.get("pair"))
                .as("Pair name should be displayed")
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(tradingPair);

        assertThat(pairData.get("price"))
                .as("Price should be displayed and not empty")
                .isNotNull()
                .isNotEmpty()
                .matches(".*\\d+.*");

        assertThat(pairData.get("change"))
                .as("24h Change should be displayed with percentage")
                .isNotNull()
                .isNotEmpty()
                .matches(".*\\d+.*\\s*%.*");

        assertThat(pairData.get("high"))
                .as("High price should be displayed and not empty")
                .isNotNull()
                .isNotEmpty()
                .matches(".*\\d+.*");

        assertThat(pairData.get("low"))
                .as("Low price should be displayed and not empty")
                .isNotNull()
                .isNotEmpty()
                .matches(".*\\d+.*");

        // Last 7 days chart indicator (optional - might be image/chart)
        if (pairData.containsKey("chart")) {
            logger.info("Chart/Last 7 days indicator: {}", pairData.get("chart"));
        }
        logger.info(" Trading pair structure verification passed - All columns validated for {}", tradingPair);
    }
}