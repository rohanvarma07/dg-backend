#!/bin/bash

# DG Backend Stop Script
echo "🛑 Stopping DG Backend Server..."

# Stop Spring Boot application
if lsof -Pi :9090 -sTCP:LISTEN -t >/dev/null ; then
    echo "📡 Stopping backend server on port 9090..."
    pkill -f "spring-boot:run"
    sleep 3

    # Verify it stopped
    if ! lsof -Pi :9090 -sTCP:LISTEN -t >/dev/null ; then
        echo "✅ Backend server stopped successfully"
    else
        echo "⚠️  Force killing remaining processes..."
        lsof -ti:9090 | xargs kill -9
    fi
else
    echo "ℹ️  Backend server is not running"
fi

echo "🏁 Backend shutdown complete"
