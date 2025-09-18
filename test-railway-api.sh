#!/bin/bash

echo "Testing Railway Deployed API..."
echo "================================"

# Your Railway deployment URL
BASE_URL="https://dg-backend-production.up.railway.app/api"

echo "1. Testing GET all products (to verify API is accessible):"
echo "--------------------------------------------------------"
curl -X GET "$BASE_URL/products" -H "Accept: application/json" -v

echo -e "\n\n2. Testing JSON POST request:"
echo "-----------------------------"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "prod_name": "Test Product Railway",
    "prod_price": 1500,
    "prod_quantity": 3,
    "prod_description": "This is a test product for Railway deployment"
  }' \
  "$BASE_URL/products" \
  -v

echo -e "\n\n3. Testing GET all products again (to verify product was saved):"
echo "---------------------------------------------------------------"
curl -X GET "$BASE_URL/products" -H "Accept: application/json" -v

echo -e "\n\nTest completed!"
echo "Check the logs in Railway dashboard for detailed debug information."
