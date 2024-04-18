package com.json.jsonread;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonResourceChecker {
    public static boolean verifyInputJson(File jsonFile) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        // Read JSON file and parse it into a JsonNode object
        JsonNode rootNode = mapper.readTree(jsonFile);
        //verifies if the structure of the json contains Resource field and all prerequisite fields
        verifyStructure(rootNode);
        // navigates the json nodes to the resource node
        JsonNode resourceNode = getResourceNode(rootNode);
        // Check if the Resource field contains a single asterisk '*'
        if (resourceNode.asText().equals("*")) {
            return false; // Resource field contains a single asterisk
        }
        return true; // Input JSON has the correct format
        // and structure but doesn't contain a single asterisk in the Resource field
    }

    private static void verifyStructure(JsonNode rootNode){
        JsonNode policyDocumentNode = rootNode.path("PolicyDocument");
        if (policyDocumentNode.isMissingNode() || !policyDocumentNode.isObject()) {
            throw new RuntimeException("PolicyDocument field is missing or not an object");
        }

        JsonNode statementNode = policyDocumentNode.path("Statement");
        if (statementNode.isMissingNode() || !statementNode.isArray() || statementNode.isEmpty()) {
            throw new RuntimeException("Statement field is missing, not an array, or empty");
        }

        JsonNode resourceNode = statementNode.get(0).path("Resource");
        if (resourceNode.isMissingNode() || !resourceNode.isTextual()) {
            throw new RuntimeException("Resource field is missing or not a textual value");
        }
    }

    private static JsonNode getResourceNode(JsonNode rootNode){
        JsonNode policyDocumentNode = rootNode.path("PolicyDocument");
        JsonNode statementNode = policyDocumentNode.path("Statement");
        return statementNode.get(0).path("Resource");
    }
    public static void main(String[] args) {
        String resourcePath = "input.json"; // Relative path within the resources directory
        //returns the file with given path in the src/main/resources directory
        File inputFile = FileResourceLoader.getResourceFile(resourcePath, JsonResourceChecker.class);

        // print error if the JSON is missing
        if (inputFile == null) {
            System.err.println("Input JSON file not found: " + resourcePath);
            return;
        }
        try {
            boolean result = verifyInputJson(inputFile);
            System.out.println("Resource field does not contain a single asterisk: " + result);
        } catch (IOException e) {
            System.err.println("Error reading or parsing JSON file: " + e.getMessage());
        }
    }

}
