-----------------
-- DB CREATION --
-----------------
DROP DATABASE IF EXISTS THEATRE_DB;
CREATE DATABASE THEATRE_DB;
USE THEATRE_DB;

DROP TABLE IF EXISTS USER;
CREATE TABLE USER(
    Email   VARCHAR(255) NOT NULL,
    Pwd     VARCHAR(25) NOT NULL,
    PRIMARY KEY (Email)
);

DROP TABLE IF EXISTS REGISTERED_USER;
CREATE TABLE REGISTERED_USER (
    Email           VARCHAR(255) NOT NULL,

    FirstName      VARCHAR(50) NOT NULL,
    LastName       VARCHAR(50) NOT NULL,
    
    StreetAddress  VARCHAR(255) NOT NULL,
    City            VARCHAR(100) NOT NULL,
    Province        VARCHAR(100) NOT NULL,
    PostalCode     VARCHAR(20) NOT NULL,
    
    -- Payment_Info   JSON,  -- Could turn into json but im commenting it out for now
    
    PRIMARY KEY (Email),
    FOREIGN KEY (Email) REFERENCES USER(Email) ON DELETE CASCADE
);

DROP TABLE IF EXISTS MOVIE;
CREATE TABLE MOVIE (
    Title   VARCHAR(255) NOT NULL,
    Genre   VARCHAR(255) NOT NULL,
    Rating  VARCHAR(255) NOT NULL,
    PRIMARY KEY (Title)
);

DROP TABLE IF EXISTS SHOWTIME;
CREATE TABLE SHOWTIME (
    ShowtimeID   INT AUTO_INCREMENT,         -- Auto-incrementing primary key for each showtime
    MovieTitle   VARCHAR(255) NOT NULL,      -- Movie associated with this showtime (foreign key to MOVIE)
    ShowingTime  DATETIME NOT NULL,          -- DateTime of the showing
    PRIMARY KEY (ShowtimeID),
    FOREIGN KEY (MovieTitle) REFERENCES MOVIE(Title) ON DELETE CASCADE
);






------------------
-- TEST QUERIES --
------------------
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




