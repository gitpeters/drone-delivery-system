# Drone Delivery System

A RESTful API for managing autonomous drone deliveries of medications.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Database](#database)

## Overview

This system enables the management of a drone fleet for delivering medications to locations that are difficult to access by conventional means. Built as part of the Blusalt interview exercise, it provides a comprehensive set of APIs for drone registration, medication management, and delivery logistics.

## Features

- **Drone Management**
  - Register new drones with specific capabilities
  - Monitor drone battery levels
  - Check drone availability for delivery

- **Medication Delivery**
  - Load medications onto available drones
  - Track medication delivery status (future update)
  - Retrieve loaded medication details

- **Fleet Monitoring**
  - View all registered drones
  - Check battery levels across the fleet
  - Monitor delivery operations (future update)

## Tech Stack

- **Backend**: Spring Boot
- **Database**: H2 (in-memory)
- **ORM**: Spring Data JPA
- **Build Tool**: Maven
- **Java Version**: 17+

## Getting Started

### Prerequisites

- Java 17 or higher installed
- Maven installed

### Installation and Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/gitpeters/drone-delivery-system.git
   ```

2. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the API**
   ```
   Base URL: http://localhost:9000/api/v1
   ```

### Database Access

Access the H2 console for database inspection:
- URL: http://localhost:9000/h2-console
- Driver Class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:droneDb
- Username: sa
- Password: (leave blank)

## API Documentation

Complete API documentation is available on Postman:
[API Documentation](https://documenter.getpostman.com/view/23480248/2sAYdctYuh)

Key endpoints include:
- `/drones` - Drone registration and management
- `/medications` - Medication CRUD operations
- `/drones/{serialNumber}/medications` - Loading medications onto drones
