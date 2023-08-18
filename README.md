# Student Management System - Backend

This repository contains the backend code for an interactive web application designed to manage student records, lesson scheduling, and payment tracking.

## Table of Contents

- [Project Overview](#project-overview)
- [Installation](#installation)
- [Usage](#usage)
- [Technologies Used](#technologies-used)
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
Before proceeding with the backend setup, ensure you have Docker Compose installed on your machine. If not, you can download and install it from the official Docker website: [Docker Compose Installation Guide](https://docs.docker.com/compose/install/)

Once Docker Compose is installed, you can set up the PostgreSQL database using the provided `docker-compose.dev.yml` file. In the terminal, run:
```
docker-compose -f docker-compose.dev.yml up -d
```
This command will start a PostgreSQL container with the specified configuration and environment variables. The database will be accessible on port 5432, and you can connect to it using the provided credentials: 
- Username: `demo`
- Password: `password`
- Database: `demo`

4. Frontend setup:
Refer to https://github.com/stephen3m/student-management-ui for instructions.

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

## Project Structure

The project directory is organized as follows:

* /src: Contains the source code of the backend application
  * /controllers: Includes Micronaut controllers for handling incoming HTTP requests
  * /services: Contains business logic and service layers
  * /models: Defines data models and request/response structures
  * /databases: Manages database connections and interactions
* /build: Contains build artifacts and compiled code

