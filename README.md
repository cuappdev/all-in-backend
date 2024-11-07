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

Create a new `application.properties` file located in src/main/resources/ by copying the `application.properties.template` file in the same directory. Fill in the values for the fields below with your MySQL credentials:

- spring.datasource.username=
- spring.datasource.password=

Change `spring.datasource.url=` if you are not hosting a MySQL instance locally.

### 3. Set up the Database

Enter the MySQL Shell by running:

mysql -u {MYSQL USERNAME} -p

Create the all_in database with:

CREATE DATABASE all_in;

### 4. Running the project

Run:

- mvn -N wrapper:wrapper
- pip install pre-commit
- ./mvnw spring-boot:run

### 5. Documentation

Swagger Docs API Documentation can be found at /swagger-ui/index.html
