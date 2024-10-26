# All In

A Cornell University sports betting app that allows students to place bets on student-athletes.

## Project Description

**All In** is a sports betting app built for Cornell University students, enabling them to place bets on student-athletes' performances. The application integrates with a MySQL database to store and manage betting data and is powered by Spring Boot.

## Prerequisites

Before setting up the project, ensure you have the following installed:

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [MySQL](https://dev.mysql.com/downloads/mysql/) (version 8.0 or later)

## Installation

### 1. Clone the Repository

### 2. Create application.properties

Manually create an application.properties file in src/main/resources/ with the following content:

- spring.datasource.url=jdbc:mysql://localhost:3306/all_in
- spring.datasource.username=your_mysql_username
- spring.datasource.password=your_mysql_password
- spring.jpa.hibernate.ddl-auto=update
- spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

### 3. Set up the Database

Ensure you have a running MySQL instance and create a database named all_in:

CREATE DATABASE all_in;

### 4. Running the project

Run:

- mvn -N wrapper:wrapper
- pip install pre-commit
- zsh start.sh (on Mac)

### 5. Documentation

Swagger Docs API Documentation can be found at /swagger-ui/index.html
