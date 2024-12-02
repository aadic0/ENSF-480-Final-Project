USE THEATRE_DB;

-- -------------- --
-- INSERT QUERIES --
-- -------------- --

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
    ('Devonian Park', 'Adventure', 'PG-13', '02:01:00'),
    ('Space Friends: The Final Foe', 'Comedy', 'PG', '01:45:00');

-- Showtimes
INSERT INTO SHOWTIME (TheatreRoomID, ShowDateTime, MovieID)
VALUES
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
    (2, '2024-12-09 17:00:00', 10),
    (1, '2024-12-03 15:00:00', 11), 
    (2, '2024-12-04 17:30:00', 11), 
    (3, '2024-12-04 20:00:00', 11), 
    (4, '2024-12-10 16:00:00', 11), 
    (5, '2024-12-09 18:30:00', 11); 

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

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (TRUE, 'Public announcement: Scary Shark Movie', '2024-11-20 18:00:00', 9);

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (TRUE, 'Public announcement: Devonian Park', '2024-11-20 18:00:00', 10);


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

INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, MovieID)
VALUES (FALSE, 'Private announcement: Space Otters: The Final Foe', '2024-11-10 18:00:00', 11);