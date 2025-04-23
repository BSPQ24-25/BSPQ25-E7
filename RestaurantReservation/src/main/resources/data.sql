INSERT INTO user (email, username, phone, password, user_type) VALUES
('cliente1@email.com', 'Ana García', '611111111', '1234', 'CUSTOMER'),
('cliente2@email.com', 'Carlos Ruiz', '622222222', '1234', 'CUSTOMER'),
('admin1@restaurante.com', 'Admin Principal', '633333333', '1234', 'ADMIN'),
('gerente@restaurante.com', 'Gerente Sucursal', '644444444', '1234', 'ADMIN');

INSERT INTO restaurant (restaurant_id, name, availability_hours, capacity)
VALUES (1, 'Cosa Nostra', '12:00-15:00,19:30-23:00', 30);


INSERT INTO restaurant_table (table_id, restaurant_id, capacity, state)
VALUES 
(1, 1, 2, 'available'),
(2, 1, 2, 'available'),
(3, 1, 4, 'available'),
(4, 1, 4, 'occupied'),
(5, 1, 6, 'available'),
(6, 1, 6, 'available');