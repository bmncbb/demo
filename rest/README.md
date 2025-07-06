# Calculator REST API

A simple REST API service that provides basic arithmetic operations: addition, subtraction, multiplication, and division.

## Features

- **Addition**: Add two numbers
- **Subtraction**: Subtract two numbers  
- **Multiplication**: Multiply two numbers
- **Division**: Divide two numbers (with zero division protection)
- **Health Check**: Service health monitoring endpoint

## API Endpoints

### Base URL
```
http://localhost:8080/api/calculator
```

### 1. Addition
**GET** `/add`

**Parameters:**
- `a` (double): First operand
- `b` (double): Second operand

**Example:**
```
GET /api/calculator/add?a=5&b=3
```

**Response:**
```json
{
  "operation": "addition",
  "operand1": 5.0,
  "operand2": 3.0,
  "result": 8.0,
  "success": true
}
```

### 2. Subtraction
**GET** `/subtract`

**Parameters:**
- `a` (double): First operand
- `b` (double): Second operand

**Example:**
```
GET /api/calculator/subtract?a=10&b=4
```

**Response:**
```json
{
  "operation": "subtraction",
  "operand1": 10.0,
  "operand2": 4.0,
  "result": 6.0,
  "success": true
}
```

### 3. Multiplication
**GET** `/multiply`

**Parameters:**
- `a` (double): First operand
- `b` (double): Second operand

**Example:**
```
GET /api/calculator/multiply?a=6&b=7
```

**Response:**
```json
{
  "operation": "multiplication",
  "operand1": 6.0,
  "operand2": 7.0,
  "result": 42.0,
  "success": true
}
```

### 4. Division
**GET** `/divide`

**Parameters:**
- `a` (double): First operand (dividend)
- `b` (double): Second operand (divisor)

**Example:**
```
GET /api/calculator/divide?a=15&b=3
```

**Response:**
```json
{
  "operation": "division",
  "operand1": 15.0,
  "operand2": 3.0,
  "result": 5.0,
  "success": true
}
```

**Error Response (Division by Zero):**
```json
{
  "success": false,
  "error": "Division by zero is not allowed"
}
```

### 5. Health Check
**GET** `/health`

**Example:**
```
GET /api/calculator/health
```

**Response:**
```json
{
  "status": "UP",
  "service": "Calculator REST API",
  "version": "1.0.0"
}
```

## Running the Application

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher

### Build and Run
```bash
# Navigate to the rest directory
cd rest

# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will start on port 8080.

### Running Tests
```bash
mvn test
```

## Error Handling

All endpoints include proper error handling:
- Invalid parameters return HTTP 400 (Bad Request)
- Division by zero is specifically handled with a clear error message
- All responses include a `success` field indicating operation status

## CORS Support

The API includes CORS support with `@CrossOrigin(origins = "*")` to allow cross-origin requests from web applications.

## Example Usage with curl

```bash
# Addition
curl "http://localhost:8080/api/calculator/add?a=10&b=5"

# Subtraction
curl "http://localhost:8080/api/calculator/subtract?a=20&b=8"

# Multiplication
curl "http://localhost:8080/api/calculator/multiply?a=6&b=9"

# Division
curl "http://localhost:8080/api/calculator/divide?a=100&b=4"

# Health check
curl "http://localhost:8080/api/calculator/health"
``` 