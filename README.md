# MultiBank Web UI Automation Framework

## Overview
This project is a production-grade Web UI automation framework for the MultiBank trading platform ([https://trade.multibank.io/](https://trade.multibank.io/)). It is designed for scalability, maintainability, and professional engineering practices, using Java, Selenium WebDriver, TestNG, and Allure for reporting. The framework supports cross-browser execution (Chrome, Firefox, Edge) and is CI-ready with GitHub Actions.

## Features
- **Page Object Model (POM)** for maintainable and reusable code
- **Data-driven testing** with external JSON files
- **Cross-browser support** (Chrome, Firefox, Edge)
- **Robust wait strategies** (explicit waits, no Thread.sleep)
- **Allure reporting** for rich test diagnostics
- **CI/CD integration** with GitHub Actions
- **Parallel execution**

## Prerequisites
- Java 17+
- Maven 3.6+
- Chrome, Firefox, and/or Edge browsers installed (for local runs)
- (Optional) Allure CLI for local report viewing

## Project Structure
```
mb-automation/
├── src/
│   ├── main/java/org/example/pages/   # Page Objects
│   └── test/java/org/example/tests/   # TestNG test classes
├── resources/                         # Test data (JSON, etc.)
├── pom.xml                            # Maven build file
├── .github/workflows/                 # GitHub Actions workflows
├── target/                            # Test output, reports, screenshots
└── README.md                          # This file
```

## Setup Instructions

### 1. Clone the Repository
```sh
git clone https://github.com/your-org/mb-automation.git
cd mb-automation
```

### 2. Install Dependencies
Maven will handle all Java dependencies:
```sh
mvn clean install
```

### 3. Configure Browsers
- Ensure Chrome, Firefox, and/or Edge are installed for local runs.
- Browser selection is controlled via the `-Dbrowser` property (default: chrome).

### 4. Test Data
- Test data is stored in `src/test/resources/` as JSON files.
- Update these files to change expected values or add new data sets.

## Running Tests

### 1. Run All Tests (Default: Chrome)
```sh
mvn clean test
```

### 2. Run Tests in a Specific Browser
```sh
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

### 3. Run a Specific Test Class or Method
```sh
mvn clean test -Dtest=NavigationTest
mvn clean test -Dtest=NavigationTest#verifyTopNavigationDisplayed
```

### 4. Run in Headless Mode
```sh
mvn clean test -Dheadless=true
```

## Allure Reporting

### 1. Generate Allure Report
After test execution:
```sh
mvn allure:report
```

### 2. View Allure Report Locally
```sh
mvn allure:serve
```
Or, if you have Allure CLI:
```sh
allure serve target/allure-results
```

### 3. Allure in CI
- Allure results and reports are uploaded as artifacts in GitHub Actions.
- The report is published to GitHub Pages (see workflow in `.github/workflows/`).

## Troubleshooting
- **Element not found/timeouts:** Ensure the site is accessible and selectors are up to date.
- **Browser not launching:** Check browser installation and driver compatibility.
- **Allure report not generated:** Ensure `target/allure-results` exists after test run.

## License
This project is for demonstration and evaluation purposes.

