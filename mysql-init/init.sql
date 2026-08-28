CREATE DATABASE IF NOT EXISTS CCSOP_db;
USE CCSOP_db;

-- 1. Table Types
CREATE TABLE Types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(15) NOT NULL UNIQUE
);

-- 2. Table Users
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    firstname VARCHAR(105) NOT NULL,
    address VARCHAR(45),
    zipcode VARCHAR(4),
    locality VARCHAR(18),
    email VARCHAR(320) NOT NULL UNIQUE,
    phoneNumber CHAR(16),
    creditCard VARCHAR(19),
    password VARCHAR(100) NOT NULL,
    id_type INT NOT NULL,
    CONSTRAINT fk_user_type FOREIGN KEY (id_type) REFERENCES Types(id)
);

-- 3. Table Restaurants
CREATE TABLE Restaurants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(45),
    zipcode CHAR(4),
    locality VARCHAR(18),
    summary VARCHAR(20),
    description TEXT,
    id_owner INT NOT NULL,
    CONSTRAINT fk_restaurant_user FOREIGN KEY (id_owner) REFERENCES Users(id)
);

-- 4. Table Dish
CREATE TABLE Dishs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(4,2) NOT NULL,
    description VARCHAR(255),
    id_restaurant INT NOT NULL,
    CONSTRAINT fk_dish_restaurant FOREIGN KEY (id_restaurant) REFERENCES Restaurants(id)
);

-- 5. Table Order 
CREATE TABLE Orders (
    id_user INT NOT NULL,
    id_restaurant INT NOT NULL,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_user, id_restaurant, order_date),
    CONSTRAINT fk_order_user FOREIGN KEY (id_user) REFERENCES Users(id),
    CONSTRAINT fk_order_restaurant FOREIGN KEY (id_restaurant) REFERENCES Restaurants(id)
);

-- 6. Table Deliver
CREATE TABLE Delivers (
    id_customer INT NOT NULL,
    id_deliverer  INT NOT NULL,
    delivery_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_customer, id_deliverer, delivery_date),
    CONSTRAINT fk_deliver_customer FOREIGN KEY (id_customer) REFERENCES Users(id),
    CONSTRAINT fk_deliver_deliverer FOREIGN KEY (id_deliverer) REFERENCES Users(id)
);