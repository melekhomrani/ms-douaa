# 🌟 Cosmetics Microservices Platform

A comprehensive Spring Boot microservices architecture designed for a cosmetics management system, featuring service discovery, configuration management, API gateway, authentication, monitoring, and event-driven communication.

## 📌 Project Overview

This project implements a modern microservices architecture for a cosmetics management platform. It includes multiple business services (brand management, product management, notifications) along with essential infrastructure components like service registry, configuration server, API gateway, and comprehensive monitoring.

### 🏗️ Architecture Components

- **🔧 Infrastructure Services**: Config Server, Eureka Registry, API Gateway
- **🔐 Security**: Keycloak-based authentication with OAuth2/JWT
- **💼 Business Services**: Brand Service, Product Service, Notification Service
- **📊 Data Layer**: MySQL (brands), MongoDB (products), PostgreSQL (Keycloak)
- **🔄 Event Streaming**: Apache Kafka for async communication
- **📈 Monitoring**: Prometheus, Grafana, health checks

### 🛠️ Technology Stack

- **Backend**: Java 17, Spring Boot 3.5.3, Spring Cloud 2025.0.0
- **Authentication**: Node.js service with Keycloak integration
- **Databases**: MySQL 8.0, MongoDB, PostgreSQL
- **Message Broker**: Apache Kafka with Zookeeper
- **Monitoring**: Prometheus, Grafana, Spring Actuator
- **Containerization**: Docker, Docker Compose
- **Build Tools**: Maven
- **Repository**: Nexus

## ⚙️ Build Instructions

### Prerequisites
- Java 17+
- Maven 3.6+
- Docker & Docker Compose
- Node.js (for authentication service)

### 🔨 Build All Services

```bash
# Build all Java microservices
mvn clean install

# Or build individual services
cd brand-service && mvn clean install
cd product-service && mvn clean install
cd notification-service && mvn clean install
cd config-server && mvn clean install
cd eureka-server && mvn clean install
cd gateway && mvn clean install
```

### 📦 Build Docker Images

```bash
# Build all Docker images
docker-compose build

# Or build specific services
docker-compose build config-server
docker-compose build eureka-server
docker-compose build gateway
docker-compose build brand-service
docker-compose build product-service
docker-compose build notification-service
docker-compose build authentication-app
```

## 🐳 Run Instructions (Docker)

### 🚀 Startup Sequence

**Important**: Services must be started in the correct order to ensure proper dependency resolution.

#### 1️⃣ Start Infrastructure Services

```bash
# 1. Start Configuration Server (must be first)
docker-compose up -d config-server
```

```bash
# 2. Start Service Registry
docker-compose up -d eureka-server
```

#### 2️⃣ Start Databases

```bash
# 3. Start all databases
docker-compose up -d keycloakdb branddb productdb
```

#### 3️⃣ Start Message Broker

```bash
# 4. Start Kafka ecosystem
docker-compose up -d zookeeper
docker-compose up -d kafka
```

#### 4️⃣ Start Security & Gateway

```bash
# 5. Start Keycloak for authentication
docker-compose up -d keycloak

# 6. Start Authentication Service
docker-compose up -d authentication-app

# 7. Start API Gateway
docker-compose up -d gateway
```

#### 5️⃣ Start Business Services

```bash
# 8. Start business microservices
docker-compose up -d brand-service
docker-compose up -d product-service
docker-compose up -d notification-service
```

#### 6️⃣ Start Monitoring & Tools

```bash
# 9. Start monitoring stack
docker-compose up -d prometheus
docker-compose up -d grafana

# 10. Start additional tools
docker-compose up -d kafdrop    # Kafka UI
docker-compose up -d mail-dev   # Email testing
docker-compose up -d nexus      # Maven repository
```

### 🏃‍♂️ Quick Start (All Services)

```bash
# If you want to start everything at once (not recommended for production)
docker-compose up -d
```

### 🔍 Health Check

```bash
# Verify all services are running
docker-compose ps

# Check service logs
docker-compose logs -f <service-name>
```

## 🔍 Service Descriptions

| Service | Port | Purpose | Technology | Database |
|---------|------|---------|------------|----------|
| **Config Server** | 8888 | Centralized configuration management | Spring Cloud Config | - |
| **Eureka Server** | 8761 | Service registry and discovery | Netflix Eureka | - |
| **API Gateway** | 8989 | Single entry point, routing, load balancing | Spring Cloud Gateway | - |
| **Authentication** | 5050 | User authentication and token management | Node.js + Keycloak | - |
| **Brand Service** | 8083 | Brand/brand CRUD operations | Spring Boot + JPA | MySQL |
| **Product Service** | 8084 | Product catalog management | Spring Boot + MongoDB | MongoDB |
| **Notification Service** | 8040 | Email notifications via Kafka events | Spring Boot + Mail | - |
| **Keycloak** | 18080 | Identity and Access Management | Keycloak | PostgreSQL |
| **Prometheus** | 9090 | Metrics collection and monitoring | Prometheus | - |
| **Grafana** | 3000 | Metrics visualization dashboards | Grafana | - |
| **Kafka** | 9092 | Event streaming platform | Apache Kafka | - |
| **Kafdrop** | 9009 | Kafka topics and messages UI | Kafdrop | - |
| **MailDev** | 1080 | Email testing interface | MailDev | - |
| **Nexus** | 8081 | Maven artifact repository | Sonatype Nexus | - |

