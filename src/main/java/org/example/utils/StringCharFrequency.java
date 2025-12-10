package org.example.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Task 2 2: String Character Frequency
 * Utility to count character frequency in a string, outputting in order of first appearance.
 * <p>
 * Assumptions:
 * - Case sensitive (e.g., 'A' and 'a' are different)
 * - Whitespace and special characters are counted
 * - Input is a standard Java String
 */
public class StringCharFrequency {
    /**
     * Counts character occurrences in a string and returns a formatted result.
     *
     * @param input the input string
     * @return formatted frequency string (e.g., h:1, e:1, l:3, ...)
     */
    public static String countCharFrequency(String input) {
        Map<Character, Integer> freqMap = new LinkedHashMap<>();
        for (char c : input.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            if (result.length() > 0) result.append(", ");
            result.append(entry.getKey()).append(":").append(entry.getValue());
        }
        return result.toString();
    }

    /**
     * Example usage and test
     */
    public static void main(String[] args) {
        String input = "aA@b #c!";
        System.out.println("Input: " + input);
        System.out.println("Output: " + countCharFrequency(input));
    }
}

