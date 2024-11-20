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

DROP TABLE IF EXISTS THEATRE;
CREATE TABLE THEATRE (
    TheatreID INT AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,

    PRIMARY KEY (TheatreID)
);

DROP TABLE IF EXISTS MOVIE;
CREATE TABLE MOVIE (
    MovieID     INT AUTO_INCREMENT,
    Title       VARCHAR(255) NOT NULL,
    Genre       VARCHAR(255) NOT NULL,
    Rating      VARCHAR(255) NOT NULL,

    PRIMARY KEY (MovieID)
);

DROP TABLE IF EXISTS SHOWTIME;
CREATE TABLE SHOWTIME (
    ShowtimeID      INT AUTO_INCREMENT,
    MovieID         INT,
    ShowingTime     DATETIME NOT NULL, 

    PRIMARY KEY (ShowtimeID),
    FOREIGN KEY (MovieID) REFERENCES MOVIE(MovieID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS SEATMAP;
CREATE TABLE SEATMAP (
    SeatMapID   INT AUTO_INCREMENT,
    ShowtimeID  INT, 
    -- Seats JSON,   -- Need to add this comment otherwise Query bugs out

    PRIMARY KEY (SeatMapID),
    FOREIGN KEY (ShowtimeID) REFERENCES SHOWTIME(ShowtimeID) ON DELETE CASCADE
);

-- Table that links Theatre, Showtime, and SeatMap (HashMap from Theatre object)
DROP TABLE IF EXISTS THEATRE_SHOWTIME_SEATING;
CREATE TABLE THEATRE_SHOWTIME_SEATING (
    TheatreID   INT,
    ShowtimeID  INT,
    SeatMapID   INT,

    PRIMARY KEY (TheatreID, ShowtimeID),
    FOREIGN KEY (TheatreID) REFERENCES THEATRE(TheatreID) ON DELETE CASCADE,
    FOREIGN KEY (ShowtimeID) REFERENCES SHOWTIME(ShowtimeID) ON DELETE CASCADE,
    FOREIGN KEY (SeatMapID) REFERENCES SEATMAP(SeatMapID) ON DELETE CASCADE
);

-- ------------ --
-- TEST QUERIES --
-- ------------ --
-- @block
INSERT INTO USER (Email, Pwd)
VALUES ('test@test.com', 'pwd');

INSERT INTO REGISTERED_USER (Email, FName, LName)
VALUES ('test@test.com', 'John', 'Doe');

-- @block
SELECT * FROM USER;
SELECT * FROM REGISTERED_USER;

-- @block
-- DELETE FROM REGISTERED_USER
-- WHERE Email = 'test@test.com';

DELETE FROM USER 
WHERE Email = 'test@test.com';

-- @block
SELECT * FROM default_user;
SELECT * FROM registered_user;
SELECT * FROM theatre;
SELECT * FROM movie;
SELECT * FROM showtime;
SELECT * FROM seatmap;
SELECT * FROM theatre_showtime_seating;


