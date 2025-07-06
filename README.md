# Calculator Microservices with Apache Kafka

This project demonstrates a microservices architecture where a REST API communicates with a Calculator service using Apache Kafka for asynchronous messaging.

## Architecture Overview

```
┌─────────────────┐    HTTP     ┌─────────────────┐    Kafka     ┌─────────────────┐
│   REST Client   │ ──────────► │   REST Module   │ ──────────► │ Calculator Module│
│                 │             │   (Port 8080)   │             │   (Port 8081)   │
└─────────────────┘             └─────────────────┘             └─────────────────┘
                                        │                                │
                                        │                                │
                                        ▼                                ▼
                                 ┌─────────────────┐             ┌─────────────────┐
                                 │ Kafka Producer  │             │ Kafka Consumer  │
                                 │ (Requests)      │             │ (Requests)      │
                                 └─────────────────┘             └─────────────────┘
                                        │                                │
                                        │                                │
                                        ▼                                ▼
                                 ┌─────────────────┐             ┌─────────────────┐
                                 │ calculation-    │             │ Calculator      │
                                 │ requests topic  │             │ Service         │
                                 └─────────────────┘             └─────────────────┘
                                        │                                │
                                        │                                │
                                        ▼                                ▼
                                 ┌─────────────────┐             ┌─────────────────┐
                                 │ Kafka Consumer  │             │ Kafka Producer  │
                                 │ (Responses)     │             │ (Responses)     │
                                 └─────────────────┘             └─────────────────┘
                                        │                                │
                                        │                                │
                                        ▼                                ▼
                                 ┌─────────────────┐             ┌─────────────────┐
                                 │ calculation-    │             │ calculation-    │
                                 │ responses topic │             │ responses topic │
                                 └─────────────────┘             └─────────────────┘
```

## Modules

### 1. REST Module (`rest/`)
- **Port**: 8080
- **Purpose**: Provides HTTP REST API endpoints
- **Features**:
  - REST endpoints for calculator operations
  - Kafka producer for sending calculation requests
  - Kafka consumer for receiving calculation responses
  - Asynchronous request-response handling

### 2. Calculator Module (`calculator/`)
- **Port**: 8081
- **Purpose**: Performs actual mathematical calculations
- **Features**:
  - Calculator service with business logic
  - Kafka consumer for receiving calculation requests
  - Kafka producer for sending calculation responses
  - Error handling for division by zero

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- Apache Kafka (running on localhost:9093)

## Setup and Running

### 1. Start Apache Kafka

First, ensure Apache Kafka is running on localhost:9092. You can use Docker:

```bash
# Start Kafka with Docker Compose
docker-compose up -d

# Or start Kafka manually if you have it installed
# Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka
bin/kafka-server-start.sh config/server.properties
```

### 2. Create Kafka Topics

```bash
# Create the request topic
kafka-topics.sh --create --topic calculation-requests --bootstrap-server localhost:9093 --partitions 1 --replication-factor 1

# Create the response topic
kafka-topics.sh --create --topic calculation-responses --bootstrap-server localhost:9093 --partitions 1 --replication-factor 1
```

### 3. Build and Run the Modules

#### Build all modules:
```bash
mvn clean compile
```

#### Run Calculator Module:
```bash
cd calculator
mvn spring-boot:run
```

#### Run REST Module (in a new terminal):
```bash
cd rest
mvn spring-boot:run
```

## API Endpoints

### Base URL
```
http://localhost:8080/api/calculator
```

### Available Operations

#### 1. Addition
```bash
GET /api/calculator/add?a=10&b=5
```

#### 2. Subtraction
```bash
GET /api/calculator/subtract?a=20&b=8
```

#### 3. Multiplication
```bash
GET /api/calculator/multiply?a=6&b=9
```

#### 4. Division
```bash
GET /api/calculator/divide?a=100&b=4
```

#### 5. Health Check
```bash
GET /api/calculator/health
```

## Example Usage

### Using curl:
```bash
# Addition
curl "http://localhost:8080/api/calculator/add?a=10&b=5"

# Subtraction
curl "http://localhost:8080/api/calculator/subtract?a=20&b=8"

# Multiplication
curl "http://localhost:8080/api/calculator/multiply?a=6&b=9"

# Division
curl "http://localhost:8080/api/calculator/divide?a=100&b=4"

# Division by zero (error case)
curl "http://localhost:8080/api/calculator/divide?a=10&b=0"
```

### Example Response:
```json
{
  "operation": "addition",
  "operand1": 10.0,
  "operand2": 5.0,
  "result": 15.0,
  "success": true
}
```

## Kafka Topics

### calculation-requests
- **Purpose**: Sends calculation requests from REST to Calculator module
- **Message Format**: JSON with operation, operands, and request ID

### calculation-responses
- **Purpose**: Sends calculation responses from Calculator to REST module
- **Message Format**: JSON with result, success status, and error information

## Error Handling

- **Division by Zero**: Returns HTTP 400 with error message
- **Invalid Operations**: Returns HTTP 400 with error message
- **Kafka Timeout**: Returns HTTP 400 with timeout error
- **Network Issues**: Returns HTTP 400 with connection error

## Testing

### Run Tests for Calculator Module:
```bash
cd calculator
mvn test
```

### Run Tests for REST Module:
```bash
cd rest
mvn test
```

## Monitoring

### Health Endpoints:
- REST Module: `http://localhost:8080/api/calculator/health`
- Calculator Module: `http://localhost:8081/actuator/health`

### Kafka Monitoring:
You can monitor Kafka topics using:
```bash
# Monitor calculation-requests topic
kafka-console-consumer.sh --topic calculation-requests --bootstrap-server localhost:9093

# Monitor calculation-responses topic
kafka-console-consumer.sh --topic calculation-responses --bootstrap-server localhost:9093
```

## Benefits of This Architecture

1. **Decoupling**: REST and Calculator modules are completely independent
2. **Scalability**: Calculator service can be scaled independently
3. **Reliability**: Kafka provides message persistence and fault tolerance
4. **Asynchronous**: Non-blocking communication between services
5. **Monitoring**: Easy to monitor and debug with Kafka tools

## Troubleshooting

### Common Issues:

1. **Kafka Connection Failed**:
   - Ensure Kafka is running on localhost:9093
   - Check if topics are created

2. **Timeout Errors**:
   - Verify Calculator module is running
   - Check Kafka connectivity

3. **Port Already in Use**:
   - Change ports in application.properties files
   - Kill existing processes using the ports

### Logs:
- REST Module logs: Check console output for REST module
- Calculator Module logs: Check console output for Calculator module
- Kafka logs: Check Kafka server logs for connection issues 