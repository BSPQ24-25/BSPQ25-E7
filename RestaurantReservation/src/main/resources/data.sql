INSERT INTO restaurant (restaurant_id, name, availability_hours, capacity)
VALUES (1, 'Cosa Nostra', '12:00-15:00,19:30-23:00', 30);


INSERT INTO restaurant_table (table_id, restaurant_id, capacity, state)
VALUES 
(1, 1, 2, 'available'),
(2, 1, 3, 'available'),
(3, 1, 4, 'available'),
(4, 1, 5, 'available'),
(5, 1, 6, 'available'),
(6, 1, 7, 'available');