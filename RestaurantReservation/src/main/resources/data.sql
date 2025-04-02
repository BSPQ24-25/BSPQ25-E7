-- Insertar un Customer (usuario de tipo 'customer')
INSERT IGNORE INTO `User` (gmail, username, phone, password, user_type) 
VALUES 
('cliente1@email.com', 'Ana García', '123456789', '12345678A', 'customer');

-- Insertar un Admin (usuario de tipo 'admin')
INSERT IGNORE INTO `User` (gmail, username, phone, password, user_type) 
VALUES 
(NULL, 'admin', '987654321', 'admin123', 'admin');

-- Insertar un Restaurant
INSERT IGNORE INTO Restaurant (name, availabilityHours, capacity) 
VALUES 
('Cosa Nostra', '12:00-16:00, 20:00-00:00', 50);

-- Insertar un Table (relacionado con Restaurant)
INSERT IGNORE INTO RestaurantTable (restaurantId, capacity, state) 
VALUES 
(1, 4, 'available');

-- Insertar una Reservation (relacionado con User y RestaurantTable)
INSERT IGNORE INTO Reservation (customerId, tableId, date, hour, nPeople, state) 
VALUES 
(1, 1, '2023-12-15', '20:30:00', 4, 'confirmed');
