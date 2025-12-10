package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Configuration reader for loading test configuration from YAML files
 */
public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static TestConfig testConfig;

    /**
     * Load configuration from YAML file
     */
    public static TestConfig getConfig() {
        if (testConfig == null) {
            synchronized (ConfigReader.class) {
                if (testConfig == null) {
                    try {
                        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                        InputStream inputStream = ConfigReader.class
                                .getClassLoader()
                                .getResourceAsStream("config/test-config.yaml");

                        if (inputStream == null) {
                            throw new RuntimeException("test-config.yaml not found in resources");
                        }

                        testConfig = mapper.readValue(inputStream, TestConfig.class);
                        logger.info("Configuration loaded successfully");
                    } catch (IOException e) {
                        logger.error("Failed to load configuration", e);
                        throw new RuntimeException("Failed to load test configuration", e);
                    }
                }
            }
        }
        return testConfig;
    }

    /**
     * Get browser from system property or configuration
     */
    public static String getBrowser() {
        String browser = System.getProperty("browser");
        // Handle null or empty/whitespace-only strings
        if (browser != null && !browser.trim().isEmpty()) {
            return browser.trim();
        }
        return getConfig().getBrowser().getDefaultBrowser();
    }

    /**
     * Check if tests should run in headless mode
     */
    public static boolean isHeadless() {
        String headless = System.getProperty("headless");
        return headless != null ? Boolean.parseBoolean(headless) : getConfig().getBrowser().isHeadless();
    }
}


