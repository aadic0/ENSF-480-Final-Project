-- ----------- --
-- DB CREATION --
-- ----------- --
DROP DATABASE IF EXISTS THEATRE_DB;
CREATE DATABASE THEATRE_DB;
USE THEATRE_DB;

DROP TABLE IF EXISTS DEFAULT_USER;
CREATE TABLE DEFAULT_USER(
    Email   VARCHAR(255) NOT NULL,
    Pwd     VARCHAR(25) NOT NULL,

    PRIMARY KEY (Email)
);

DROP TABLE IF EXISTS REGISTERED_USER;
CREATE TABLE REGISTERED_USER (
    Email          VARCHAR(255) NOT NULL,
    Pwd            VARCHAR(255) NOT NULL,

    FirstName      VARCHAR(50) NOT NULL,
    LastName       VARCHAR(50) NOT NULL,
    
    StreetAddress  VARCHAR(255) NOT NULL,
    City           VARCHAR(100) NOT NULL,
    Province       VARCHAR(100) NOT NULL,
    PostalCode     VARCHAR(20) NOT NULL,
    
    -- Payment_Info   JSON,  -- Could turn into json but im commenting it out for now
    
    PRIMARY KEY (Email),
    FOREIGN KEY (Email) REFERENCES DEFAULT_USER(Email) ON DELETE CASCADE

);

-- DROP TABLE IF EXISTS THEATRE;
-- CREATE TABLE THEATRE (
--     TheatreID INT AUTO_INCREMENT,
--     Name VARCHAR(255) NOT NULL,

--     PRIMARY KEY (TheatreID)
-- );

-- DROP TABLE IF EXISTS MOVIE;
-- CREATE TABLE MOVIE (
--     MovieID     INT AUTO_INCREMENT,
--     Title       VARCHAR(255) NOT NULL,
--     Genre       VARCHAR(255) NOT NULL,
--     Rating      VARCHAR(255) NOT NULL,

--     PRIMARY KEY (MovieID)
-- );

-- DROP TABLE IF EXISTS SHOWTIME;
-- CREATE TABLE SHOWTIME (
--     ShowtimeID      INT AUTO_INCREMENT,
--     MovieID         INT NOT NULL,
--     ShowingTime     DATETIME NOT NULL, 

--     PRIMARY KEY (ShowtimeID),
--     FOREIGN KEY (MovieID) REFERENCES MOVIE(MovieID) ON DELETE CASCADE
-- );

-- DROP TABLE IF EXISTS SEATMAP;
-- CREATE TABLE SEATMAP (
--     SeatMapID   INT AUTO_INCREMENT,
--     ShowtimeID  INT NOT NULL, 
--     -- Seats JSON,   -- Need to add this comment otherwise Query bugs out

--     PRIMARY KEY (SeatMapID),
--     FOREIGN KEY (ShowtimeID) REFERENCES SHOWTIME(ShowtimeID) ON DELETE CASCADE
-- );

-- -- Table that links Theatre, Showtime, and SeatMap (HashMap from Theatre object)
-- DROP TABLE IF EXISTS THEATRE_SHOWTIME_SEATING;
-- CREATE TABLE THEATRE_SHOWTIME_SEATING (
--     TheatreID   INT NOT NULL,
--     ShowtimeID  INT NOT NULL,
--     SeatMapID   INT NOT NULL,

--     PRIMARY KEY (TheatreID, ShowtimeID),
--     FOREIGN KEY (TheatreID) REFERENCES THEATRE(TheatreID) ON DELETE CASCADE,
--     FOREIGN KEY (ShowtimeID) REFERENCES SHOWTIME(ShowtimeID) ON DELETE CASCADE,
--     FOREIGN KEY (SeatMapID) REFERENCES SEATMAP(SeatMapID) ON DELETE CASCADE
-- );

-- Theatre table
DROP TABLE IF EXISTS THEATRE;
CREATE TABLE THEATRE (
    TheatreID       INT,
    TheatreName     VARCHAR(100) NOT NULL,
    Location        VARCHAR(255) NOT NULL,

    PRIMARY KEY (TheatreID)
);

-- TheatreRoom table
DROP TABLE IF EXISTS THEATREROOM;
CREATE TABLE THEATREROOM (
    TheatreRoomID   INT,
    TheatreID       INT NOT NULL,
    RoomName        VARCHAR(255) NOT NULL,

    PRIMARY KEY (TheatreRoomID)
    FOREIGN KEY (TheatreID) REFERENCES Theatre(TheatreID)
);

