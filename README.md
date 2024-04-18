### JsonResourceChecker

The `JsonResourceChecker` class is responsible for verifying the structure and content of a JSON file to ensure that it meets specific criteria. It checks whether the JSON file contains a valid structure with required fields and verifies that the "Resource" field does not contain a single asterisk '*'. The class contains a static method `verifyInputJson(File inputFile)` that when called returns `false` if the File contains a single 'asterisk' in `Resource` field and true if it contains any other content. Method throws a Runtime Exception if the file doesn't follow the AWS::IAM::Role Policy structure.

#### Example AWS::IAM::Role Policy file structure:
```json
{
    "PolicyName": "root",
    "PolicyDocument": {
        "Version": "2012-10-17",
        "Statement": [
            {
                "Sid": "IamListAccess",
                "Effect": "Allow",
                "Action": [
                    "iam:ListRoles",
                    "iam:ListUsers"
                ],
                "Resource": "*"
            }
        ]
    }
}
```
#### Usage
To use the `JsonResourceChecker` class, follow these steps:

1. **Clone the Repository**
   Clone the repository containing the `JsonResourceChecker` class into your local environment.

2. **Input JSON File**
   Place your input JSON file (`input.json`) in the `src/main/resources` directory within the project.

3. **Run the Main Method**
   Open the `JsonResourceChecker` class in your preferred Java development environment (e.g., IntelliJ IDEA, Eclipse).

4. **Run the Main Method**
   Execute the `main` method in the `JsonResourceChecker` class. This will load the input JSON file, verify its structure and content, and print the result to the console.

#### Running the Program
You can run the `JsonResourceChecker` program by executing the `main` method in the `JsonResourceChecker` class. Ensure that your project setup includes the required dependencies (e.g., Jackson ObjectMapper for JSON processing).

```java
public class JsonResourceChecker {

    public static void main(String[] args) {
        String resourcePath = "input.json"; // Relative path within the resources directory
        File inputFile = FileResourceLoader.getResourceFile(resourcePath, JsonResourceChecker.class);

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
```

#### Instructions
1. Ensure the input JSON file (`input.json`) is located in the `src/main/resources` directory.
2. Run the `main` method in the `JsonResourceChecker` class.
3. Check the console output to see if the "Resource" field in the JSON file meets the required criteria.

#### Notes
- Make sure to replace `input.json` with your own JSON file name if it differs.
- The `JsonResourceChecker` class uses the Jackson library (`ObjectMapper`) for JSON parsing. Ensure the library is correctly configured in your project.

### JsonResourceCheckerTest

The `JsonResourceCheckerTest` class is used to test the functionality of the `JsonResourceChecker` class, which verifies the structure and content of JSON files.

#### Test Methods

1. **testVerifyInputJson_ContainingAsteriskReturnFalse**
    - Verifies that `verifyInputJson` method returns `false` when the input JSON file contains a single asterisk ('*') in the "Resource" field.

2. **testVerifyInputJson_NotContainingAsteriskReturnTrue**
    - Verifies that `verifyInputJson` method returns `true` when the input JSON file does not contain a single asterisk in the "Resource" field.

3. **testVerifyInputJson_InvalidJsonMissingStatement**
    - Tests the behavior of `verifyInputJson` method when the input JSON file is missing the "Statement" field, which is required.
    - Expects a `RuntimeException` with a specific error message related to the missing "Statement" field.

4. **testVerifyInputJson_InvalidJsonMissingPolicyDocument**
    - Tests the behavior of `verifyInputJson` method when the input JSON file is missing the "PolicyDocument" field, which is required.
    - Expects a `RuntimeException` with a specific error message related to the missing "PolicyDocument" field.

5. **testVerifyInputJson_InvalidJsonMissingResource**
    - Tests the behavior of `verifyInputJson` method when the input JSON file is missing the "Resource" field within the "Statement" array.
    - Expects a `RuntimeException` with a specific error message related to the missing "Resource" field.

#### Instructions for Running Tests

1. **Setup**
    - Ensure that you have the required dependencies and test framework configured (e.g., JUnit 5).

2. **File Preparation**
    - Prepare the test JSON files (`valid_input.json`, `no_asterisk.json`, `missing_statement.json`, `missing_policy.json`, `missing_resource.json`) and place them in the appropriate location within the resources directory (`src/test/resources`).

3. **Running Tests**
    - Open the `JsonResourceCheckerTest` class in your IDE.
    - Run each test method individually or execute all test methods to validate the behavior of the `JsonResourceChecker` class under different scenarios.
