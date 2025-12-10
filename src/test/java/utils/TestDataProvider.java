package utils;

import org.example.utils.TestDataManager;
import org.testng.annotations.DataProvider;

import java.util.*;

/**
 * Data Provider class for data-driven testing
 * Provides test data from JSON files to TestNG tests
 */
public class TestDataProvider {

    /**
     * Provides advantage items data for parameterized testing
     * @return Object[][] containing item index and item data
     */
    @DataProvider(name = "advantageItemsData")
    public static Object[][] getAdvantageItemsData() {
        Map<String, Object> testData = TestDataManager.loadTestData("why-multibank-data.json");

        @SuppressWarnings("unchecked")
        Map<String, Object> advantages = (Map<String, Object>) testData.get("advantages");

        @SuppressWarnings("unchecked")
        List<Map<String, String>> items = (List<Map<String, String>>) advantages.get("items");

        List<Object[]> dataList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            dataList.add(new Object[]{i, items.get(i)});
        }

        return dataList.toArray(new Object[0][]);
    }

    /**
     * Provides spot trading features data for parameterized testing
     * @return Object[][] containing feature text
     */
    @DataProvider(name = "spotTradingFeaturesData")
    public static Object[][] getSpotTradingFeaturesData() {
        Map<String, Object> testData = TestDataManager.loadTestData("why-multibank-data.json");

        @SuppressWarnings("unchecked")
        Map<String, Object> spotTrading = (Map<String, Object>) testData.get("spotTrading");

        @SuppressWarnings("unchecked")
        List<String> features = (List<String>) spotTrading.get("features");

        List<Object[]> dataList = new ArrayList<>();
        for (int i = 0; i < features.size(); i++) {
            dataList.add(new Object[]{i + 1, features.get(i)});
        }

        return dataList.toArray(new Object[0][]);
    }
}

