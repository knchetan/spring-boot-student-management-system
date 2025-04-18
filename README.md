# Student Management System

## Overview
This project is a Spring Boot–based Student Management System that demonstrates a layered architecture using Spring Boot, Spring Data JPA, Spring Security (JWT-based), and a MySQL backend. The application allows you to manage students, grades, memberships, and activities. It also includes secure login with token-based authentication and robust validation.

## Features
- **Student Management:** Register, view, update, and delete student records.
- **Grade Management:** Manage student grades and standards.
- **Membership Management:** Handle one-to-one student membership details.
- **Activity Management:** Manage many-to-many associations between students and activities.
- **Spring Security with JWT:** Secure endpoints with token-based authentication. Only authenticated users can access protected routes.
- **Role-based Authentication:** Users have roles (e.g., `ROLE_ADMIN`) stored in the database.
- **Validation:** DTO-based validation with custom rules for fields and logic.
- **Logging:** Uses SLF4J with Logback for detailed request and error logging.

## Technologies Used
- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- MySQL
- Logback (via SLF4J)
- Maven

## Prerequisites
- JDK 17 or later
- Maven 3.6+
- MySQL database

## Configuration
1. **Database Configuration:**  
   Update `src/main/resources/application.properties` with your MySQL connection details:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/student_spring?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=####
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   ```

2. **JWT Token Configuration:**
   Tokens are generated via `/auth/login` with valid credentials and must be sent in the `Authorization` header as:
   ```
   Authorization: Bearer <your_token>
   ```

3. **Initial User Creation:**
   A `DataInitializer` creates a default `admin` user with encoded password (`admin123`) on startup. You can log in and access protected endpoints using this.

## API Authentication Flow
- `POST /auth/login` – returns JWT token
- Use the JWT token to access endpoints like:
  - `GET /students`
  - `POST /grades`
  - `DELETE /activities/{id}`

## Validation Highlights
- Field-level validation using annotations (e.g., `@NotBlank`, `@Email`, `@Pattern`)
- Nested DTO validations (e.g., validating membership inside student)

## Logging
- Errors and successful operations are logged using SLF4J with appropriate log levels.
- Log output is saved to `logs/springboot-app.log` as configured in `application.properties`

---

For testing the API, used tools like **Postman** to log in, obtain the token, and test secured endpoints.