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
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    user_type VARCHAR(20) NOT NULL
);

CREATE TABLE restaurant (
    restaurant_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    availability_hours VARCHAR(255),
    capacity INT
);

CREATE TABLE restaurant_table (
    table_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id BIGINT NOT NULL,
    capacity INT NOT NULL,
    state VARCHAR(50) DEFAULT 'available',
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(restaurant_id) ON DELETE CASCADE
);

CREATE TABLE reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    table_id INT NOT NULL,
    date DATE NOT NULL,
    hour TIME NOT NULL,
    n_people INT NOT NULL,
    state VARCHAR(50) DEFAULT 'confirmed',
    FOREIGN KEY (customer_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (table_id) REFERENCES restaurant_table(table_id) ON DELETE CASCADE
);
