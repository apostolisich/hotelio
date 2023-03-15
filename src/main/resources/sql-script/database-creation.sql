DROP TABLE IF EXISTS api_user;

DROP TABLE IF EXISTS contact_details;

DROP TABLE IF EXISTS payment;

DROP TABLE IF EXISTS guest;

DROP TABLE IF EXISTS booking;

CREATE TABLE api_user(id INT auto_increment PRIMARY KEY, username varchar_ignorecase(50) NOT NULL, password varchar_ignorecase(500) NOT NULL, enabled boolean NOT NULL);

CREATE TABLE booking(booking_reference VARCHAR(10) PRIMARY KEY, creation_date TIMESTAMP, amount NUMERIC(6, 2), currency VARCHAR(3), hotel_name VARCHAR(255), check_in VARCHAR(10), check_out VARCHAR(10), room_description VARCHAR(1000));

CREATE TABLE guest(id INT PRIMARY KEY AUTO_INCREMENT, booking_reference VARCHAR(10), title VARCHAR(4), name VARCHAR(255), surname VARCHAR(255), age INT, FOREIGN KEY (booking_reference) REFERENCES booking(booking_reference));

CREATE TABLE contact_details(id INT PRIMARY KEY AUTO_INCREMENT, booking_reference VARCHAR(10), title VARCHAR(4), name VARCHAR(255), surname VARCHAR(255), address VARCHAR(255), phone VARCHAR(15), email VARCHAR(50), FOREIGN KEY (booking_reference) REFERENCES booking(booking_reference));

CREATE TABLE payment(id INT PRIMARY KEY AUTO_INCREMENT, booking_reference VARCHAR(10), card_number VARCHAR(19), cvv INT, expiry_date VARCHAR(5), cardholder_name VARCHAR(255), FOREIGN KEY (booking_reference) REFERENCES booking(booking_reference));

-- Creating a test user. Username: demo, Password: password
INSERT INTO api_user (username, password, enabled) values ('demo', '{bcrypt}$2a$12$hAtxluFte9XtAzHjr1FsCuNWApyl2owCit.PeArqWaV8f44KKgyCO', TRUE);