package com.json.jsonread;

import java.io.File;

/**
 * Class used to load a file from src/main/resources directory
 */
public class FileResourceLoader {

    /**
     * @param resourcePath - path relative to src/main/resources directory
     * @param givenClass - any class in the project with resources directory
     * @return file or null if file not found
     */
    public static File getResourceFile(String resourcePath, Class givenClass) {
        // Get the class loader for the current class
        ClassLoader classLoader = givenClass.getClassLoader();

        // Try to load the resource URL
        java.net.URL resourceUrl = classLoader.getResource(resourcePath);
        if (resourceUrl == null) {
            return null; // Resource not found
        }

        // Convert the resource URL to a File object
        try {
            return new File(resourceUrl.toURI());
        } catch (Exception e) {
            System.err.println("Error converting resource URL to file: " + e.getMessage());
            return null;
        }
    }
}
