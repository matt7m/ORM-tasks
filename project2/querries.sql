/* insert roles */
INSERT INTO role(`type`)
VALUES ('organizator'), ('student'), ('naukowiec')


INSERT INTO user(`first_name`, `last_name`, `email`, `country`, `role_id`)
VALUES ('John', 'Conore', 'conor@mail.com', 'Anglia', 3),
('Jan', 'Kowalski', 'kowalski@mail.pl', 'Polska', 1),
('Piotr', 'Nowak', 'nowak@mail.pl', 'Polska', 1),
('Adam', 'Kruk', 'temp@mail.com', 'Polska', 3),
('Christian', 'Frederiksen', 'frederiksen.info@mail.com', 'Dania', 3),
('Tommy', 'Sabb', 'tommy@mail.com', 'USA', 2),
('Filip', 'Mróz', 'mail@mail.com', 'Polska', 2),
('Terry', 'Joe', 'joeTerry@mail.com', 'USA', 2),
('Julian', 'Kot', 'kotk@mail.pl', 'Polska', 2),
('Robert', 'Sokol', 'robertsokol@mail.pl', 'Polska', 2)


INSERT INTO room(`number_of_seats`)
VALUES (50), (50), (25), (25), (5)


INSERT INTO presentation(`title`, `description`, `author_id`)
VALUES ('Black hole', 'lorem ipsum ...', 1),
('Biotechnology', 'Advanced techniques in biotechnologies', 4),
('Astronomy, Astrophysics and Cosmology', 'Particle Physics and Cosmology', 5),
('Nuclear Physics', '...', 5),
('Explore nuclear physcics', 'Practical experiments', 5)


INSERT INTO conference(`is_full`, `presentation_id`, `room_id`)
VALUES (0, 1, 1), (0, 2, 1), (0, 3, 2), (0, 4, 1), (1, 5, 3)


INSERT INTO reservation(`conference_id`, `user_id`)
VALUES (1, 2), (1, 3), (1, 4), (1, 6), (1, 7), (1, 8), (1, 9), 
(1, 10), (2, 6), (2, 7), (2, 8), (2, 9), (3, 1), (3, 6), (3, 7),
(3, 8), (3, 9), (3, 10), (3, 2), (3, 8), (3, 9),
(5, 6), (5, 7), (5, 8), (5, 9), (5, 10)



/** room + number of conferences sql**/
SELECT room.id, count(conference.presentation_id) as presentations_number
from room
left join conference
on (room.id = conference.room_id)
group by room.id