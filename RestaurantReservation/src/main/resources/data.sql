-- 1. Usuarios (2 clientes y 2 admins)
INSERT INTO user (email, username, phone, password, user_type) VALUES
-- Clientes
('cliente1@email.com', 'Ana García', '611111111', '1234', 'customer'),  -- password: cliente123
('cliente2@email.com', 'Carlos Ruiz', '622222222', '1234', 'customer'), -- password: cliente456
-- Admins
('admin1@restaurante.com', 'Admin Principal', '633333333', '1234', 'admin'),    -- password: admin123
('gerente@restaurante.com', 'Gerente Sucursal', '644444444', '1234', 'admin'); -- password: gerente789

-- 2. Restaurantes (2 ejemplos)
INSERT INTO restaurant (name, availability_hours, capacity) VALUES
('Cosa Nostra Principal', '12:00-16:00, 20:00-00:00', 100),
('Cosa Nostra Sucursal', '13:00-17:00, 20:30-23:30', 80);

-- 3. Mesas (4 ejemplos, 2 por restaurante)
INSERT INTO restaurant_table (restaurant_id, capacity, state) VALUES
-- Mesas para restaurante 1
(1, 2, 'available'),  -- Mesa pequeña
(1, 4, 'reserved'),   -- Mesa estándar (ocupada)
-- Mesas para restaurante 2
(2, 6, 'available'),  -- Mesa grande
(2, 4, 'maintenance');-- En reparación

-- 4. Reservas (2 ejemplos)
INSERT INTO reservation (customer_id, table_id, date, hour, n_people, state) VALUES
-- Reserva activa (cliente 1 en mesa 2 del restaurante 1)
(1, 2, CURDATE() + INTERVAL 3 DAY, '20:30:00', 3, 'confirmed'),
-- Reserva pasada (cliente 2 en mesa 3 del restaurante 2)
(2, 3, CURDATE() - INTERVAL 2 DAY, '21:00:00', 6, 'completed');