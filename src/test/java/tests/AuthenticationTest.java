package tests;

import io.qameta.allure.*;
import org.example.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Test class for Authentication scenarios.
 * Validates visibility and functionality of Login/Sign Up buttons on the home page.
 */
@Epic("MultiBank Trading Platform")
@Feature("Navigation and Layout - User Authentication")
public class AuthenticationTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    @Step("Initialize home page")
    public void setupHomePage() {
        homePage = new HomePage(driver);
        homePage.open(getBaseUrl());
    }

    @Test(priority = 1, description = "Verify login and sign up buttons are displayed")
    @Story("Authentication Buttons")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that Login and Sign Up buttons are visible in the header")
    public void verifyAuthenticationButtonsDisplayed() {
        assertThat(homePage.isLoginButtonDisplayed())
                .as("Login button should be displayed")
                .isTrue();
        assertThat(homePage.isSignUpButtonDisplayed())
                .as("Sign Up button should be displayed")
                .isTrue();
    }

    @Test(priority = 2, description = "Verify authentication buttons are functional")
    @Story("Authentication Buttons")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that Login and Sign Up buttons have valid links")
    public void verifyAuthenticationButtonsFunctional() {
        assertThat(homePage.areAuthButtonsFunctional())
                .as("Authentication buttons should have valid hrefs")
                .isTrue();
    }
}
