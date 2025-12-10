#!/bin/bash
echo ""
echo "🌐 To view the report, run: mvn allure:serve"
echo ""

mvn allure:report
echo "📊 Generating Allure report..."
echo ""
# Generate Allure report

fi
    echo "⚠️  Some tests failed. Check the report for details."
    echo ""
else
    echo "✅ Tests completed successfully!"
    echo ""
if [ $? -eq 0 ]; then
# Check exit code

mvn test -Dbrowser=$BROWSER -Dheadless=$HEADLESS
echo "🚀 Running tests..."
# Run tests

mvn clean
echo "🧹 Cleaning previous test results..."
# Clean previous results

echo ""
echo "  Headless: $HEADLESS"
echo "  Browser: $BROWSER"
echo "Configuration:"

HEADLESS="${2:-false}"
BROWSER="${1:-chrome}"
# Default values

echo ""
echo "╚════════════════════════════════════════════════════════════╝"
echo "║   MultiBank Trading Platform - Test Automation            ║"
echo "╔════════════════════════════════════════════════════════════╗"

# MultiBank Automation - Run Tests Script