### 📊 Database Services

| Database | Port | Purpose | Credentials |
|----------|------|---------|-------------|
| **MySQL (Brand DB)** | 3306 | Brand data storage | root/root |
| **MongoDB (Product DB)** | 27017 | Product data storage | root/root |
| **PostgreSQL (Keycloak)** | 5432 | Identity management | keycloak/password |

## 🔑 Access Information

### 🌐 Service URLs

- **API Gateway**: http://localhost:8989
- **Eureka Dashboard**: http://localhost:8761
- **Keycloak Admin**: http://localhost:18080 (admin/admin)
- **Grafana Dashboard**: http://localhost:3000 (admin/admin)
- **Prometheus**: http://localhost:9090
- **Kafka UI (Kafdrop)**: http://localhost:9009
- **Mail Testing**: http://localhost:1080
- **Nexus Repository**: http://localhost:8081

### 🔗 API Endpoints

- **Brand API**: http://localhost:8989/api/brands/**
- **Product API**: http://localhost:8989/api/products/**
- **Authentication**: http://localhost:5050/token/**

### 🔐 Authentication Tutorial

To authenticate and get a JWT token from Keycloak via the authentication service:

#### Using cURL:

```bash
# Get JWT token using username/password
curl -X POST http://localhost:5050/token/client-credentials \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=douaa&password=melek"
```

#### Response:
```json
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "token_type": "Bearer",
  "expires_in": 300
}
```

#### Using the Token:
```bash
# Use the token in API requests
curl -X GET http://localhost:8989/api/brands \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN_HERE"
```

**📝 Credentials:**
- **Username**: `douaa`
- **Password**: `melek`

### 🎯 API Usage Examples

#### 1️⃣ Create a Brand

```bash
# First, get your authentication token
TOKEN=$(curl -s -X POST http://localhost:5050/token/client-credentials \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=douaa&password=melek" | jq -r '.access_token')

# Create a new brand
curl -X POST http://localhost:8989/api/brands \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "L'\''Oreal",
    "description": "French cosmetics brand",
    "country": "France",
    "website": "https://www.loreal.com"
  }'
```

#### Response:
```json
{
  "id": 1,
  "name": "L'Oreal",
  "description": "French cosmetics brand",
  "country": "France",
  "website": "https://www.loreal.com",
  "createdAt": "2025-07-16T10:30:00Z"
}
```

#### 2️⃣ Create a Product (with brandId)

```bash
# Create a product using the brand ID from the previous step
curl -X POST http://localhost:8989/api/products \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Revitalift Day Cream",
    "description": "Anti-aging day cream with SPF 30",
    "price": 29.99,
    "category": "Skincare",
    "brandId": 1,
    "stock": 150
  }'
```

#### Response:
```json
{
  "id": "64f8b2c4e8d1a2b3c4d5e6f7",
  "name": "Revitalift Day Cream",
  "description": "Anti-aging day cream with SPF 30",
  "price": 29.99,
  "category": "Skincare",
  "brandId": 1,
  "stock": 150,
  "createdAt": "2025-07-16T10:35:00Z"
}
```

#### 3️⃣ Get All Brands

```bash
curl -X GET http://localhost:8989/api/brands \
  -H "Authorization: Bearer $TOKEN"
```

#### 4️⃣ Get All Products

```bash
curl -X GET http://localhost:8989/api/products \
  -H "Authorization: Bearer $TOKEN"
```

**⚠️ Important Notes:**
- **Always create brands first** before creating products
- **Use the brand `id`** from the response when creating products
- **brandId is required** for all products
- **Email notifications** will be sent to MailDev (http://localhost:1080) for both operations

### 📈 Monitoring

- **Health Checks**: All services expose `/actuator/health`
- **Metrics**: Available at `/actuator/prometheus`
- **Service Discovery**: View registered services at Eureka dashboard

### 📧 Email Testing with MailDev

The platform includes **MailDev** for testing email notifications:

1. **Access MailDev UI**: Open http://localhost:1080 in your browser
2. **Email Notifications**: When you create or update brands/products, notification emails are sent via Kafka events
3. **View Emails**: All emails sent by the notification service will appear in the MailDev interface
4. **No SMTP Required**: MailDev captures emails without needing real SMTP configuration

**📝 Important**: 
- **Products must have a valid `brandId`** that references an existing brand in the brand service
- Create brands first, then use their IDs when creating products
- The system will trigger email notifications for both brand and product operations

## 🛠️ Development Features

- **🔄 Hot Reload**: Spring Boot DevTools enabled
- **📝 API Documentation**: OpenAPI/Swagger integration
- **🔍 Circuit Breaker**: Resilience4j for fault tolerance
- **📊 Distributed Tracing**: Micrometer integration
- **🚨 Error Handling**: Global exception handling
- **✅ Validation**: Request/response validation

## 🔧 Configuration

Services are configured via Spring Cloud Config Server with environment-specific profiles:

- **Default Profile**: Docker/container environment
- **Dev Profile**: Local development environment

Configuration files are located in `config-server/src/main/resources/configurations/`

## 📋 Prerequisites for Development

- Docker 
- Java 17 JDK
- Maven
- Node.js

## 🧾 Credits

**Douaa**
