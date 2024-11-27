-- ----------- --
-- DB CREATION --
-- ----------- --
DROP DATABASE IF EXISTS THEATRE_DB;
CREATE DATABASE THEATRE_DB;
USE THEATRE_DB;


-- User Payment Info Table
DROP TABLE IF EXISTS USER_PAYMENT_INFO;
CREATE TABLE USER_PAYMENT_INFO (
    PaymentID       INT AUTO_INCREMENT,
    NumberCC        BIGINT NOT NULL,
    ExpirationDate  DATE NOT NULL,
    CVV             INT NOT NULL,

    Email           VARCHAR(255) NOT NULL, -- This will double store RegisteredUsers payment info but we need to keep track of default user payments 

    PRIMARY KEY (PaymentID)
);

-- RegisteredUser table
DROP TABLE IF EXISTS REGISTERED_USER;
CREATE TABLE REGISTERED_USER (
    Email           VARCHAR(255) NOT NULL,
    Pwd             VARCHAR(255) NOT NULL,

    FirstName       VARCHAR(50) NOT NULL,
    LastName        VARCHAR(50) NOT NULL,

    StreetAddress   VARCHAR(255) NOT NULL,
    City            VARCHAR(100) NOT NULL,
    Province        VARCHAR(100) NOT NULL,
    PostalCode      VARCHAR(20) NOT NULL,

    PaymentID       INT NOT NULL,

    PRIMARY KEY (Email),
    FOREIGN KEY (PaymentID) REFERENCES USER_PAYMENT_INFO(PaymentID) ON UPDATE CASCADE

);


-- Theatre table
DROP TABLE IF EXISTS THEATRE;
CREATE TABLE THEATRE (
    TheatreID       INT NOT NULL,
    TheatreName     VARCHAR(100) NOT NULL,
    StreetAddress   VARCHAR(255) NOT NULL, -- This line is creating errors because of column name

    PRIMARY KEY (TheatreID)
);

-- TheatreRoom table
DROP TABLE IF EXISTS THEATREROOM;
CREATE TABLE THEATREROOM (
    TheatreRoomID   INT NOT NULL,
    TheatreID       INT NOT NULL,
    RoomName        VARCHAR(255) NOT NULL,

    PRIMARY KEY (TheatreRoomID),
    FOREIGN KEY (TheatreID)REFERENCES THEATRE(TheatreID) ON UPDATE CASCADE
);

-- Seat table (SeatMap)
DROP TABLE IF EXISTS SEAT;
CREATE TABLE SEAT (
    SeatID          INT NOT NULL,
    TheatreRoomID   INT NOT NULL,
    SeatRow         INT NOT NULL,
    SeatNumber      INT NOT NULL,  
    UNIQUE (TheatreRoomID, SeatRow, SeatNumber), -- These three values combined must be unique

    PRIMARY KEY (SeatID),
    FOREIGN KEY (TheatreRoomID) REFERENCES THEATREROOM(TheatreRoomID) ON UPDATE CASCADE
);

-- Movie table
DROP TABLE IF EXISTS MOVIE;
CREATE TABLE MOVIE (
    MovieID     INT AUTO_INCREMENT,
    Title       VARCHAR(255) NOT NULL,
    Genre       VARCHAR(255) NOT NULL,
    Rating      VARCHAR(255) NOT NULL,
    Runtime     TIME NOT NULL,

    PRIMARY KEY (MovieID)
);

-- Showtime table
DROP TABLE IF EXISTS SHOWTIME;
CREATE TABLE SHOWTIME (
    ShowtimeID      INT NOT NULL,
    TheatreRoomID   INT NOT NULL,
    ShowDateTime    DATETIME NOT NULL,
    MovieID         INT NOT NULL,

    PRIMARY KEY (ShowtimeID),
    FOREIGN KEY (TheatreRoomID) REFERENCES THEATREROOM(TheatreRoomID) ON UPDATE CASCADE,
    FOREIGN KEY (MovieID) REFERENCES MOVIE(MovieID) ON UPDATE CASCADE
);

