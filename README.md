# Spring Boot HR Management Demo

This is a sample web application built with Spring Boot that serves as a user interface for the classic Oracle HR sample schema. It provides basic CRUD (Create, Read, Update, Delete) functionality for employees and allows users to view lists of departments and jobs. It also includes a simple analytics dashboard.

## Features

* **Employee Management**:
  * View a paginated list of all employees.
  * Add a new employee to the database.
  * Edit the details of an existing employee.
  * View the details of a single employee.
  * Delete an employee.
* **Department & Job Views**:
  * View a list of all departments and their managers.
  * View a list of all available job roles.
* **Analytics Dashboard**:
  * A central hub with links to various (sample) analytics reports.
  * Displays key metrics like top earners and department statistics.

## Prerequisites

Before you can run this project, you will need the following installed on your system:

* **Java Development Kit (JDK)**: Version 17 or higher.
* **Apache Maven**: To build and run the project.
* **Oracle Database**: An instance of Oracle Database (like Oracle XE) with the sample HR schema installed and accessible.

## Configuration

The application's connection to the Oracle database is configured in the `src/main/resources/application.properties` file. You will need to update the following properties to match your database environment:

```properties
# Database Configuration
spring.datasource.url=jdbc:oracle:thin:@<YOUR_DATABASE_HOST>:<PORT>/<SERVICE_NAME>
spring.datasource.username=hr
spring.datasource.password=hr
```

* Replace `<YOUR_DATABASE_HOST>` with the hostname or IP address of your Oracle database server (e.g., `localhost` or `oracle-xe`).
* Replace `<PORT>` with the port your Oracle listener is running on (typically `1521`).
* Replace `<SERVICE_NAME>` with the service name for your pluggable database (e.g., `XEPDB1` for Oracle XE).

## How to Run

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd backend-springboot
   ```

2. **Build the project**:
   Use the Maven wrapper to compile the application and download all necessary dependencies.
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**:
   Once the build is complete, you can start the application with the following command:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application**:
   Open your web browser and navigate to `http://localhost:8080`. You will be redirected to the employee list, which is the main page of the application.

## Technologies Used

* **Backend**:
  * Spring Boot 3
  * Spring Data JPA
  * Hibernate
* **Frontend**:
  * Thymeleaf
  * Bootstrap 5
* **Database**:
  * Oracle Database
* **Build Tool**:
  * Apache Maven
