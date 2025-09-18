#!/bin/bash

echo "Testing Product API endpoints..."
echo "================================"

# Base URL (adjust if your server runs on a different port)
BASE_URL="http://localhost:8080/api"

echo "1. Testing JSON POST request:"
echo "----------------------------"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "prod_name": "Test Product",
    "prod_price": 1000,
    "prod_quantity": 5,
    "prod_description": "This is a test product"
  }' \
  $BASE_URL/products \
  -v

echo -e "\n\n2. Testing Form Data POST request:"
echo "-----------------------------------"
curl -X POST \
  -F "prod_name=Test Product Form" \
  -F "prod_price=2000" \
  -F "prod_quantity=10" \
  -F "prod_description=This is a test product via form data" \
  $BASE_URL/products \
  -v

echo -e "\n\n3. Testing GET all products:"
echo "----------------------------"
curl -X GET $BASE_URL/products -v

echo -e "\n\nTest completed!"
