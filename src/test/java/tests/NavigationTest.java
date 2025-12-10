package tests;

import io.qameta.allure.*;
import org.example.pages.HomePage;
import org.example.utils.TestDataManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
    private Map<String, Object> testData;

    @BeforeClass
    public void loadTestData() {
        this.testData = TestDataManager.loadTestData("navigation-data.json");
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
        @SuppressWarnings("unchecked")
        Map<String, Object> menu = (Map<String, Object>) testData.get("expected_navigation_items");
        assertThat(homePage.isTopNavigationDisplayed())
                .as("Top navigation should be displayed")
                .isTrue();
        String dashboardMenu = (String) menu.get("dashboard");
        String marketsMenu = (String) menu.get("markets");
        String tradeMenu = (String) menu.get("trade");
        String featuresMenu = (String) menu.get("features");
        String aboutUsMenu = (String) menu.get("about_us");
        String supportMenu = (String) menu.get("support");

        assertThat(homePage.isTextDisplayed(dashboardMenu))
                .as("Dashboard menu item should be displayed in the top navigation")
                .isTrue();
        assertThat(homePage.isTextDisplayed(marketsMenu))
                .as("Markets menu item should be displayed in the top navigation")
                .isTrue();
        assertThat(homePage.isTextDisplayed(tradeMenu))
                .as("Trade menu item should be displayed in the top navigation")
                .isTrue();
        assertThat(homePage.isTextDisplayed(featuresMenu))
                .as("Features menu item should be displayed in the top navigation")
                .isTrue();
        assertThat(homePage.isTextDisplayed(aboutUsMenu))
                .as("About Us menu item should be displayed in the top navigation")
                .isTrue();
        assertThat(homePage.isTextDisplayed(supportMenu))
                .as("Support menu item should be displayed in the top navigation")
                .isTrue();
        logger.info("Top navigation menu is displayed with all expected options");
    }

    @Test(priority = 2, description = "Verify navigation items are functional and link to appropriate destinations")
    @Story("Top Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that navigation items are functional and can be interacted with")
    public void verifyNavigationItemsFunctional() {
        @SuppressWarnings("unchecked")
        Map<String, Object> menu = (Map<String, Object>) testData.get("expected_navigation_items");

        logger.info("Verifying navigation items are functional");

        menu.values().forEach(linkTextObj -> {
            String linkText = (String) linkTextObj;
            boolean isFunctional = homePage.isNavigationLinkFunctional(linkText);
            assertThat(isFunctional)
                    .as("Navigation item '%s' should be functional and clickable", linkText)
                    .isTrue();
            logger.info("Navigation item '{}' is functional", linkText);
        });
    }
}

