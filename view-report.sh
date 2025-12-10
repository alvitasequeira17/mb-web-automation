#!/bin/bash

# Generate and serve Allure report

echo "📊 Generating Allure Report..."
echo ""

# Generate report
mvn allure:report

if [ $? -eq 0 ]; then
    echo "✅ Report generated successfully!"
    echo ""
    echo "🌐 Starting Allure server..."
    mvn allure:serve
else
    echo "❌ Failed to generate report"
    exit 1
fi

