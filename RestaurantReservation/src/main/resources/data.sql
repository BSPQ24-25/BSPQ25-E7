-- 1. Usuarios (2 clientes y 2 admins)
INSERT INTO user (email, username, phone, password, user_type) VALUES
-- Clientes
('cliente1@email.com', 'Ana García', '611111111', '1234', 'CUSTOMER'),  -- password: cliente123
('cliente2@email.com', 'Carlos Ruiz', '622222222', '1234', 'CUSTOMER'), -- password: cliente456
-- Admins
('admin1@restaurante.com', 'Admin Principal', '633333333', '1234', 'ADMIN'),    -- password: admin123
('gerente@restaurante.com', 'Gerente Sucursal', '644444444', '1234', 'ADMIN'); -- password: gerente789
