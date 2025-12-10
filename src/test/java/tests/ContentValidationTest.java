package tests;

import io.qameta.allure.*;
import org.example.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for Content Validation scenarios
 * Validates the presence and correctness of marketing banners and download links
 */
@Epic("MultiBank Trading Platform")
@Feature("Content Validation")
public class ContentValidationTest extends BaseTest {

    private HomePage homePage;
    private Map<String, Object> testData;

    @BeforeMethod
    public void setupHomePage() {
        homePage = new HomePage(driver);
        homePage.open(getBaseUrl());
    }

    @Test(priority = 1, description = "Verify marketing banners appear at page bottom")
    @Story("Marketing Content")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that marketing banners are displayed at the bottom of the page")
    public void verifyMarketingBannersDisplayed() {
        boolean isDisplayed = homePage.areMarketingBannersDisplayed();
        assertThat(isDisplayed)
                .as("Marketing banners should be displayed at page bottom")
                .isTrue();
        int bannersCount = homePage.getMarketingBannersCount();
        assertThat(bannersCount)
                .as("At least one marketing banner should be present")
                .isGreaterThan(0);
        logger.info("✓ Found {} marketing banners at page bottom", bannersCount);
    }

    @Test(priority = 2, description = "Verify download section is displayed")
    @Story("Download Section")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the download section is present at the page bottom")
    public void verifyDownloadSectionDisplayed() {
        boolean isDisplayed = homePage.isDownloadSectionDisplayed();
        assertThat(isDisplayed)
                .as("Download section should be displayed")
                .isTrue();

        logger.info("Download section is displayed");
    }

    @Test(priority = 3, description = "Verify App Store link is present and clickable")
    @Story("Download Section")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that App Store download link is present and points to Apple App Store")
    public void verifyAppStoreLinkPresentAndValid() {
        boolean isPresent = homePage.isAppStoreLinkPresent();
        assertThat(isPresent)
                .as("App Store link should be present")
                .isTrue();
        String appStoreUrl = homePage.getAppStoreLinkUrl();
        assertThat(appStoreUrl)
                .as("App Store URL should not be null or empty")
                .isNotNull()
                .isNotEmpty();

        boolean isValid = homePage.verifyAppStoreLinkValid();
        assertThat(isValid)
                .as("App Store link should point to Apple/iOS store (contains 'apple.com' or 'app-store' or 'ios')")
                .isTrue();

        logger.info("App Store link is valid: {}", appStoreUrl);
    }

    @Test(priority = 4, description = "Verify Google Play link is present and clickable")
    @Story("Download Section")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that Google Play download link is present and points to Play Store")
    public void verifyGooglePlayLinkPresentAndValid() {
        boolean isPresent = homePage.isGooglePlayLinkPresent();
        assertThat(isPresent)
                .as("Google Play link should be present")
                .isTrue();

        String googlePlayUrl = homePage.getGooglePlayLinkUrl();
        assertThat(googlePlayUrl)
                .as("Google Play URL should not be null or empty")
                .isNotNull()
                .isNotEmpty();

        boolean isValid = homePage.verifyGooglePlayLinkValid();

        assertThat(isValid)
                .as("Google Play link should point to Google Play store (contains 'google.com' or 'play.google' or 'android')")
                .isTrue();

        logger.info("Google Play link is valid: {}", googlePlayUrl);
    }
}

