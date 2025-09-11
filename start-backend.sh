#!/bin/bash

# DG Backend Startup Script
echo "🚀 Starting DG Backend Server..."

# Check if port 9090 is already in use
if lsof -Pi :9090 -sTCP:LISTEN -t >/dev/null ; then
    echo "⚠️  Port 9090 is already in use. Stopping existing process..."
    pkill -f "spring-boot:run"
    sleep 3
fi

# Start MySQL if not running
echo "🔍 Checking MySQL service..."
if ! pgrep -x "mysqld" > /dev/null; then
    echo "📡 Starting MySQL server..."
    sudo /usr/local/mysql/support-files/mysql.server start
else
    echo "✅ MySQL is already running"
fi

# Start the Spring Boot application
echo "🌟 Starting Spring Boot application on port 9090..."
cd /Users/blackpanther/Desktop/dg-backend
nohup ./mvnw spring-boot:run > backend.log 2>&1 &

# Wait for startup
echo "⏳ Waiting for server to start..."
sleep 15

# Check if server started successfully
if lsof -Pi :9090 -sTCP:LISTEN -t >/dev/null ; then
    echo "✅ Backend server started successfully!"
    echo "🌐 Server running at: http://localhost:9090"
    echo "📊 API Endpoints available:"
    echo "   - Products: http://localhost:9090/api/products"
    echo "   - Categories: http://localhost:9090/api/categories"
    echo "   - Auth: http://localhost:9090/api/auth/login"
    echo "📝 Logs are being written to: backend.log"
else
    echo "❌ Failed to start backend server"
    echo "📋 Check backend.log for error details"
fi
