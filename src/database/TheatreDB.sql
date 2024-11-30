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
VALUES 
    ('ACMEplex Theatre North', '123 Main Street, Calgary, AB'),
    ('ACMEplex Theatre South', '123 Associated Street, Calgary, AB'),
    ('ACMEplex Theatre East', '123 Partial Street, Calgary, AB');

-- Theatre rooms
INSERT INTO THEATREROOM (TheatreID, RoomName) 
VALUES 
    (1, 'Room A'),
    (1, 'Room B'),
    (1, 'Room C'),

    (2, 'Room A'),
    (2, 'Room B'),
    (2, 'Room C'),

    (3, 'Room A'),
    (3, 'Room B'),
    (3, 'Room C');

-- Theare 1 seats
-- Theatreroom 1 (A) seats
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

-- Theatreroom 2 (B) seats
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

-- Theatreroom 3 (C) seats
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


-- Theatre 2 seats
-- Theatreroom 4 (A) seats
INSERT INTO SEAT (TheatreRoomID, SeatRow, SeatNumber)
VALUES
    (4, 1, 1), (4, 1, 2), (4, 1, 3), (4, 1, 4), (4, 1, 5),
    (4, 1, 6), (4, 1, 7), (4, 1, 8), (4, 1, 9), (4, 1, 10),

    (4, 2, 1), (4, 2, 2), (4, 2, 3), (4, 2, 4), (4, 2, 5),
    (4, 2, 6), (4, 2, 7), (4, 2, 8), (4, 2, 9), (4, 2, 10),

    (4, 3, 1), (4, 3, 2), (4, 3, 3), (4, 3, 4), (4, 3, 5),
    (4, 3, 6), (4, 3, 7), (4, 3, 8), (4, 3, 9), (4, 3, 10),

    (4, 4, 1), (4, 4, 2), (4, 4, 3), (4, 4, 4), (4, 4, 5),
    (4, 4, 6), (4, 4, 7), (4, 4, 8), (4, 4, 9), (4, 4, 10);

-- Theatreroom 5 (B) seats
INSERT INTO SEAT (TheatreRoomID, SeatRow, SeatNumber)
VALUES
    (5, 1, 1), (5, 1, 2), (5, 1, 3), (5, 1, 4), (5, 1, 5),
    (5, 1, 6), (5, 1, 7), (5, 1, 8), (5, 1, 9), (5, 1, 10),

    (5, 2, 1), (5, 2, 2), (5, 2, 3), (5, 2, 4), (5, 2, 5),
    (5, 2, 6), (5, 2, 7), (5, 2, 8), (5, 2, 9), (5, 2, 10),

    (5, 3, 1), (5, 3, 2), (5, 3, 3), (5, 3, 4), (5, 3, 5),
    (5, 3, 6), (5, 3, 7), (5, 3, 8), (5, 3, 9), (5, 3, 10),

    (5, 4, 1), (5, 4, 2), (5, 4, 3), (5, 4, 4), (5, 4, 5),
    (5, 4, 6), (5, 4, 7), (5, 4, 8), (5, 4, 9), (5, 4, 10);

-- Theatreroom 6 (C) seats
INSERT INTO SEAT (TheatreRoomID, SeatRow, SeatNumber)
VALUES
    (6, 1, 1), (6, 1, 2), (6, 1, 3), (6, 1, 4), (6, 1, 5),
    (6, 1, 6), (6, 1, 7), (6, 1, 8), (6, 1, 9), (6, 1, 10),

    (6, 2, 1), (6, 2, 2), (6, 2, 3), (6, 2, 4), (6, 2, 5),
    (6, 2, 6), (6, 2, 7), (6, 2, 8), (6, 2, 9), (6, 2, 10),

    (6, 3, 1), (6, 3, 2), (6, 3, 3), (6, 3, 4), (6, 3, 5),
    (6, 3, 6), (6, 3, 7), (6, 3, 8), (6, 3, 9), (6, 3, 10),

    (6, 4, 1), (6, 4, 2), (6, 4, 3), (6, 4, 4), (6, 4, 5),
    (6, 4, 6), (6, 4, 7), (6, 4, 8), (6, 4, 9), (6, 4, 10);

-- Theatre 2 seats
-- Theatreroom 4 (A) seats
INSERT INTO SEAT (TheatreRoomID, SeatRow, SeatNumber)
VALUES
    (7, 1, 1), (7, 1, 2), (7, 1, 3), (7, 1, 4), (7, 1, 5),
    (7, 1, 6), (7, 1, 7), (7, 1, 8), (7, 1, 9), (7, 1, 10),

    (7, 2, 1), (7, 2, 2), (7, 2, 3), (7, 2, 4), (7, 2, 5),
    (7, 2, 6), (7, 2, 7), (7, 2, 8), (7, 2, 9), (7, 2, 10),

    (7, 3, 1), (7, 3, 2), (7, 3, 3), (7, 3, 4), (7, 3, 5),
    (7, 3, 6), (7, 3, 7), (7, 3, 8), (7, 3, 9), (7, 3, 10),

    (7, 4, 1), (7, 4, 2), (7, 4, 3), (7, 4, 4), (7, 4, 5),
    (7, 4, 6), (7, 4, 7), (7, 4, 8), (7, 4, 9), (7, 4, 10);

