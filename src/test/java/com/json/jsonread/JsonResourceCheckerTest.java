package com.json.jsonread;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for testing `JsonResourceChecker` class
 */
public class JsonResourceCheckerTest {
    @Test
    public void testVerifyInputJson_ContainingAsteriskReturnFalse() {
        // valid_input contains good JSON structure and an asterisk in Resource field
        File validJsonFile = FileResourceLoader.getResourceFile("valid_input.json", JsonResourceChecker.class);

        // Test the verifyInputJson method with the valid JSON file and asterisk in Resource field
        try {
            boolean result = JsonResourceChecker.verifyInputJson(validJsonFile);
            assertFalse(result);
        } catch (IOException e) {
            fail("Unexpected IOException thrown for valid JSON file");
        }
    }

    @Test
    public void testVerifyInputJson_NotContainingAsteriskReturnTrue() {
        // no_asterisk.json contains good JSON structure and no asterisk in Resource field
        File validJsonFile = FileResourceLoader.getResourceFile("no_asterisk.json", JsonResourceChecker.class);

        // Test the verifyInputJson method with the valid JSON file and no asterisk in Resource field
        try {
            boolean result = JsonResourceChecker.verifyInputJson(validJsonFile);
            assertTrue(result);
        } catch (IOException e) {
            fail("Unexpected IOException thrown for valid JSON file");
        }
    }

    @Test
    public void testVerifyInputJson_InvalidJsonMissingStatement() {
        // Prepare an invalid JSON file for testing (missing required fields)
        File invalidJsonFile = FileResourceLoader.getResourceFile("missing_statement.json", JsonResourceChecker.class);

        // Test the verifyInputJson method with the invalid JSON file
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            JsonResourceChecker.verifyInputJson(invalidJsonFile);
        }, "Expected RuntimeException for invalid JSON file");

        String expectedMessage = "Statement field is missing, not an array, or empty";
        assertEquals(expectedMessage, exception.getMessage(), "message is wrong");
    }

    @Test
    public void testVerifyInputJson_InvalidJsonMissingPolicyDocument() {
        // missing_policy.json contains input with PolicyDocument field missing
        File invalidJsonFile = FileResourceLoader.getResourceFile("missing_policy.json", JsonResourceChecker.class);

        // Testing the verifyInputJson method with the invalid JSON file
       RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            JsonResourceChecker.verifyInputJson(invalidJsonFile);
        }, "Expected RuntimeException for invalid JSON file");

       String expectedMessage = "PolicyDocument field is missing or not an object";
       assertEquals(expectedMessage, exception.getMessage(), "message is wrong");
    }

    @Test
    public void testVerifyInputJson_InvalidJsonMissingResource() {
        // Prepare an invalid JSON file for testing (missing required fields)
        File invalidJsonFile = FileResourceLoader.getResourceFile("missing_resource.json", JsonResourceChecker.class);

        // Test the verifyInputJson method with the invalid JSON file
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            JsonResourceChecker.verifyInputJson(invalidJsonFile);
        }, "Expected RuntimeException for invalid JSON file");

        String expectedMessage = "Resource field is missing or not a textual value";
        assertEquals(expectedMessage, exception.getMessage(), "message is wrong");
    }

}
