-- Eliminar usuario si existe
DROP USER IF EXISTS 'spq'@'%';

-- Crear usuario con acceso
CREATE USER IF NOT EXISTS 'spq'@'%' IDENTIFIED BY 'spq';

-- Eliminar base de datos si existe y crear una nueva
DROP DATABASE IF EXISTS cosanostradb;
CREATE DATABASE cosanostradb;
USE cosanostradb;

-- Dar permisos al usuario
GRANT ALL ON cosanostradb.* TO 'spq'@'%';
FLUSH PRIVILEGES;

-- Crear tabla User (si no existe)
CREATE TABLE IF NOT EXISTS `User` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gmail VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    user_type VARCHAR(50) NOT NULL,
    CONSTRAINT unique_gmail UNIQUE (gmail),
    CONSTRAINT unique_username UNIQUE (username)
);

-- Crear tabla Customer
-- (Los Customer se insertarán en la tabla `User` con `user_type` = 'customer')
CREATE TABLE IF NOT EXISTS Customer (
    customerId BIGINT PRIMARY KEY,
    FOREIGN KEY (customerId) REFERENCES `User`(id) ON DELETE CASCADE
);

-- Crear tabla Admin
-- (Los Admin se insertarán en la tabla `User` con `user_type` = 'admin')
CREATE TABLE IF NOT EXISTS Admin (
    adminId BIGINT PRIMARY KEY,
    FOREIGN KEY (adminId) REFERENCES `User`(id) ON DELETE CASCADE
);

-- Crear tabla Restaurant
CREATE TABLE IF NOT EXISTS Restaurant (
    restaurantId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    availabilityHours VARCHAR(255),
    capacity INT
);

-- Crear tabla RestaurantTable
CREATE TABLE IF NOT EXISTS RestaurantTable (
    tableId INT AUTO_INCREMENT PRIMARY KEY,
    restaurantId INT,
    capacity INT NOT NULL,
    state VARCHAR(50) DEFAULT 'available',
    FOREIGN KEY (restaurantId) REFERENCES Restaurant(restaurantId) ON DELETE CASCADE
);

-- Crear tabla Reservation
CREATE TABLE IF NOT EXISTS Reservation (
    reservationId INT AUTO_INCREMENT PRIMARY KEY,
    customerId BIGINT,
    tableId INT,
    date DATE NOT NULL,
    hour TIME NOT NULL,
    nPeople INT NOT NULL,
    state VARCHAR(50) DEFAULT 'confirmed',
    FOREIGN KEY (customerId) REFERENCES `User`(id) ON DELETE CASCADE,
    FOREIGN KEY (tableId) REFERENCES RestaurantTable(tableId) ON DELETE CASCADE
);

