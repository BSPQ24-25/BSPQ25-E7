DROP USER IF EXISTS 'spq'@'%';
CREATE USER IF NOT EXISTS 'spq'@'%' IDENTIFIED BY 'spq';

DROP SCHEMA IF EXISTS cosanostradb;
CREATE SCHEMA cosanostradb;

GRANT ALL ON cosanostradb.* TO 'spq'@'%';
FLUSH PRIVILEGES;