-- Announcement table
DROP TABLE IF EXISTS ANNOUNCEMENT;
CREATE TABLE ANNOUNCEMENT (
    AnnouncementID      INT AUTO_INCREMENT,
    IsPublic            BOOLEAN NOT NULL,
    AnnouncementMessage VARCHAR(255) NOT NULL,
    DateAnnounced       DATETIME NOT NULL,
    ShowtimeID          INT,

    PRIMARY KEY (AnnouncementID),
    FOREIGN KEY (ShowtimeID) REFERENCES SHOWTIME(ShowtimeID) ON UPDATE CASCADE

);

-- Ticket table
DROP TABLE IF EXISTS TICKET;
CREATE TABLE TICKET (
    TicketID            INT AUTO_INCREMENT,
    ShowtimeID          INT NOT NULL,
    SeatID              INT NOT NULL,
    PurchaseDateTime    DATETIME NOT NULL,

-- Not making a foreign key because acountless users can have an email without being on DB
-- I'm thinking we do email checks in the java code 
-- Damon Nov 21
    Email               VARCHAR(255) NOT NULL, 

    UNIQUE (ShowtimeID, SeatID), -- Ensure a seat can't be double-booked

    PRIMARY KEY (TicketID),
    FOREIGN KEY (ShowtimeID) REFERENCES Showtime(ShowtimeID),
    FOREIGN KEY (SeatID) REFERENCES Seat(SeatID)
    
);


-- ------------- --
-- USER CREATION --
-- ------------- --

-- This stopped working for some reason so you have to do it in the MySQL CLI instead

-- DROP USER IF EXISTS 'theatre_connect'@'localhost';
-- CREATE USER 'theatre_connect'@'localhost' IDENTIFIED WITH 'caching_sha2_password' BY 'theatre';

-- GRANT ALL ON THEATRE_DB.* TO 'theatre_connect'@'localhost';


-- -------------- --
-- INSERT QUERIES --
-- -------------- --
-- @block
-- 1 Theatre
INSERT INTO THEATRE (TheatreID, TheatreName, StreetAddress)
VALUES (1, 'ACMEplex Theatre', '123 Main Street, Calgary, AB');

-- 3 theatre rooms
INSERT INTO THEATREROOM (TheatreRoomID, TheatreID, RoomName) 
VALUES 
    (1, 1, 'Room A'),
    (2, 1, 'Room B'),
    (3, 1, 'Room C');

-- Theare 1 (A) seats
INSERT INTO SEAT (SeatID, TheatreRoomID, SeatRow, SeatNumber)
VALUES
    (1, 1, 1, 1), (2, 1, 1, 2), (3, 1, 1, 3), (4, 1, 1, 4), (5, 1, 1, 5),
    (6, 1, 1, 6), (7, 1, 1, 7), (8, 1, 1, 8), (9, 1, 1, 9), (10, 1, 1, 10),

    (11, 1, 2, 1), (12, 1, 2, 2), (13, 1, 2, 3), (14, 1, 2, 4), (15, 1, 2, 5),
    (16, 1, 2, 6), (17, 1, 2, 7), (18, 1, 2, 8), (19, 1, 2, 9), (20, 1, 2, 10),

    (21, 1, 3, 1), (22, 1, 3, 2), (23, 1, 3, 3), (24, 1, 3, 4), (25, 1, 3, 5),
    (26, 1, 3, 6), (27, 1, 3, 7), (28, 1, 3, 8), (29, 1, 3, 9), (30, 1, 3, 10),

    (31, 1, 4, 1), (32, 1, 4, 2), (33, 1, 4, 3), (34, 1, 4, 4), (35, 1, 4, 5),
    (36, 1, 4, 6), (37, 1, 4, 7), (38, 1, 4, 8), (39, 1, 4, 9), (40, 1, 4, 10);

