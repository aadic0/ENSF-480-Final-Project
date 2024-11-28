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

DROP TABLE IF EXISTS REGULAR_USER;
CREATE TABLE REGULAR_USER (
    Email           VARCHAR(255) NOT NULL,
    Pwd             VARCHAR(255) NOT NULL,
    StoreCredit     FLOAT,

    PRIMARY KEY (Email)
);

-- RegisteredUser table
DROP TABLE IF EXISTS REGISTERED_USER;
CREATE TABLE REGISTERED_USER (
    Email           VARCHAR(255) NOT NULL,

    FirstName       VARCHAR(50) NOT NULL,
    LastName        VARCHAR(50) NOT NULL,

    StreetAddress   VARCHAR(255) NOT NULL,
    City            VARCHAR(100) NOT NULL,
    Province        VARCHAR(100) NOT NULL,
    PostalCode      VARCHAR(20) NOT NULL,

    PaymentID       INT NOT NULL,

    PRIMARY KEY (Email),
    FOREIGN KEY (PaymentID) REFERENCES USER_PAYMENT_INFO(PaymentID) ON UPDATE CASCADE,
    FOREIGN KEY (Email) REFERENCES REGULAR_USER(Email) ON UPDATE CASCADE

);


-- Theatre table
DROP TABLE IF EXISTS THEATRE;
CREATE TABLE THEATRE (
    TheatreID       INT AUTO_INCREMENT,
    TheatreName     VARCHAR(100) NOT NULL,
    StreetAddress   VARCHAR(255) NOT NULL, -- This line is creating errors because of column name

    PRIMARY KEY (TheatreID)
);

-- TheatreRoom table
DROP TABLE IF EXISTS THEATREROOM;
CREATE TABLE THEATREROOM (
    TheatreRoomID   INT AUTO_INCREMENT,
    TheatreID       INT NOT NULL,
    RoomName        VARCHAR(255) NOT NULL,

    PRIMARY KEY (TheatreRoomID),
    FOREIGN KEY (TheatreID) REFERENCES THEATRE(TheatreID) ON UPDATE CASCADE
);

