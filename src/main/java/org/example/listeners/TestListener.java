package org.example.listeners;

import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener for test execution events
 */
public class TestListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting test: {}", result.getMethod().getMethodName());
        Allure.getLifecycle().updateTestCase(testResult ->
                testResult.setDescription(result.getMethod().getDescription())
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test PASSED: {}", result.getMethod().getMethodName());
        logger.info("Duration: {} ms", result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test FAILED: {}", result.getMethod().getMethodName());
        logger.error("Failure reason: {}", result.getThrowable().getMessage());
        logger.error("Duration: {} ms", result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test SKIPPED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Starting Test Suite: {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Finished Test Suite: {}", context.getName());
        logger.info("Total tests run: {}", context.getAllTestMethods().length);
        logger.info("Passed: {}", context.getPassedTests().size());
        logger.info("Failed: {}", context.getFailedTests().size());
        logger.info("Skipped: {}", context.getSkippedTests().size());
    }
}


