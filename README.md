# 🧗‍♂️ ClimbTracker API

A secure, fully-tested backend REST API built with **Java 25** and **Spring Boot 4.0.2** for tracking rock climbing routes, grades, and personal progression.

This project is designed as a pure backend service, demonstrating modern API architecture, relational database mapping, automated testing, and stateless security.

## 🌐 Live Cloud Demo

The application is fully deployed on the cloud using **Render** and **PostgreSQL**. You can interact with the live API directly through the browser using the built-in Swagger UI documentation.

**👉 [Click here to test the Live API](https://climb-tracker-api.onrender.com/swagger-ui/index.html)**

### How to test the live endpoints:
1. Click the link above to open the Swagger documentation.
2. Click the **"Authorize"** padlock button near the top right.
3. Enter the auto-generated test database credentials:
    * **Username:** `db_admin`
    * **Password:** `database123`
4. Click **Authorize**, then **Close**.
5. You can now open any endpoint (e.g., `GET /api/climbs`), click **"Try it out"**, and click **"Execute"** to see live JSON data coming from the cloud database!

---

## ✨ Key Features

* **Authentication & Authorization:** Implements Spring Security with a custom `UserDetailsService`. Users can securely register, and all climb data is strictly isolated (users can only view, update, and delete their own climbs). Passwords are cryptographically hashed using BCrypt.
* **Relational Database Mapping:** Utilizes Spring Data JPA to manage One-to-Many relationships between `AppUser` and `Climb` entities.
* **Global Exception Handling:** Uses `@RestControllerAdvice` to intercept `@Valid` constraints, returning clean, formatted error messages to the client when invalid data is submitted.
* **Interactive API Documentation:** Integrated with OpenAPI 3 (Swagger) for real-time endpoint testing and schema visualization.
* **Automated Testing Suite:** * **Data Layer:** Uses `@DataJpaTest` configured to run against a real local PostgreSQL instance to ensure true production-parity database testing.
    * **Web Layer:** Uses `@WebMvcTest` and Spring Boot 4's new `@MockitoBean` to perform rapid, isolated testing of controller logic and JSON serialization without starting the full server.

---

## 🛠️ Tech Stack

* **Language:** Java 25
* **Framework:** Spring Boot 4.0.2
* **Database:** PostgreSQL (Used for Production, Local Development, and Automated Testing)
* **Build Tool:** Maven
* **Documentation:** Springdoc OpenAPI (Swagger UI)
* **Containerization:** Docker

---

## 🛣️ API Endpoints

### Users
* `POST /api/users/register` - Register a new account (Public)
* `PUT /api/users/{id}` - Update your password (Requires Auth)
* `DELETE /api/users/{id}` - Delete your account and all associated climbs (Requires Auth)

### Climbs
* `GET /api/climbs` - Get all climbs for the logged-in user
* `POST /api/climbs` - Log a new climb
* `GET /api/climbs/{id}` - Get a specific climb by its ID
* `GET /api/climbs/grade/{grade}` - Filter climbs by difficulty grade (e.g., "V5")
* `PUT /api/climbs/{id}` - Update a climb's details
* `DELETE /api/climbs/{id}` - Delete a specific climb

---

## 🚀 Getting Started Locally

### Prerequisites
* Java 25 installed
* Docker Desktop running

### 1. Start the Local Database
The application requires a PostgreSQL database to run locally. Spin one up instantly using Docker with the credentials expected by the `application.properties` file:
```bash
docker run --name climb-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=postgres -p 5432:5432 -d postgres:latest
```
### 2. Run the Application

Use the included Maven wrapper to start the server. You do not need Maven installed globally on your machine:

```bash
# Mac / Linux
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

### 3. Explore Locally

Navigate to `http://localhost:8080/swagger-ui/index.html` in your browser. The `DatabaseSeeder.java` file will automatically create the `db_admin` account so you can begin testing immediately.