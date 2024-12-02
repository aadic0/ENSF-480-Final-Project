USE THEATRE_DB;

-- ------------ --
-- TEST QUERIES --
-- ------------ --

-- @block
INSERT INTO REGULAR_USER (Email, Pwd, StoreCredit)
VALUES('user1@example.com', 'password', 20);

INSERT INTO REGULAR_USER (Email, Pwd)
VALUES('user2@example.com', 'password');

INSERT INTO REGULAR_USER (Email, Pwd)
VALUES('user3@example.com', 'password');

INSERT INTO REGULAR_USER (Email, Pwd)
VALUES('user4@example.com', 'password');

INSERT INTO REGULAR_USER (Email, Pwd)
VALUES('user5@example.com', 'password');

-- @block
INSERT INTO REGULAR_USER (Email, Pwd)
VALUES('user6@example.com', 'password')


-- @block
INSERT INTO USER_PAYMENT_INFO (NumberCC, ExpirationDate, CVV, Email)
VALUES 
    (1234567812345678, '2026-12-31', 123, 'user5@example.com');
    -- (9876543298765432, '2025-08-15', 456, 'user2@example.com'),
    -- (1122334455667788, '2027-05-20', 789, 'user3@example.com');

INSERT INTO REGISTERED_USER (Email, FirstName, LastName, StreetAddress, City, Province, PostalCode, PaymentID)
VALUES ('user5@example.com', 'John', 'Doe', 'Random Address', 'Calgary', 'Province', 'T2X2A9', 1);


-- @block
INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email, TicketPrice)
VALUES (82, 201, '2024-11-10 17:30:00', 'user1@example.com', 20);

INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email, TicketPrice)
VALUES (9, 202, '2024-11-10 17:31:00', 'user2@example.com', 20);

INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email, TicketPrice)
VALUES (9, 203, '2024-11-10 17:32:00', 'user3@example.com', 20);

INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email, TicketPrice)
VALUES (9, 204, '2024-11-10 17:32:00', 'user4@example.com', 20);

INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email, TicketPrice)
VALUES (82, 205, '2024-11-10 17:32:00', 'user5@example.com', 20);


-- @block
INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, Email)
VALUES (FALSE, 'Private announcement: Devonian Park', '2024-11-10 18:00:00', 'user1@example.com');

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
DELETE FROM USER_PAYMENT_INFO;


