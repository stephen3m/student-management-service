# Student Management System - Backend

This repository contains the backend code for an interactive web application designed to manage student records, lesson scheduling, and payment tracking.

## Table of Contents

- [Project Overview](#project-overview)
- [Installation](#installation)
- [Usage](#usage)
- [Technologies Used](#technologies-used)
- [Architecture Diagram](#architecture-diagram)
- [Project Structure](#project-structure)

## Project Overview

The Student Management System backend is responsible for handling data management, processing user requests, and interfacing with the database. It provides essential functionalities for creating, updating, and retrieving student information, lesson schedules, and payment details.

## Installation

Follow these steps to set up and run the backend of the Student Management System:

1. Clone this repository to your local machine:

```
git clone git@github.com:stephen3m/student-management-service.git
```

2. Navigate to the project directory:

```
cd student-management-service
```

3. Backend setup:
* Set up PostgreSQL database
  * Install Docker Compose. You can install it from the official Docker website: [Docker Compose Installation Guide](https://docs.docker.com/compose/install/)
  * Once Docker Compose is installed, you can set up the PostgreSQL database using the provided `docker-compose.dev.yml` file. In the terminal, run:
    ```
    docker-compose -f docker-compose.dev.yml up -d
    ```
    This command will start a PostgreSQL container with the specified configuration and environment variables. The database will be accessible on 
    port 5432, and you can connect to it using the provided credentials:   
    - Username: `demo`
    - Password: `password`
    - Database: `demo`
  * To manage and interact with the PostgreSQL database, follow these steps:
    1. Install DBeaver
    2. Open DBeaver: Launch DBeaver on your machine.
    3. Connect to Database by creating a new database connection using the following settings:
       Host: localhost
       Port: 5432
       Database: demo
       Username: demo
       Password: password
    4. Open SQL Editor: After successfully connecting, open an SQL editor within DBeaver.
    5. Run SQL Queries: Open the file sqlQueries.sql located in the folder src/main/resources/databases of this repository. Run the SQL queries in 
       the file to set up the necessary tables and schema for your application.
* Run Micronaut Application
  * To run the Application.kt file on your preferred IDE, follow these steps:
    1. Install Micronaut
    2. Open the project in your preferred IDE
    3. Locate the Application.kt file and run it using your IDE's run/debug configuration.
       
You've now successfully set up the backend environment, created the necessary database tables, and ran the Micronaut application.

For setting up the frontend part of this project, refer to https://github.com/stephen3m/student-management-ui for instructions.

## Usage

The Student Management System backend provides the following main features:

* Handling requests for student information
* Managing lesson schedules and details
* Tracking payment information and history

## Technologies Used

* Micronaut: A modern, JVM-based, full-stack framework for building modular and maintainable microservices.
* PostgreSQL: A powerful, open-source relational database management system.
* DBeaver: A versatile and user-friendly database management tool, used for designing, managing, and querying databases.
* Insomnia: A comprehensive API testing tool that enables efficient testing, debugging, and documentation of APIs.

## Architecture Diagram
![UML class](https://github.com/stephen3m/student-management-service/assets/96703864/1883db62-2a7c-4567-9890-312268a5703c)

## Project Structure

The project directory is organized as follows:

* /src: Contains the source code of the backend application
  * /controllers: Includes Micronaut controllers for handling incoming HTTP requests
  * /services: Contains business logic and service layers
  * /models: Defines data models and request/response structures
  * /databases: Manages database connections and interactions
* /build: Contains build artifacts and compiled code

