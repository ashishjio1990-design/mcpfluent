# Add Test Case

Scaffold a new Appium test method in the correct test class with proper tags, Allure annotations, and POM usage.

## Usage
```
/add-test
```

Ask the user for:
1. **Test class** — which existing class to add the test to, or a new class name (e.g. `HelloAppTest`, `SignInTest`)
2. **Test name** — method name in camelCase (e.g. `testVersionBannerVisible`)
3. **Tags** — comma-separated list (e.g. `smoke, regression`)
4. **Description** — what the test verifies

## What to do

1. Find the target test class under `src/test/java/com/fluent/tests/`.
   - If it doesn't exist, create it following the pattern of `HelloAppTest.java`.

2. Add a new `@Test` method following this structure:
   ```java
   @Test
   @Tag("smoke")          // one @Tag per tag provided
   @Story("<story>")
   @Description("<description>")
   @Severity(SeverityLevel.NORMAL)
   public void <testName>() {
       // TODO: implement test steps using pages.helloMainPage() or appropriate page
   }
   ```

3. If the test class is `HelloAppTest`, use `pages.helloMainPage()` for interactions.
   For other classes, use the appropriate page from `PageManager`.

4. Use assertions from `org.junit.jupiter.api.Assertions.*`.

5. If a new page method is needed, add it to the relevant page class under `src/test/java/com/fluent/pages/` and register it in `PageManager` if it's a new page.

6. Show the added method to the user and ask if they want to push to git.
