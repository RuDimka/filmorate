package com.example.filmorate.constant;

public class SqlRequestConstant {
    public static final String SQL_QUERY_CREATE_FILM =
            "INSERT INTO films (name, description, release_date, duration, rating_id) VALUES(?, ?, ?, ?, ?)";
    public static String SQL_QUERY_DELETE_GENRE = "DELETE FROM film_genres WHERE film_id = ?";
    public static String SQL_QUERY_INSERT_GENRE = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
    public static String SQL_QUERY_UPDATE_FILM = "UPDATE films SET name = ?, description = ?, release_date = ?, " +
            "duration = ? WHERE id = ?";
    public static String SQL_QUERY_GET_ALL_FILMS = "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.rating_id, r.rating_name, likes_count " +
            "FROM films f LEFT JOIN ratings r ON f.rating_id = r.id ORDER BY f.id";
    public static String SQL_QUERY_ADD_LIKE = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
    public static String SQL_QUERY_DELETE_LIKE = "DELETE FROM  likes WHERE film_id = ? AND user_id = ? ";
    public static String SQL_QUERY_IS_EXIST_FILM = "SELECT COUNT(*) FROM films WHERE id = ?";
    public static String SQL_QUERY_GET_TOP_FILMS = "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.rating_id, r.rating_name," +
            "(SELECT COUNT(*) FROM likes l WHERE l.film_id = f.id) AS likes_count" +
            " FROM films f " +
            "JOIN ratings r ON f.rating_id = r.id" +
            " ORDER BY likes_count DESC, f.id LIMIT ?";
    public static String SQL_QUERY_CHECK_LIKE = "SELECT COUNT(*) FROM likes WHERE film_id = ? AND user_id = ? LIMIT 1";
    public static String SQL_QUERY_GET_FILM_BY_ID = "SELECT f.id, f.name, f.description, f.release_date, f.duration, rating_id, r.rating_name, likes_count " +
            " FROM films f JOIN ratings r ON f.rating_id = r.id " +
            " WHERE f.id = ?";
    public static String SQL_QUERY_GET_GENRE_BY_FILM_ID = "SELECT g.id, g.name FROM genres g JOIN film_genres" +
            " fg ON g.id = fg.genre_id WHERE fg.film_id = ?";
    public static String SQL_QUERY_GET_GENRE_BY_ID = "SELECT * FROM genres WHERE id = ?";
    public static String SQL_QUERY_GET_FILM_BY_GENRE = "SELECT * FROM genres WHERE id IN (SELECT id FROM film_genres WHERE film_id = ? ORDER BY id)";
    public static String SQL_QUERY_GET_ALL_GENRES = "SELECT * FROM genres ORDER BY id";
    public static String SQL_QUERY_GET_MPA_RATING_BY_ID = "SELECT * FROM ratings WHERE id = ?";
    public static String SQL_QUERY_GER_ALL_MPA_RATING = "SELECT * FROM ratings ORDER BY id";
    public static String SQL_QUERY_ADD_NEW_USER = "INSERT INTO users (login, name, email, birthday) VALUES(?, ?, ?, ?)";
    public static String SQL_QUERY_UPDATE_USER = "UPDATE users SET login = ?, name = ?, email = ?, birthday = ?  WHERE id = ?";
    public static String SQL_QUERY_GET_ALL_USERS = "SELECT * FROM users ORDER BY id LIMIT 1";
    public static String SQL_QUERY_ADD_FRIEND = "INSERT INTO friends (user_id, friend_id, status) VALUES (?, ?, ?)";
    public static String SQL_QUERY_DELETE_FRIEND = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
    public static String SQL_QUERY_GET_LIST_FRIENDS = "SELECT * FROM users WHERE users.id IN " +
            "(SELECT friends.friend_id FROM friends WHERE friends.user_id = ? AND status = 'CONFIRMED');";
    public static String SQL_QUERY_GET_CONTAINS_FRIENDS = "SELECT * FROM users WHERE id IN " +
            "(SELECT DISTINCT (friends.friend_id) FROM friends WHERE user_id = ? AND status = 'CONFIRMED'" +
            " AND friend_id IN (SELECT friend_id FROM friends WHERE user_id = ? AND status = 'CONFIRMED'))";
    public static String SQL_QUERY_IS_EXIST_USER = "SELECT COUNT(*) FROM users WHERE ID = ?";
}