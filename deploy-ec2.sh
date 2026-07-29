#!/usr/bin/env bash

# Exit immediately if a command exits with a non-zero status
set -e

echo "=== 🚀 Starting EC2 Deployment for Life Designer Backend ==="

# 1. Update System Packages & Install Dependencies
echo "📦 Updating packages..."
sudo apt-get update -y || sudo yum update -y

# 2. Install Docker & Docker Compose if not present
if ! command -v docker &> /dev/null; then
    echo "🐳 Installing Docker..."
    sudo apt-get install -y docker.io docker-compose-v2 || sudo yum install -y docker
    sudo systemctl start docker
    sudo systemctl enable docker
    sudo usermod -aG docker $USER
    echo "✅ Docker installed successfully."
fi

# 3. Ensure Java 21 is present for building Maven JAR
if ! command -v java &> /dev/null; then
    echo "☕ Installing OpenJDK 21..."
    sudo apt-get install -y openjdk-21-jdk || sudo yum install -y java-21-amazon-corretto
fi

# 4. Make Maven Wrapper Executable & Build JAR
echo "🔨 Packaging Spring Boot Application..."
chmod +x ./mvnw
./mvnw clean package -DskipTests

# 5. Build and Launch Containers with Docker Compose
echo "🚀 Launching Docker Containers (PostgreSQL & Spring Boot Backend)..."
docker compose down || true
docker compose up -d --build

# 6. Check Container Status
echo "📊 Checking container status..."
docker compose ps

echo "=========================================================="
echo "🎉 Deployment Complete!"
echo "🌐 API Base: http://$(curl -s ifconfig.me):8080/"
echo "📚 Swagger Docs: http://$(curl -s ifconfig.me):8080/swagger-ui/index.html"
echo "=========================================================="