-- Theatreroom 5 (B) seats
INSERT INTO SEAT (TheatreRoomID, SeatRow, SeatNumber)
VALUES
    (8, 1, 1), (8, 1, 2), (8, 1, 3), (8, 1, 4), (8, 1, 5),
    (8, 1, 6), (8, 1, 7), (8, 1, 8), (8, 1, 9), (8, 1, 10),

    (8, 2, 1), (8, 2, 2), (8, 2, 3), (8, 2, 4), (8, 2, 5),
    (8, 2, 6), (8, 2, 7), (8, 2, 8), (8, 2, 9), (8, 2, 10),

    (8, 3, 1), (8, 3, 2), (8, 3, 3), (8, 3, 4), (8, 3, 5),
    (8, 3, 6), (8, 3, 7), (8, 3, 8), (8, 3, 9), (8, 3, 10),

    (8, 4, 1), (8, 4, 2), (8, 4, 3), (8, 4, 4), (8, 4, 5),
    (8, 4, 6), (8, 4, 7), (8, 4, 8), (8, 4, 9), (8, 4, 10);

-- Theatreroom 6 (C) seats
INSERT INTO SEAT (TheatreRoomID, SeatRow, SeatNumber)
VALUES
    (9, 1, 1), (9, 1, 2), (9, 1, 3), (9, 1, 4), (9, 1, 5),
    (9, 1, 6), (9, 1, 7), (9, 1, 8), (9, 1, 9), (9, 1, 10),

    (9, 2, 1), (9, 2, 2), (9, 2, 3), (9, 2, 4), (9, 2, 5),
    (9, 2, 6), (9, 2, 7), (9, 2, 8), (9, 2, 9), (9, 2, 10),

    (9, 3, 1), (9, 3, 2), (9, 3, 3), (9, 3, 4), (9, 3, 5),
    (9, 3, 6), (9, 3, 7), (9, 3, 8), (9, 3, 9), (9, 3, 10),

    (9, 4, 1), (9, 4, 2), (9, 4, 3), (9, 4, 4), (9, 4, 5),
    (9, 4, 6), (9, 4, 7), (9, 4, 8), (9, 4, 9), (9, 4, 10);

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
    (1, '2024-11-24 15:00:00', 9),
    (1, '2024-11-25 15:00:00', 9),
    (1, '2024-11-26 15:00:00', 1),
    (1, '2024-11-28 15:00:00', 9),
    (2, '2024-11-28 17:00:00', 10),
    (3, '2024-12-01 15:00:00', 1),
    (4, '2024-12-01 15:30:00', 2),
    (5, '2024-12-01 17:00:00', 3),
    (6, '2024-12-01 17:30:00', 4),
    (7, '2024-12-01 18:00:00', 5),
    (8, '2024-12-01 18:30:00', 6),
    (9, '2024-12-01 20:00:00', 7),
    (1, '2024-12-01 20:30:00', 8),
    (2, '2024-12-01 21:00:00', 9),
    (3, '2024-12-01 21:30:00', 10),
    (4, '2024-12-02 15:00:00', 1),
    (5, '2024-12-02 17:00:00', 2),
    (6, '2024-12-02 18:00:00', 3),
    (7, '2024-12-02 20:00:00', 4),
    (8, '2024-12-02 12:00:00', 5),
    (9, '2024-12-02 14:30:00', 6),
    (1, '2024-12-02 15:00:00', 7),
    (2, '2024-12-02 17:00:00', 8),
    (3, '2024-12-02 18:00:00', 9),
    (4, '2024-12-02 20:00:00', 10),
    (5, '2024-12-03 12:00:00', 1),
    (6, '2024-12-03 14:30:00', 2),
    (7, '2024-12-03 15:00:00', 3),
    (8, '2024-12-03 17:00:00', 4),
    (9, '2024-12-03 18:00:00', 5),
    (1, '2024-12-03 20:00:00', 6),
    (2, '2024-12-03 12:00:00', 7),
    (3, '2024-12-03 14:30:00', 8),
    (4, '2024-12-03 15:00:00', 9),
    (5, '2024-12-03 17:00:00', 10),
    (6, '2024-12-04 12:00:00', 1),
    (7, '2024-12-04 14:30:00', 2),
    (8, '2024-12-04 15:00:00', 3),
    (9, '2024-12-04 17:00:00', 4),
    (1, '2024-12-04 18:00:00', 5),
    (2, '2024-12-04 20:00:00', 6),
    (3, '2024-12-04 12:00:00', 7),
    (4, '2024-12-04 14:30:00', 8),
    (5, '2024-12-04 15:00:00', 9),
    (6, '2024-12-04 17:00:00', 10),
    (7, '2024-12-05 12:00:00', 1),
    (8, '2024-12-05 14:30:00', 2),
    (9, '2024-12-05 15:00:00', 3),
    (1, '2024-12-05 17:00:00', 4),
    (2, '2024-12-05 18:00:00', 5),
    (3, '2024-12-05 20:00:00', 6),
    (4, '2024-12-05 12:00:00', 7),
    (5, '2024-12-05 14:30:00', 8),
    (6, '2024-12-05 15:00:00', 9),
    (7, '2024-12-05 17:00:00', 10),
    (8, '2024-12-06 12:00:00', 1),
    (9, '2024-12-06 14:30:00', 2),
    (1, '2024-12-06 15:00:00', 3),
    (2, '2024-12-06 17:00:00', 4),
    (3, '2024-12-06 18:00:00', 5),
    (4, '2024-12-06 20:00:00', 6),
    (5, '2024-12-06 12:00:00', 7),
    (6, '2024-12-06 14:30:00', 8),
    (7, '2024-12-06 15:00:00', 9),
    (8, '2024-12-06 17:00:00', 10),
    (9, '2024-12-07 12:00:00', 1),
    (1, '2024-12-07 14:30:00', 2),
    (2, '2024-12-07 15:00:00', 3),
    (3, '2024-12-07 17:00:00', 4),
    (4, '2024-12-07 18:00:00', 5),
    (5, '2024-12-07 20:00:00', 6),
    (6, '2024-12-07 12:00:00', 7),
    (7, '2024-12-07 14:30:00', 8),
    (8, '2024-12-07 15:00:00', 9),
    (9, '2024-12-07 17:00:00', 10),
    (1, '2024-12-08 12:00:00', 1),
    (2, '2024-12-08 14:30:00', 2),
    (3, '2024-12-08 15:00:00', 3),
    (4, '2024-12-08 17:00:00', 4),
    (5, '2024-12-08 18:00:00', 5),
    (6, '2024-12-08 20:00:00', 6),
    (7, '2024-12-08 12:00:00', 7),
    (8, '2024-12-08 14:30:00', 8),
    (9, '2024-12-08 15:00:00', 9),
    (1, '2024-12-08 17:00:00', 10),
    (2, '2024-12-09 12:00:00', 1),
    (3, '2024-12-09 14:30:00', 2),
    (4, '2024-12-09 15:00:00', 3),
    (5, '2024-12-09 17:00:00', 4),
    (6, '2024-12-09 18:00:00', 5),
    (7, '2024-12-09 20:00:00', 6),
    (8, '2024-12-09 12:00:00', 7),
    (9, '2024-12-09 14:30:00', 8),
    (1, '2024-12-09 15:00:00', 9),
    (2, '2024-12-09 17:00:00', 10);

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
INSERT INTO REGULAR_USER (Email, Pwd, StoreCredit)
VALUES('user1@example.com', 'password', 0.00);

