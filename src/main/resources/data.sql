/*
Комедия.
Драма.
Мультфильм.
Триллер.
Документальный.
Боевик.
 */

INSERT INTO genres (id, name)
VALUES (1, 'Комедия'),
       (2, 'Драма'),
       (3, 'Мультфильм'),
       (4, 'Триллер'),
       (5, 'Документальный'),
       (6, 'Боевик');

/*
G — у фильма нет возрастных ограничений,
PG — детям рекомендуется смотреть фильм с родителями,
PG-13 — детям до 13 лет просмотр не желателен,
R — лицам до 17 лет просматривать фильм можно только в присутствии взрослого,
NC-17 — лицам до 18 лет просмотр запрещён.
 */

INSERT INTO ratings (id, name)
VALUES (1, 'G'),
       (2, 'PG'),
       (3, 'PG-13'),
       (4, 'R'),
       (5, 'NC-17');