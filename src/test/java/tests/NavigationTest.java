package tests;

import io.qameta.allure.*;
import org.example.pages.HomePage;
import org.example.utils.TestDataManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for Navigation and Layout validation scenarios.
 * Validates the visibility and functionality of the top navigation menu on the home page.
 */
@Epic("MultiBank Trading Platform")
@Feature("Navigation and Layout")
public class NavigationTest extends BaseTest {

    private HomePage homePage;
    private List<String> expectedNavItems;

    @BeforeClass
    public void loadTestData() {
        Map<String, Object> testData = TestDataManager.loadTestData("navigation-data.json");
        expectedNavItems = getExpectedNavItems(testData);
    }

    @BeforeMethod
    @Step("Initialize home page")
    public void setupHomePage() {
        homePage = new HomePage(driver);
        homePage.open(getBaseUrl());
    }

    @Test(priority = 1, description = "Verify top navigation menu displays correctly with all expected options")
    @Story("Top Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the top navigation menu is displayed with all expected options")
    public void verifyTopNavigationDisplayed() {
        assertThat(homePage.isTopNavigationDisplayed())
                .as("Top navigation should be displayed")
                .isTrue();

        List<String> actualLinks = homePage.getNavigationLinkTexts();
        logger.info("Found navigation links: {}", actualLinks);

        assertThat(actualLinks)
                .as("Navigation should contain all expected items")
                .isNotEmpty()
                .hasSize(expectedNavItems.size())
                .containsExactlyInAnyOrderElementsOf(expectedNavItems);
    }

    @Test(priority = 2, description = "Verify navigation items are functional and link to appropriate destinations")
    @Story("Top Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that navigation items are functional and can be interacted with")
    public void verifyNavigationItemsFunctional() {
        List<String> actualLinks = homePage.getNavigationLinkTexts();

        logger.info("Verifying {} navigation items are functional", actualLinks.size());

        actualLinks.forEach(linkText -> {
            boolean isFunctional = homePage.isNavigationLinkFunctional(linkText);
            assertThat(isFunctional)
                    .as("Navigation item '%s' should be functional and clickable", linkText)
                    .isTrue();
            logger.info("Navigation item '{}' is functional", linkText);
        });
    }

    @SuppressWarnings("unchecked")
    private List<String> getExpectedNavItems(Map<String, Object> testData) {
        return (List<String>) testData.get("expected_navigation_items");
    }
}

