# 💳 PayPal Payment Integration System

A Java Spring Boot-based microservices project integrating **PayPal Standard Checkout** APIs to handle order creation, payment capture, and transaction tracking with OAuth 2.0 security and Redis cache optimization. Deployed on **AWS EC2** with **MySQL RDS** and **AWS Secrets Manager**.

---

## 🚀 Features

- **PayPal REST API Integration** (Create & Capture Order)
- **OAuth 2.0 Security** with Client Credentials Grant
- **Payment Lifecycle Tracking** with accurate status transitions
- **Centralized Exception Handling** & Custom Error Codes
- **Redis Caching** for access token optimization
- **Microservices Architecture** with Circuit Breaker for fault tolerance
- **AWS Deployment** (EC2, RDS, Secrets Manager)
- **JUnit & Mockito Tests** ensuring code reliability
- **Design Patterns** (Factory, Builder) for modular scalability
- **Agile Development** using Jira & Scrum practices

---

## 🧩 Tech Stack

| Category | Technologies |
|-----------|---------------|
| **Language** | Java |
| **Frameworks** | Spring Boot, Spring JDBC |
| **Microservices** | Circuit Breaker, Eureka Service Registry |
| **Database** | MySQL (AWS RDS) |
| **Cache** | Redis |
| **Build Tool** | Maven |
| **Testing** | JUnit, Mockito |
| **Cloud** | AWS EC2, RDS, Secrets Manager |
| **Messaging** | ActiveMQ / RabbitMQ (Async Communication) |
| **Version Control** | Git, Bitbucket |
| **Logging** | Slf4J + Logback, Micrometer |
| **IDE** | Eclipse, DBeaver |
| **Project Management** | Jira (Agile / Scrum) |

---

## 🧠 Architecture Overview

Client → API Gateway → PayPal-Provider-Service → PayPal REST API
↓
MySQL (RDS) + Redis Cache


- **paypal-provider-service** handles Create and Capture Order APIs.  
- **Redis** stores access tokens for PayPal REST API calls.  
- **MySQL (RDS)** persists payment transactions.  
- **Micrometer** & **Slf4J** handle distributed logging and performance tracking.  

---

## ⚙️ Setup & Installation

### Prerequisites
- JDK 17
- Maven 3.9+
- MySQL / RDS database
- PayPal Sandbox account credentials
- AWS account (optional for deployment)

### Steps
1. **Clone the repository**
   ```bash
   git clone https://github.com/bansalharshit/paypal-payment-integration-system.git

2. **Configure application.properties**

- spring.datasource.url=jdbc:mysql://localhost:3306/paypal_db
- spring.datasource.username=root
- spring.datasource.password=yourpassword
- paypal.client.id=YOUR_SANDBOX_CLIENT_ID
- paypal.client.secret=YOUR_SANDBOX_SECRET


3. **Build & Run**

- mvn clean package
- mvn clean spring-boot:run


4. **Test APIs using Postman**

- POST /api/v1/paypal/create-order
- POST /api/v1/paypal/capture-order/{orderId}

---

## 🔐 Security

- Uses **OAuth 2.0 Client Credentials** Grant for PayPal API calls.
- **Access Tokens** cached in **Redis** for efficient reuse.
- **Exception Handling** via custom global handler for consistent responses.

---

## 🧪 Testing

- **Unit Tests:** JUnit & Mockito
- **Integration Tests:** Simulated PayPal API calls
- **Code Coverage:** Maintained via Sonar & Jacoco

## ☁️ Deployment

- **AWS EC2** – Used for hosting the Spring Boot microservices ensuring high availability and scalability.  
- **AWS RDS** – Managed relational database service for secure and reliable data persistence.  
- **AWS Secrets Manager** – Safely stored and managed application credentials, API keys, and sensitive configuration data.

---

## 🏆 Achievements

- 🥇 Awarded **STAR Performer** for timely project delivery and active sprint participation.  
- ✅ Achieved **100% accuracy** in payment status tracking through robust validation and lifecycle management.  
- ⚡ Enhanced performance by implementing **Redis caching** for access token reuse and reduced latency in API calls.

---

## 👨‍💻 Author

**Harshit Bansal**  
[LinkedIn](https://www.linkedin.com/in/harshitbansal01/) | [GitHub](https://github.com/bansalharshit)

---

## 📝 License

This project is licensed under the **MIT License** — you are free to use, modify, and distribute with proper attribution.
