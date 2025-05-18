# 🍽️ Restaurant Reservation System

A full-stack Spring Boot application for managing restaurant reservations, customer reviews, and notifications. Includes user authentication, role-based access control, and responsive frontend views.

## 📚 **Features**

* ✅ User Registration and Login (Spring Security)
* ✅ Reservation Management (Create, Update, Delete)
* ✅ Review System with Star Ratings
* ✅ Notification System for New Reservations and Cancellations
* ✅ Admin Dashboard for Managing Restaurants and Tables
* ✅ Email Notifications for Reservation Confirmations and Cancellations
* ✅ Dockerized with MySQL Database Integration

## 🚀 **Technologies Used**

* Java
* Spring Boot
* Spring Security
* Thymeleaf
* MySQL
* Docker & Docker Compose
* JUnit 5 + Mockito (Unit and Integration Testing)

## 🛠️ **Setup Instructions**

### 1️⃣ **Using Docker (Recommended)**

```bash
docker-compose up --build
```

Access the app at: [http://localhost:8080](http://localhost:8080)

### 2️⃣ **Run Locally**

Ensure you have MySQL running locally and update `application.properties` accordingly.

```bash
mvn spring-boot:run
```

## 📂 **Project Structure**

```plaintext
src/
├── main/java/com/restaurant/reservation/...
├── resources/
│   ├── application.properties
│   └── templates/
└── test/
    └── java/com/restaurant/reservation/...
```

## 👥 **Team Members**

* Asier Castrillejo
* Alejandro Contreras
* Ainhoa Ezkurdia
* Aitor Merodio
* David Pérez
* Tommaso Piccoli
* Eneko Sáez
