#!/bin/bash

# Cross-Browser Test Execution Script

echo "╔════════════════════════════════════════════════════════════╗"
echo "║   Cross-Browser Test Execution                            ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

BROWSERS=("chrome" "firefox" "edge")
FAILED_BROWSERS=()

for browser in "${BROWSERS[@]}"; do
    echo "═══════════════════════════════════════════════════════════"
    echo "Running tests on: $browser"
    echo "═══════════════════════════════════════════════════════════"

    mvn clean test -Dbrowser=$browser -Dheadless=true

    if [ $? -ne 0 ]; then
        FAILED_BROWSERS+=($browser)
        echo "❌ Tests failed on $browser"
    else
        echo "✅ Tests passed on $browser"
    fi

    echo ""
done

echo "╔════════════════════════════════════════════════════════════╗"
echo "║   Cross-Browser Test Summary                              ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""
echo "Total browsers tested: ${#BROWSERS[@]}"
echo "Failed browsers: ${#FAILED_BROWSERS[@]}"

if [ ${#FAILED_BROWSERS[@]} -gt 0 ]; then
    echo "Failed on: ${FAILED_BROWSERS[*]}"
    exit 1
else
    echo "All browsers passed! ✅"
    exit 0
fi

