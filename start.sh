#!/bin/bash

echo "🚀 Starting Calculator Microservices with Kafka..."

# Stop any existing containers
echo "🛑 Stopping existing containers..."
docker-compose down

# Start Kafka and Zookeeper
echo "📦 Starting Kafka and Zookeeper..."
docker-compose up -d

# Wait for Kafka to be ready
echo "⏳ Waiting for Kafka to be ready..."
sleep 30

# Create Kafka topics
echo "📝 Creating Kafka topics..."
docker exec demo-kafka-1 kafka-topics --create --topic calculation-requests --bootstrap-server kafka:9092 --partitions 1 --replication-factor 1 --if-not-exists
docker exec demo-kafka-1 kafka-topics --create --topic calculation-responses --bootstrap-server kafka:9092 --partitions 1 --replication-factor 1 --if-not-exists

echo "✅ Kafka setup complete!"
echo ""
echo "📋 Next steps:"
echo "1. Start the Calculator module: cd calculator && mvn spring-boot:run"
echo "2. Start the REST module: cd rest && mvn spring-boot:run"
echo "3. Access Kafka UI: http://localhost:8082"
echo "4. Test the API: curl 'http://localhost:8080/api/calculator/add?a=10&b=5'"
echo ""
echo "🔍 To monitor Kafka topics:"
echo "kafka-console-consumer.sh --topic calculation-requests --bootstrap-server localhost:9093"
echo "kafka-console-consumer.sh --topic calculation-responses --bootstrap-server localhost:9093" 