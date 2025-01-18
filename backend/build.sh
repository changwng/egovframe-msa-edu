#!/bin/bash

ps -ef | grep build/libs/board-service-1.0.0.jar | grep -v grep | awk '{print $2}' | xargs kill


cd /Users/changwng/workspace.edu/egovframe-msa-edu/backend

echo "Building config service..."
cd config
./gradlew clean build -x test

echo "Building discovery service..."
cd ../discovery
./gradlew clean build -x test

echo "Building apigateway..."
cd ../apigateway
./gradlew clean build -x test

echo "Building board-service..."
cd ../board-service
./gradlew clean build -x test

echo "Building user-service..."
cd ../user-service
./gradlew clean build -x test

echo "Building portal-service..."
cd ../portal-service
./gradlew clean build -x test

echo "Building reserve-check-service..."
cd ../reserve-check-service
./gradlew clean build -x test

echo "Building reserve-item-service..."
cd ../reserve-item-service
./gradlew clean build -x test

echo "Building reserve-request-service..."
cd ../reserve-request-service
./gradlew clean build -x test

echo "Build complete!"




cd ../board-service
./gradlew clean build -x test

cd ../user-service
./gradlew clean build -x test

cd ../portal-service
./gradlew clean build -x test

cd ../reserve-check-service
./gradlew clean build -x test

cd ../reserve-item-service
./gradlew clean build -x test

cd ../reserve-request-service
./gradlew clean build -x test