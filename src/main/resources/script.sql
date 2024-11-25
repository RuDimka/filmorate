DELETE FROM film_genres;
DELETE FROM likes;
DELETE FROM friends;
DELETE FROM films;
DELETE FROM users;

ALTER SEQUENCE films_id_seq RESTART with 1;
ALTER SEQUENCE users_id_seq RESTART with 1;
ALTER SEQUENCE genres_id_seq RESTART with 1;
ALTER SEQUENCE ratings_id_seq RESTART with 1;