-- Theatre 2 (B) seats
INSERT INTO SEAT (SeatID, TheatreRoomID, SeatRow, SeatNumber)
VALUES
    (41, 2, 1, 1), (42, 2, 1, 2), (43, 2, 1, 3), (44, 2, 1, 4), (45, 2, 1, 5),
    (46, 2, 1, 6), (47, 2, 1, 7), (48, 2, 1, 8), (49, 2, 1, 9), (50, 2, 1, 10),

    (51, 2, 2, 1), (52, 2, 2, 2), (53, 2, 2, 3), (54, 2, 2, 4), (55, 2, 2, 5),
    (56, 2, 2, 6), (57, 2, 2, 7), (58, 2, 2, 8), (59, 2, 2, 9), (60, 2, 2, 10),

    (61, 2, 3, 1), (62, 2, 3, 2), (63, 2, 3, 3), (64, 2, 3, 4), (65, 2, 3, 5),
    (66, 2, 3, 6), (67, 2, 3, 7), (68, 2, 3, 8), (69, 2, 3, 9), (70, 2, 3, 10),

    (71, 2, 4, 1), (72, 2, 4, 2), (73, 2, 4, 3), (74, 2, 4, 4), (75, 2, 4, 5),
    (76, 2, 4, 6), (77, 2, 4, 7), (78, 2, 4, 8), (79, 2, 4, 9), (80, 2, 4, 10);

-- Theatre 3 (C) seats
INSERT INTO SEAT (SeatID, TheatreRoomID, SeatRow, SeatNumber)
VALUES
    (81, 3, 1, 1), (82, 3, 1, 2), (83, 3, 1, 3), (84, 3, 1, 4), (85, 3, 1, 5),
    (86, 3, 1, 6), (87, 3, 1, 7), (88, 3, 1, 8), (89, 3, 1, 9), (90, 3, 1, 10),

    (91, 3, 2, 1), (92, 3, 2, 2), (93, 3, 2, 3), (94, 3, 2, 4), (95, 3, 2, 5),
    (96, 3, 2, 6), (97, 3, 2, 7), (98, 3, 2, 8), (99, 3, 2, 9), (100, 3, 2, 10),

    (101, 3, 3, 1), (102, 3, 3, 2), (103, 3, 3, 3), (104, 3, 3, 4), (105, 3, 3, 5),
    (106, 3, 3, 6), (107, 3, 3, 7), (108, 3, 3, 8), (109, 3, 3, 9), (110, 3, 3, 10),

    (111, 3, 4, 1), (112, 3, 4, 2), (113, 3, 4, 3), (114, 3, 4, 4), (115, 3, 4, 5),
    (116, 3, 4, 6), (117, 3, 4, 7), (118, 3, 4, 8), (119, 3, 4, 9), (120, 3, 4, 10);

-- Movies
INSERT INTO MOVIE (Title, Genre, Rating, Runtime)
VALUES
    ('Glimbo''s Revenge', 'Sci-Fi', 'PG-13', '02:28:00'),
    ('The Adventures of The Bag of Grain', 'Action', 'PG-13', '02:22:50'),
    ('Smoochin'' on the Moon', 'Romance', 'PG-13', '04:15:00'),
    ('Endvengers: EndEndEndEndgame', 'Action', 'PG-13', '03:01:00'),
    ('Toy Anecdote', 'Animation', 'G', '01:00:00'),
    ('Finding Norman', 'Animation', 'G', '01:13:00'),
    ('The Godmother', 'Crime', 'R', '02:45:00'),
    ('The Matrices', 'Sci-Fi', 'R', '02:46:00'),
    ('Scary Shark Movie', 'Thriller', 'PG', '02:00:00'),
    ('Devonian Park', 'Adventure', 'PG-13', '02:01:00');

