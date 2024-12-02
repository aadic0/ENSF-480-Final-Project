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
    Email           VARCHAR(255) NOT NULL,

    PRIMARY KEY (PaymentID)
);

DROP TABLE IF EXISTS REGULAR_USER;
CREATE TABLE REGULAR_USER (
    Email           VARCHAR(255) NOT NULL,
    Pwd             VARCHAR(255) NOT NULL,
    StoreCredit     FLOAT,

    PRIMARY KEY (Email)
);

ALTER TABLE REGULAR_USER
ALTER StoreCredit SET DEFAULT 0.0;

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
    StreetAddress   VARCHAR(255) NOT NULL, 

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
    AnnouncementMessage VARCHAR(1000) NOT NULL,
    DateAnnounced       DATETIME NOT NULL,
    MovieID             INT,
    Email               VARCHAR(255),

    PRIMARY KEY (AnnouncementID),
    FOREIGN KEY (MovieID) REFERENCES MOVIE(MovieID) ON UPDATE CASCADE,
    FOREIGN KEY (Email) REFERENCES REGULAR_USER(Email) ON UPDATE CASCADE

);

-- Ticket table
DROP TABLE IF EXISTS TICKET;
CREATE TABLE TICKET (
    TicketID            INT AUTO_INCREMENT,
    ShowtimeID          INT NOT NULL,
    SeatID              INT NOT NULL,
    PurchaseDateTime    DATETIME NOT NULL,
    TicketPrice         FLOAT NOT NULL,
    Email               VARCHAR(255) NOT NULL, 

    UNIQUE (ShowtimeID, SeatID), -- Ensure a seat can't be double-booked

    PRIMARY KEY (TicketID),
    FOREIGN KEY (ShowtimeID) REFERENCES Showtime(ShowtimeID) ON UPDATE CASCADE,
    FOREIGN KEY (SeatID) REFERENCES Seat(SeatID) ON UPDATE CASCADE,
    FOREIGN KEY (Email) REFERENCES REGULAR_USER(Email) ON UPDATE CASCADE
    
);


