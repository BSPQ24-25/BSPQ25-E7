CREATE DATABASE IF NOT EXISTS cosanostradb;
USE cosanostradb;

CREATE TABLE IF NOT EXISTS Customer (
    customerId INT AUTO_INCREMENT PRIMARY KEY,
    mail VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50),
    tif VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS Admin (
    adminId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Restaurant (
    restaurantId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    availabilityHours VARCHAR(255),
    capacity INT
);

CREATE TABLE IF NOT EXISTS `Table` (
    tableId INT AUTO_INCREMENT PRIMARY KEY,
    restaurantId INT,
    capacity INT NOT NULL,
    state VARCHAR(50) DEFAULT 'available',
    FOREIGN KEY (restaurantId) REFERENCES Restaurant(restaurantId)
);

CREATE TABLE IF NOT EXISTS Reservation (
    reservationId INT AUTO_INCREMENT PRIMARY KEY,
    customerId INT,
    tableId INT,
    date DATE NOT NULL,
    hour TIME NOT NULL,
    nPeople INT NOT NULL,
    state VARCHAR(50) DEFAULT 'confirmed',
    FOREIGN KEY (customerId) REFERENCES Customer(customerId),
    FOREIGN KEY (tableId) REFERENCES `Table`(tableId)
);

INSERT IGNORE INTO Customer (mail, name, type, tif) VALUES 
('cliente1@email.com', 'Ana García', 'regular', '12345678A');

INSERT IGNORE INTO Admin (username, password) VALUES 
('admin1', 'admin123');

INSERT IGNORE INTO Restaurant (name, availabilityHours, capacity) VALUES 
('Cosa Nostra', '12:00-16:00, 20:00-00:00', 50);

INSERT IGNORE INTO `Table` (restaurantId, capacity, state) VALUES 
(1, 4, 'available');

INSERT IGNORE INTO Reservation (customerId, tableId, date, hour, nPeople, state) VALUES 
(1, 1, '2023-12-15', '20:30:00', 4, 'confirmed');