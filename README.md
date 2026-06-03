# AI Code Reviewer

AI Code Reviewer is a full-stack application built using Java Spring Boot, MySQL, and React. The platform performs static code analysis on uploaded source files and generates quality reports based on code complexity, naming conventions, duplicate code detection, method length analysis, and documentation checks.

The project demonstrates software engineering best practices including layered architecture, REST API development, design patterns, database integration, and frontend dashboard visualization.

---

## Features

### File Upload & Analysis

* Upload Java source code files
* Store uploaded files securely
* Analyze source code automatically

### Static Code Analysis

* Complexity Analysis
* Naming Convention Validation
* Duplicate Code Detection
* Long Method Detection
* Documentation Analysis

### Quality Scoring

* Generate code quality scores
* Identify code smells
* Provide improvement suggestions

### Dashboard

* Total Reports Generated
* Average Quality Score
* Average Complexity Score
* Historical Analysis Reports

### API Documentation

* Swagger/OpenAPI Integration

---

## Technology Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Data JPA
* Hibernate
* MySQL
* Lombok
* Swagger/OpenAPI

### Frontend

* React JS
* Axios
* React Router
* Recharts

### Tools

* Maven
* Git
* GitHub
* Postman

---

## Project Architecture

```text
Frontend (React)
        |
        |
 REST APIs
        |
        |
Spring Boot Backend
        |
        |
 Service Layer
        |
        |
 Analyzer Engine
        |
        |
 Repository Layer
        |
        |
      MySQL
```

---

## Backend Structure

```text
backend/
│
├── controller/
├── service/
├── repository/
├── model/
├── analyzer/
├── config/
└── exception/
```

---

## Frontend Structure

```text
frontend/
│
├── components/
├── pages/
├── services/
├── hooks/
└── charts/
```

---

## Design Patterns Used

### Strategy Pattern

Each analyzer implements a common interface:

```java
CodeAnalyzer
```

Examples:

```java
ComplexityAnalyzer
NamingAnalyzer
DuplicateCodeAnalyzer
LongMethodAnalyzer
DocumentationAnalyzer
```

---

### Factory Pattern

Analyzer objects are created dynamically using:

```java
AnalyzerFactory
```

This allows new analyzers to be added without modifying existing code.

---

## API Endpoints

### Upload File

```http
POST /api/files/upload
```

### Analyze File

```http
POST /api/analysis/{fileId}
```

### Dashboard Statistics

```http
GET /api/dashboard
```

---

## Sample Analysis Result

```json
{
  "complexityScore": 4,
  "qualityScore": 86,
  "issues": [
    "Class student should start with uppercase.",
    "Method exceeds 30 lines."
  ],
  "suggestions": [
    "Follow Java naming conventions.",
    "Break large methods into smaller methods."
  ]
}
```

---

## Database Schema

### uploaded_files

| Column      | Type     |
| ----------- | -------- |
| id          | BIGINT   |
| file_name   | VARCHAR  |
| file_path   | VARCHAR  |
| language    | VARCHAR  |
| upload_time | DATETIME |

### analysis_reports

| Column           | Type     |
| ---------------- | -------- |
| id               | BIGINT   |
| complexity_score | INT      |
| quality_score    | INT      |
| issues           | TEXT     |
| suggestions      | TEXT     |
| created_at       | DATETIME |

---

## Installation

### Clone Repository

```bash
git clone https://github.com/yourusername/AI-Code-Reviewer.git
```

### Configure Database

Create database:

```sql
CREATE DATABASE code_reviewer;
```

Update:

```properties
application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/code_reviewer
spring.datasource.username=root
spring.datasource.password=root
```

### Run Backend

```bash
mvn spring-boot:run
```

Backend:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

### Run Frontend

```bash
npm install
npm run dev
```

Frontend:

```text
http://localhost:5173
```

---

## Future Enhancements

* AI-Powered Review Suggestions
* Cyclomatic Complexity Analysis
* JWT Authentication
* Docker Deployment
* GitHub Integration
* CI/CD Pipeline
* Multi-Language Support
* PDF Report Export

---

## What I Learned

* Spring Boot REST API Development
* JPA & Hibernate
* Software Design Patterns
* Static Code Analysis
* React Frontend Development
* Database Design
* Full Stack Application Architecture
* API Documentation with Swagger

---

## Author

Jasim Mohamed

Aspiring Full Stack Developer focused on Java, Spring Boot, React, Python, AI, and Software Engineering.
