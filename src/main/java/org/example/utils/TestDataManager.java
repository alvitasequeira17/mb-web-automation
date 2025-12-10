package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Utility class for managing test data from JSON files
 */
public class TestDataManager {
    private static final Logger logger = LoggerFactory.getLogger(TestDataManager.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Load test data from JSON file
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> loadTestData(String fileName) {
        try {
            InputStream inputStream = TestDataManager.class
                    .getClassLoader()
                    .getResourceAsStream("testdata/" + fileName);

            if (inputStream == null) {
                throw new RuntimeException("Test data file not found: " + fileName);
            }

            Map<String, Object> data = objectMapper.readValue(inputStream, Map.class);
            logger.info("Loaded test data from: {}", fileName);
            return data;
        } catch (IOException e) {
            logger.error("Failed to load test data from: {}", fileName, e);
            throw new RuntimeException("Failed to load test data", e);
        }
    }
}


