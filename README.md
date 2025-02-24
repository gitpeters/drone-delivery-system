# Drone Delivery System

This project is part of Blusalt interview exercise.

---

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Setup Instructions](#setup-instructions)
4. [Usage](#usage)

---

## Introduction

This API is built with **SpringBoot** to provide drone dispatch delivery services to users. The API uses:

- **SpringBoot** for REST.
- **H2** as the in-memory database.
- **JPA** as the ORM to manage database interactions

---

## Features

- Register new drone.
- Load drone with medications.
- Check drone availability.
- Check drone battery level.
- Retrieve loaded medications.
- Manage Medications (create, retrieve, update, delete)

---

## Setup Instructions

### Prerequisites

1. Have java 17 and above installed.
2. Access H2 console at (h2 console)[http://localhost:9000/h2-console]
   use the following credentials:
   ```bash
   Driver Class: org.h2.Driver
   JDBC URL: jdbc:h2:mem:droneDb
   username: sa
   password: <leave blank>

### Installation Steps

1. **Clone the repository**:
   ```bash
   git https://github.com/gitpeters/drone-delivery-system.git
   cd <DroneDeliverySystem>
   ```

4. **Start the application**:
   ```maven
       mvn spring-boot:run
   ```

## Usage

### Test the API

You can the API collection (here) [https://documenter.getpostman.com/view/23480248/2sAYdctYuh] to interact with the API

### Base URL

```bash
       http://localhost:9000/api/v1
```
