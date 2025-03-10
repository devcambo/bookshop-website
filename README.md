# Bookshop Website

## Overview

This project is a web application for managing a bookshop. It uses a Spring Boot API for the backend and React.js for the frontend.

## Prerequisites

- Docker and Docker Compose installed on your machine.
- Java 17 installed on your machine.
- Node.js and npm installed on your machine.

## Running the Application

To start the application, simply run the following command:

```bash
docker-compose up
./mvnw spring-boot:run
npm run dev
```

This will launch both the postgres and localstack containers, and the application will be available at http://localhost:8080/swagger-ui.html. Make sure you create the bucket if you want to upload file.

## Backend

The backend is built using Spring Boot and provides a RESTful API for managing books, users, and customers.

## Frontend

The frontend is built using React.js and provides a user-friendly interface for interacting with the bookshop's services.
