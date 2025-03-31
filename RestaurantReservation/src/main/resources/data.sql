INSERT IGNORE INTO Customer (mail, name, type, tif) VALUES 
('cliente1@email.com', 'Ana García', 'regular', '12345678A');

INSERT IGNORE INTO Admin (username, password) VALUES 
('admin', 'admin123');

INSERT IGNORE INTO Restaurant (name, availabilityHours, capacity) VALUES 
('Cosa Nostra', '12:00-16:00, 20:00-00:00', 50);

INSERT IGNORE INTO `Table` (restaurantId, capacity, state) VALUES 
(1, 4, 'available');

INSERT IGNORE INTO Reservation (customerId, tableId, date, hour, nPeople, state) VALUES 
(1, 1, '2023-12-15', '20:30:00', 4, 'confirmed');