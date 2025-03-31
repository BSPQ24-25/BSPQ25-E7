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

-- Crear tabla Customer
CREATE TABLE IF NOT EXISTS Customer (
    customerId INT AUTO_INCREMENT PRIMARY KEY,
    mail VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50),
    tif VARCHAR(20)
);

-- Crear tabla Admin
CREATE TABLE IF NOT EXISTS Admin (
    adminId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Crear tabla Restaurant
CREATE TABLE IF NOT EXISTS Restaurant (
    restaurantId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    availabilityHours VARCHAR(255),
    capacity INT
);

-- Crear tabla Table (asegurarse de que restaurantId sea una clave foránea válida)
CREATE TABLE IF NOT EXISTS `Table` (
    tableId INT AUTO_INCREMENT PRIMARY KEY,
    restaurantId INT,
    capacity INT NOT NULL,
    state VARCHAR(50) DEFAULT 'available',
    FOREIGN KEY (restaurantId) REFERENCES Restaurant(restaurantId) ON DELETE CASCADE
);

-- Crear tabla Reservation
CREATE TABLE IF NOT EXISTS Reservation (
    reservationId INT AUTO_INCREMENT PRIMARY KEY,
    customerId INT,
    tableId INT,
    date DATE NOT NULL,
    hour TIME NOT NULL,
    nPeople INT NOT NULL,
    state VARCHAR(50) DEFAULT 'confirmed',
    FOREIGN KEY (customerId) REFERENCES Customer(customerId) ON DELETE CASCADE,
    FOREIGN KEY (tableId) REFERENCES `Table`(tableId) ON DELETE CASCADE
);