INSERT INTO REGULAR_USER (Email, Pwd, StoreCredit)
VALUES('user2@example.com', 'password', 0.00);

INSERT INTO REGULAR_USER (Email, Pwd, StoreCredit)
VALUES('user3@example.com', 'password', 0.00);

INSERT INTO REGULAR_USER (Email, Pwd, StoreCredit)
VALUES('user4@example.com', 'password', 0.00);

INSERT INTO REGULAR_USER (Email, Pwd, StoreCredit)
VALUES('user5@example.com', 'password', 0.00);

INSERT INTO USER_PAYMENT_INFO (NumberCC, ExpirationDate, CVV, Email)
VALUES 
    (1234567812345678, '2026-12-31', 123, 'user6@example.com');
    -- (9876543298765432, '2025-08-15', 456, 'user2@example.com'),
    -- (1122334455667788, '2027-05-20', 789, 'user3@example.com');

INSERT INTO REGISTERED_USER (Email, FirstName, LastName, StreetAddress, City, Province, PostalCode, PaymentID)
VALUES ('user5@example.com', 'John', 'Doe', 'Random Address', 'Calgary', 'Province', 'T2X2A9', 1);


-- @block
INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email)
VALUES (9, 201, '2024-11-10 17:30:00', 'user1@example.com');

INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email)
VALUES (9, 202, '2024-11-10 17:31:00', 'user2@example.com');

INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email)
VALUES (9, 203, '2024-11-10 17:32:00', 'user3@example.com');

INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email)
VALUES (9, 204, '2024-11-10 17:32:00', 'user4@example.com');

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
SELECT * FROM REGULAR_USER;

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