-- Seat table (SeatMap)
DROP TABLE IF EXISTS SEAT;
CREATE TABLE SEAT (
    SeatID          INT AUTO_INCREMENT,
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
    ShowtimeID      INT AUTO_INCREMENT,
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
    MovieID             INT,

    PRIMARY KEY (AnnouncementID),
    FOREIGN KEY (MovieID) REFERENCES MOVIE(MovieID) ON UPDATE CASCADE

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
    FOREIGN KEY (ShowtimeID) REFERENCES Showtime(ShowtimeID) ON UPDATE CASCADE,
    FOREIGN KEY (SeatID) REFERENCES Seat(SeatID) ON UPDATE CASCADE,
    FOREIGN KEY (Email) REFERENCES REGULAR_USER(Email) ON UPDATE CASCADE
    
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
-- Theatre
INSERT INTO THEATRE (TheatreName, StreetAddress)
VALUES ('ACMEplex Theatre', '123 Main Street, Calgary, AB');

-- Theatre rooms
INSERT INTO THEATREROOM (TheatreID, RoomName) 
VALUES 
    (1, 'Room A'),
    (1, 'Room B'),
    (1, 'Room C');

-- Theare 1 (A) seats
-- Theatre 1 (A) seats
INSERT INTO SEAT (TheatreRoomID, SeatRow, SeatNumber)
VALUES
    (1, 1, 1), (1, 1, 2), (1, 1, 3), (1, 1, 4), (1, 1, 5),
    (1, 1, 6), (1, 1, 7), (1, 1, 8), (1, 1, 9), (1, 1, 10),

    (1, 2, 1), (1, 2, 2), (1, 2, 3), (1, 2, 4), (1, 2, 5),
    (1, 2, 6), (1, 2, 7), (1, 2, 8), (1, 2, 9), (1, 2, 10),

    (1, 3, 1), (1, 3, 2), (1, 3, 3), (1, 3, 4), (1, 3, 5),
    (1, 3, 6), (1, 3, 7), (1, 3, 8), (1, 3, 9), (1, 3, 10),

    (1, 4, 1), (1, 4, 2), (1, 4, 3), (1, 4, 4), (1, 4, 5),
    (1, 4, 6), (1, 4, 7), (1, 4, 8), (1, 4, 9), (1, 4, 10);

-- Theatre 2 (B) seats
INSERT INTO SEAT (TheatreRoomID, SeatRow, SeatNumber)
VALUES
    (2, 1, 1), (2, 1, 2), (2, 1, 3), (2, 1, 4), (2, 1, 5),
    (2, 1, 6), (2, 1, 7), (2, 1, 8), (2, 1, 9), (2, 1, 10),

    (2, 2, 1), (2, 2, 2), (2, 2, 3), (2, 2, 4), (2, 2, 5),
    (2, 2, 6), (2, 2, 7), (2, 2, 8), (2, 2, 9), (2, 2, 10),

    (2, 3, 1), (2, 3, 2), (2, 3, 3), (2, 3, 4), (2, 3, 5),
    (2, 3, 6), (2, 3, 7), (2, 3, 8), (2, 3, 9), (2, 3, 10),

    (2, 4, 1), (2, 4, 2), (2, 4, 3), (2, 4, 4), (2, 4, 5),
    (2, 4, 6), (2, 4, 7), (2, 4, 8), (2, 4, 9), (2, 4, 10);

-- Theatre 3 (C) seats
INSERT INTO SEAT (TheatreRoomID, SeatRow, SeatNumber)
VALUES
    (3, 1, 1), (3, 1, 2), (3, 1, 3), (3, 1, 4), (3, 1, 5),
    (3, 1, 6), (3, 1, 7), (3, 1, 8), (3, 1, 9), (3, 1, 10),

    (3, 2, 1), (3, 2, 2), (3, 2, 3), (3, 2, 4), (3, 2, 5),
    (3, 2, 6), (3, 2, 7), (3, 2, 8), (3, 2, 9), (3, 2, 10),

    (3, 3, 1), (3, 3, 2), (3, 3, 3), (3, 3, 4), (3, 3, 5),
    (3, 3, 6), (3, 3, 7), (3, 3, 8), (3, 3, 9), (3, 3, 10),

    (3, 4, 1), (3, 4, 2), (3, 4, 3), (3, 4, 4), (3, 4, 5),
    (3, 4, 6), (3, 4, 7), (3, 4, 8), (3, 4, 9), (3, 4, 10);


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
INSERT INTO SHOWTIME (TheatreRoomID, ShowDateTime, MovieID)
VALUES
    (1, '2024-11-22 12:00:00', 1),
    (1, '2024-11-22 14:30:00', 2),
    (2, '2024-11-22 15:00:00', 3),
    (2, '2024-11-22 17:00:00', 4),
    (3, '2024-11-22 18:00:00', 5),
    (3, '2024-11-22 20:00:00', 6),
    (1, '2024-11-23 12:00:00', 7),
    (1, '2024-11-27 14:30:00', 8),
    (2, '2024-11-23 15:00:00', 9),
    (2, '2024-11-23 17:00:00', 10),
    (3, '2024-11-23 18:00:00', 1),
    (3, '2024-11-23 20:00:00', 2),
    (1, '2024-11-24 12:00:00', 3),
    (1, '2024-11-24 14:30:00', 4),
    (2, '2024-11-24 15:00:00', 5),
    (2, '2024-11-24 17:00:00', 6),
    (3, '2024-11-24 18:00:00', 7),
    (3, '2024-11-24 20:00:00', 8),
    (1, '2024-11-25 12:00:00', 9),
    (1, '2024-11-25 14:30:00', 10),
    (2, '2024-11-25 15:00:00', 1),
    (2, '2024-11-25 17:00:00', 2),
    (3, '2024-11-25 18:00:00', 3),
    (3, '2024-11-25 20:00:00', 4),
    (1, '2024-11-26 12:00:00', 5),
    (1, '2024-11-26 14:30:00', 6),
    (2, '2024-11-26 15:00:00', 7),
    (2, '2024-11-26 17:00:00', 8),
    (3, '2024-11-26 18:00:00', 9),
    (3, '2024-11-26 20:00:00', 10),
    (1, '2024-11-27 12:00:00', 1),
    (1, '2024-11-27 14:30:00', 2),
    (2, '2024-11-27 15:00:00', 3),
    (2, '2024-11-27 17:00:00', 4),
    (3, '2024-11-27 18:00:00', 5),
    (3, '2024-11-27 20:00:00', 6),
    (1, '2024-11-28 12:00:00', 7),
    (1, '2024-11-28 14:30:00', 8),
    (2, '2024-11-28 15:00:00', 9),
    (2, '2024-11-28 17:00:00', 10);

-- Annoucements
-- Public Announcements
INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (TRUE, 'Public announcement: Glimbo''s Revenge', '2024-11-20 18:00:00', 1);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (TRUE, 'Public announcement: The Adventures of The Bag of Grain', '2024-11-20 18:00:00', 2);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (TRUE, 'Public announcement: Smoochin'' on the Moon', '2024-11-20 18:00:00', 3);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (TRUE, 'Public announcement: Endvengers: EndEndEndEndgame', '2024-11-20 18:00:00', 4);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (TRUE, 'Public announcement: Toy Anecdote', '2024-11-20 18:00:00', 5);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (TRUE, 'Public announcement: Finding Norman', '2024-11-20 18:00:00', 6);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (TRUE, 'Public announcement: The Godmother', '2024-11-20 18:00:00', 7);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (TRUE, 'Public announcement: The Matrices', '2024-11-20 18:00:00', 8);

-- INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
-- VALUES (TRUE, 'Public announcement: Scary Shark Movie', '2024-11-20 18:00:00', 9);

-- INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
-- VALUES (TRUE, 'Public announcement: Devonian Park', '2024-11-20 18:00:00', 10);

-- Private Announcements
INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (FALSE, 'Private announcement: Glimbo''s Revenge', '2024-11-10 18:00:00', 1);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (FALSE, 'Private announcement: The Adventures of The Bag of Grain', '2024-11-10 18:00:00', 2);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (FALSE, 'Private announcement: Smoochin'' on the Moon', '2024-11-10 18:00:00', 3);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (FALSE, 'Private announcement: Endvengers: EndEndEndEndgame', '2024-11-10 18:00:00', 4);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (FALSE, 'Private announcement: Toy Anecdote', '2024-11-10 18:00:00', 5);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (FALSE, 'Private announcement: Finding Norman', '2024-11-10 18:00:00', 6);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (FALSE, 'Private announcement: The Godmother', '2024-11-10 18:00:00', 7);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (FALSE, 'Private announcement: The Matrices', '2024-11-10 18:00:00', 8);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (FALSE, 'Private announcement: Scary Shark Movie', '2024-11-10 18:00:00', 9);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (FALSE, 'Private announcement: Devonian Park', '2024-11-10 18:00:00', 10);


-- ------------ --
-- TEST QUERIES --
-- ------------ --

-- @block
-- INSERT --
INSERT INTO REGISTERED_USER (Email, Pwd, FirstName, LastName, StreetAddress, City, Province, PostalCode)
VALUES ('test@test.com', 'jdoe', 'John', 'Doe', 'Random Address', 'Calgary', 'Province', 'T2X2A9');

-- @block
INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email)
VALUES (9, 41, '2024-11-10 17:30:00', 'user1@example.com');

INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email)
VALUES (9, 42, '2024-11-10 17:31:00', 'user2@example.com');

INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email)
VALUES (9, 43, '2024-11-10 17:32:00', 'user3@example.com');

INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email)
VALUES (9, 44, '2024-11-10 17:32:00', 'user4@example.com');

-- @block
-- SELECT --

SELECT * FROM USER_PAYMENT_INFO;
SELECT * FROM REGULAR_USER;
SELECT * FROM REGISTERED_USER;
SELECT * FROM ANNOUNCEMENT;
SELECT * FROM THEATRE;
SELECT * FROM THEATREROOM;
SELECT * FROM SEAT;
SELECT * FROM MOVIE;
SELECT * FROM SHOWTIME;
SELECT * FROM TICKET;

-- @block
SELECT * FROM REGISTERED_USER;
-- @block
SELECT * FROM TICKET;

-- @block
SELECT * FROM ANNOUNCEMENT;
-- @block
-- DELETE --

SET FOREIGN_KEY_CHECKS = 0; -- Disable foreign key checks

DELETE FROM USER_PAYMENT_INFO;
DELETE FROM REGULAR_USER;
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