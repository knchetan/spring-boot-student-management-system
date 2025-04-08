# Student Management System

## Overview
This project is a Spring Boot–based Student Management System that demonstrates a layered architecture with Spring Boot, Spring Data JPA, and a MySQL backend. The application allows you to manage students, grades, memberships, and activities. It features a console-based UI and uses Log4j2 for logging.

## Features
- **Student Management:** Register, view, update, and delete student records.
- **Grade Management:** Manage student grades and standards.
- **Membership Management:** Handle one-to-one student membership details.
- **Activity Management:** Manage many-to-many associations between students and activities.
- **Logging:** Uses Log4j2 (via SLF4J) for detailed logging.
- **Spring Boot:** Auto-configuration, dependency injection, and transaction management.

## Technologies Used
- Java 8
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL
- Log4j2 (via Spring Boot Starter Log4j2)
- Maven

## Prerequisites
- JDK 1.8 or later
- Maven 3.6+
- MySQL database (ensure it is running and accessible)

## Configuration
1. **Database Configuration:**  
   Update `src/main/resources/application.properties` with your MySQL connection details:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name?useSSL=false&serverTimezone=UTC
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
