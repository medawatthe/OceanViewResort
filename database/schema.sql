-- Ocean View Resort Database Schema
-- MySQL Database

CREATE DATABASE IF NOT EXISTS ocean_view_resort;
USE ocean_view_resort;

-- Users Table
CREATE TABLE users (
    user_id VARCHAR(20) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Room Types Table
CREATE TABLE room_types (
    room_type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL UNIQUE,
    rate_per_night DECIMAL(10, 2) NOT NULL,
    capacity INT NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Rooms Table
CREATE TABLE rooms (
    room_id VARCHAR(20) PRIMARY KEY,
    room_type_id INT NOT NULL,
    room_number VARCHAR(10) UNIQUE NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (room_type_id) REFERENCES room_types(room_type_id)
);

-- Reservations Table
CREATE TABLE reservations (
    reservation_number VARCHAR(20) PRIMARY KEY,
    guest_name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    room_type VARCHAR(50) NOT NULL,
    number_of_guests INT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'Confirmed',
    special_requests TEXT,
    created_by VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- Bills Table
CREATE TABLE bills (
    bill_number VARCHAR(20) PRIMARY KEY,
    reservation_number VARCHAR(20) NOT NULL,
    guest_name VARCHAR(100) NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    number_of_nights INT NOT NULL,
    room_rate DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    tax DECIMAL(10, 2) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reservation_number) REFERENCES reservations(reservation_number)
);

-- Insert Default Room Types
INSERT INTO room_types (type_name, rate_per_night, capacity, description) VALUES
('Standard', 5000.00, 2, 'Comfortable room with basic amenities'),
('Deluxe', 8000.00, 3, 'Spacious room with ocean view and premium amenities'),
('Suite', 12000.00, 4, 'Luxury suite with living area and panoramic ocean view');

-- Insert Default Admin User (password: admin123)
INSERT INTO users (user_id, username, password, role, full_name, email) VALUES
('USR001', 'admin', 'admin123', 'ADMIN', 'System Administrator', 'admin@oceanview.com'),
('USR002', 'staff1', 'staff123', 'STAFF', 'John Doe', 'john@oceanview.com');

-- Insert Sample Rooms
INSERT INTO rooms (room_id, room_type_id, room_number, is_available) VALUES
('R001', 1, '101', TRUE),
('R002', 1, '102', TRUE),
('R003', 1, '103', TRUE),
('R004', 2, '201', TRUE),
('R005', 2, '202', TRUE),
('R006', 2, '203', TRUE),
('R007', 3, '301', TRUE),
('R008', 3, '302', TRUE);
