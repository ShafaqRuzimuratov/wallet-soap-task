# Wallet Management Service (WS)

## Overview

The Wallet Management Service is a Spring Boot application designed to provide a comprehensive solution for managing wallet transactions and balances. It utilizes PostgreSQL as its database, Hibernate JPA for object-relational mapping, and HikariCP for efficient connection pooling. This service is configured to run on port 8200.

## Prerequisites

Before you can run the Wallet Management WS, ensure you have the following installed:

- Java JDK 17 or newer
- Maven (if using Maven as a build tool)
- PostgreSQL

## Configuration

Before running the application, make sure to configure your database settings. The default settings are as follows:

- **URL**: `jdbc:postgresql://localhost:5432/walletmdb`
- **Username**: `postgres`
- **Password**: `yourpass`

You can override these defaults by setting environment variables `DB_URL`, `DB_USER`, and `DB_PASS` respectively.

## Running the Application

### Using Maven

To run the application using Maven, navigate to the project's root directory and execute:

```bash
mvn spring-boot:run
```

### Generating Java Classes from XSD
Our project uses JAXB (Java Architecture for XML Binding) to generate Java classes from XML Schema Definitions (XSD). This allows for easy serialization and deserialization of XML data corresponding to our defined schema.

```bash
mvnw jaxb2:xjc
```

### Accessing the Application (WSDL)
Once the application is running, it can be accessed with this wsdl file

```
http://localhost:8200/ws/wallet-managements.wsdl
```
