# Dockerfile for Spring Boot Student Management System

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/*.jar student-management.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "student-management.jar"]