-- Showtimes
INSERT INTO SHOWTIME (ShowtimeID, TheatreRoomID, ShowDateTime, MovieID)
VALUES
    (1, 1, '2024-11-22 12:00:00', 1),
    (2, 1, '2024-11-22 14:30:00', 2),
    (3, 2, '2024-11-22 15:00:00', 3),
    (4, 2, '2024-11-22 17:00:00', 4),
    (5, 3, '2024-11-22 18:00:00', 5),
    (6, 3, '2024-11-22 20:00:00', 6),
    (7, 1, '2024-11-23 12:00:00', 7),
    (8, 1, '2024-11-23 14:30:00', 8),
    (9, 2, '2024-11-23 15:00:00', 9),
    (10, 2, '2024-11-23 17:00:00', 10),
    (11, 3, '2024-11-23 18:00:00', 1),
    (12, 3, '2024-11-23 20:00:00', 2),
    (13, 1, '2024-11-24 12:00:00', 3),
    (14, 1, '2024-11-24 14:30:00', 4),
    (15, 2, '2024-11-24 15:00:00', 5),
    (16, 2, '2024-11-24 17:00:00', 6),
    (17, 3, '2024-11-24 18:00:00', 7),
    (18, 3, '2024-11-24 20:00:00', 8),
    (19, 1, '2024-11-25 12:00:00', 9),
    (20, 1, '2024-11-25 14:30:00', 10),
    (21, 2, '2024-11-25 15:00:00', 1),
    (22, 2, '2024-11-25 17:00:00', 2),
    (23, 3, '2024-11-25 18:00:00', 3),
    (24, 3, '2024-11-25 20:00:00', 4),
    (25, 1, '2024-11-26 12:00:00', 5),
    (26, 1, '2024-11-26 14:30:00', 6),
    (27, 2, '2024-11-26 15:00:00', 7),
    (28, 2, '2024-11-26 17:00:00', 8),
    (29, 3, '2024-11-26 18:00:00', 9),
    (30, 3, '2024-11-26 20:00:00', 10),
    (31, 1, '2024-11-27 12:00:00', 1),
    (32, 1, '2024-11-27 14:30:00', 2),
    (33, 2, '2024-11-27 15:00:00', 3),
    (34, 2, '2024-11-27 17:00:00', 4),
    (35, 3, '2024-11-27 18:00:00', 5),
    (36, 3, '2024-11-27 20:00:00', 6),
    (37, 1, '2024-11-28 12:00:00', 7),
    (38, 1, '2024-11-28 14:30:00', 8),
    (39, 2, '2024-11-28 15:00:00', 9),
    (40, 2, '2024-11-28 17:00:00', 10);


-- ------------ --
-- TEST QUERIES --
-- ------------ --

-- @block
-- INSERT --
INSERT INTO REGISTERED_USER (Email, Pwd, FirstName, LastName, StreetAddress, City, Province, PostalCode)
VALUES ('test@test.com', 'jdoe', 'John', 'Doe', 'Random Address', 'Calgary', 'Province', 'T2X2A9');


-- @block
-- SELECT --

SELECT * FROM USER_PAYMENT_INFO;
SELECT * FROM REGISTERED_USER;
SELECT * FROM ANNOUNCEMENT;
SELECT * FROM THEATRE;
SELECT * FROM THEATREROOM;
SELECT * FROM SEAT;
SELECT * FROM MOVIE;
SELECT * FROM SHOWTIME;
SELECT * FROM TICKET;

-- @block
SELECT * FROM TICKET;

-- @block
SELECT * FROM ANNOUNCEMENT;
-- @block
-- DELETE --

SET FOREIGN_KEY_CHECKS = 0; -- Disable foreign key checks

DELETE FROM USER_PAYMENT_INFO;
DELETE FROM REGISTERED_USER;
DELETE FROM ANNOUNCEMENT;
DELETE FROM SEAT;
DELETE FROM MOVIE;
DELETE FROM SHOWTIME;
DELETE FROM THEATREROOM;
DELETE FROM THEATRE;
DELETE FROM TICKET;

SET FOREIGN_KEY_CHECKS = 1; -- Re-enable foreign key checks

-- @block
DELETE FROM TICKET;
-- @block
DELETE FROM ANNOUNCEMENT;