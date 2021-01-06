DROP DATABASE IF EXISTS sark;
CREATE DATABASE sark;
USE sark;
DROP TABLE IF EXISTS feedbacks;
DROP TABLE IF EXISTS bookedflights;
DROP TABLE IF EXISTS contactdetails;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS aircraftdetails;
CREATE TABLE bookedflights (user_id INT NOT NULL,serial_number INT NOT NULL);
CREATE TABLE users (user_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,first_name VARCHAR (20) NOT NULL,last_name VARCHAR (20) NOT NULL,dob DATE NOT NULL,gender VARCHAR (10) NOT NULL,title VARCHAR (6) NOT NULL,father_firstname VARCHAR (20) NOT NULL,father_lastname VARCHAR (20) NOT NULL,nationality VARCHAR (20) NOT NULL,country VARCHAR (20) NOT NULL,state VARCHAR (20) NOT NULL,city VARCHAR (20) NOT NULL,areacode INT NOT NULL,landmark VARCHAR (100),isAdmin BOOLEAN DEFAULT 0 NOT NULL);
CREATE TABLE contactdetails (user_id INT NOT NULL,email VARCHAR (30) NOT NULL UNIQUE,password VARCHAR (20) NOT NULL,phone_number VARCHAR (10) NOT NULL);
CREATE TABLE flights (serial_number INT PRIMARY KEY AUTO_INCREMENT NOT NULL,flight_id VARCHAR (6) NOT NULL,origin VARCHAR (20) NOT NULL,destination VARCHAR (20) NOT NULL,departure TIME NOT NULL,arrival TIME NOT NULL,aircraft VARCHAR (10) NOT NULL,time VARCHAR (8) NOT NULL,economy_cost INT NOT NULL,business_cost INT NOT NULL);
CREATE TABLE aircraftdetails (Name VARCHAR (8) PRIMARY KEY NOT NULL,speed INT NOT NULL,cost INT NOT NULL);
CREATE TABLE feedbacks (feedbackid INT PRIMARY KEY AUTO_INCREMENT NOT NULL,userid INT NOT NULL,flight_id VARCHAR(6) NOT NULL,message VARCHAR (200) NOT NULL,isread BOOLEAN NOT NULL);
ALTER TABLE bookedflights ADD CONSTRAINT bookedflights_user_id_users_user_id FOREIGN KEY (user_id) REFERENCES users(user_id);
ALTER TABLE bookedflights ADD CONSTRAINT bookedflights_serial_number_flights_serial_number FOREIGN KEY (serial_number) REFERENCES flights (serial_number);
ALTER TABLE contactdetails ADD CONSTRAINT contactdetails_user_id_users_user_id FOREIGN KEY (user_id) REFERENCES users(user_id);
ALTER TABLE feedbacks ADD CONSTRAINT feedbacks_userid_users_user_id FOREIGN KEY (userid) REFERENCES users(user_id);
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Bangalore','Chennai','15:35','16:10','0 h 35 m','KS-007','#aaaaa','1166','2916');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Goa','Bangalore','13:50','14:48','0 h 58 m','KS-007','#aaaaa','1933','4833');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Hyderabad','Goa','12:00','13:02','1 h 02 m','KS-007','#aaaaa','2066','5166');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Mumbai','Hyderabad','10:00','11:15','1 h 15 m','KS-007','#aaaaa','2500','6250');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Bangalore','Goa','18:20','19:18','0 h 58 m','KS-007','#aaaab','1933','4833');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Chennai','Bangalore','17:00','17:35','0 h 35 m','KS-007','#aaaab','1166','2916');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Goa','Hyderabad','19:05','20:07','1 h 02 m','KS-007','#aaaab','2066','5166');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Hyderabad','Mumbai','21:00','21:15','1 h 15 m','KS-007','#aaaab','2500','6250');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Kolkata','Patna','10:00','10:57','0 h 57 m','RV-037','#aaaac','1900','4750');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Lucknow','New Delhi','13:25','14:15','0 h 50 m','RV-037','#aaaac','1666','4166');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('New Delhi','Jaipur','15:00','15:30','0 h 30 m','RV-037','#aaaac','1000','2500');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Patna','Lucknow','11:45',' 12:38','0 h 53 m','RV-037','#aaaac','1766','4416');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Jaipur','New Delhi','16:15','16:45','0 h 30 m','RV-037','#aaaad','1000','2500');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Lucknow','Patna','19:05','19:58','0 h 53 m','RV-037','#aaaad','1766','4416');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('New Delhi','Lucknow','17:30','18:20','0 h 50 m','RV-037','#aaaad','1666','4166');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Patna','Kolkata','20:50','21:47','0 h 57 m','RV-037','#aaaad','1900','4750');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Chennai','Mumbai','10:00','11:29','1 h 29 m','SS-705','#aaaae','4450','7416');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Mumbai','New Delhi','12:30',' 14:09','1 h 39 m','SS-705','#aaaae','4950','8250');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('New Delhi','Chennai','15:10','17:41','2 h 31 m','SS-705','#aaaae','7550','15100');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Chennai','New Delhi','15:30','17:42','2 h 12 m','AB-786','#aaaaf','7700','15400');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Kolkata','Chennai','12:40','14:22','1 h 42 m','AB-786','#aaaaf','5950','11900');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('New Delhi','Kolkata','10:00','11:38','1 h 38 m','AB-786','#aaaaf','5716','11433');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Chennai','Kolkata','18:50','20:47','1 h 57 m','SS-705','#aaaag','5850','11700');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Kolkata','Mumbai','21:50','00:12','2 h 22 m','SS-705','#aaaag','7100','14200');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Mumbai','Chennai','01:20','02:49','1 h 29 m','SS-705','#aaaag','4450','8900');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Kolkata','New Delhi','00:50','02:28','1 h 38 m','AB-786','#aaaah','5716','11433');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('Mumbai','Kolkata','21:20','23:45','2 h 25 m','AB-786','#aaaah','8458','16916');
INSERT INTO flights (origin,destination,departure,arrival,time,aircraft,flight_id,economy_cost,business_cost) VALUES ('New Delhi','Mumbai','18:50','20:16','1 h 26 m','AB-786','#aaaah','5016','10033');