-- Seat table (SeatMap)
DROP TABLE IF EXISTS SEAT;
CREATE TABLE SEAT (
    SeatID          INT,
    TheatreRoomID   INT NOT NULL,
    SeatRow         INT NOT NULL,
    SeatNumber      INT NOT NULL,  

    UNIQUE (TheatreRoomID, SeatRow, SeatNumber) 

    PRIMARY KEY (SeatID),
    FOREIGN KEY (TheatreRoomID) REFERENCES TheatreRoom(TheatreRoomID), 
);

DROP TABLE IF EXISTS SHOWTIME;
CREATE TABLE SHOWTIME (
    ShowtimeID      INT,
    TheatreRoomID   INT NOT NULL,
    ShowDateTime    DATETIME NOT NULL,
    MovieTitle      VARCHAR(255) NOT NULL,

    PRIMARY KEY (ShowtimeID),
    FOREIGN KEY (TheatreRoomID) REFERENCES TheatreRoom(TheatreRoomID)
);

DROP TABLE IF EXISTS MOVIE;
CREATE TABLE MOVIE (
    MovieID     INT AUTO_INCREMENT,
    Title       VARCHAR(255) NOT NULL,
    Genre       VARCHAR(255) NOT NULL,
    Rating      VARCHAR(255) NOT NULL,
    Runtime     DATETIME NOT NULL,

    PRIMARY KEY (MovieID)
);

-- Ticket table
DROP TABLE IF EXISTS TICKET;
CREATE TABLE TICKET (
    TicketID            INT,
    ShowtimeID          INT NOT NULL,
    SeatID              INT NOT NULL,
    PurchaseDateTime    DATETIME NOT NULL,

    UNIQUE (ShowtimeID, SeatID) -- Ensure a seat can't be double-booked

    PRIMARY KEY (TicketID),
    FOREIGN KEY (ShowtimeID) REFERENCES Showtime(ShowtimeID),
    FOREIGN KEY (SeatID) REFERENCES Seat(SeatID),
    
);


-- ------------- --
-- USER CREATION --
-- ------------- --

DROP USER IF EXISTS 'theatre_connect'@'localhost';
CREATE USER 'theatre_connect'@'localhost' IDENTIFIED WITH 'caching_sha2_password' BY 'theatre';
GRANT ALL ON THEATRE_DB.* TO 'theatre_connect'@'localhost';



-- ------------ --
-- TEST QUERIES --
-- ------------ --


-- INSERT --

-- @block
DELETE FROM DEFAULT_USER;
INSERT INTO DEFAULT_USER (Email, Pwd)
VALUES ('test@test.com', 'pwd');

INSERT INTO REGISTERED_USER (Email, Pwd, FirstName, LastName, StreetAddress, City, Province, PostalCode)
VALUES ('test@test.com', 'jdoe', 'John', 'Doe', 'Random Address', 'Calgary', 'Province', 'T2X2A9');

-- @block
INSERT INTO THEATRE (Name) VALUES 
('Cineplex Scotiabank Theatre'), 
('Landmark Cinemas'), 
('Globe Cinema'), 
('IMAX Theatre');

INSERT INTO MOVIE (Title, Genre, Rating) VALUES 
('Inception', 'Sci-Fi', 'PG-13'),
('The Dark Knight', 'Action', 'PG-13'),
('Titanic', 'Romance', 'PG-13'),
('Interstellar', 'Sci-Fi', 'PG-13'),
('The Godfather', 'Crime', 'R');

INSERT INTO SHOWTIME (MovieID, ShowingTime) VALUES 
(1, '2024-11-20 19:00:00'), 
(2, '2024-11-20 21:30:00'), 
(3, '2024-11-21 18:00:00'), 
(4, '2024-11-21 20:00:00'), 
(5, '2024-11-22 19:00:00');

INSERT INTO SEATMAP (ShowtimeID) VALUES 
(1), 
(2), 
(3), 
(4), 
(5);

INSERT INTO THEATRE_SHOWTIME_SEATING (TheatreID, ShowtimeID, SeatMapID) VALUES 
(1, 1, 1), 
(1, 2, 2), 
(2, 3, 3), 
(3, 4, 4), 
(4, 5, 5);


-- SELECT --
-- @block
SELECT * FROM USER;
SELECT * FROM REGISTERED_USER;

-- @block
SELECT * FROM default_user;
SELECT * FROM registered_user;

-- @block
SELECT * FROM theatre;
SELECT * FROM movie;
SELECT * FROM showtime;
SELECT * FROM seatmap;
SELECT * FROM theatre_showtime_seating;



-- @block
SELECT * FROM default_user;
-- @block
SELECT * FROM registered_user;

-- DELETE --
-- @block
-- DELETE FROM REGISTERED_USER
-- WHERE Email = 'test@test.com';

DELETE * FROM USER 
WHERE Email = 'test@test.com';

-- @block
DELETE FROM DEFAULT_USER;
DELETE FROM REGISTERED_USER;