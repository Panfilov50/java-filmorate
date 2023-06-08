package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundFilmException;
import ru.yandex.practicum.filmorate.exception.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.*;

import java.util.List;

@Component
@Slf4j
public class LikeDaoImpl implements LikesStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreStorage genreStorage;
    private final MpaStorage mpaStorage;
    private final FilmDaoImpl filmStorage;
    private final UserDaoImpl userStorage;

    public LikeDaoImpl(JdbcTemplate jdbcTemplate,
                        GenreStorage genreStorage,
                        MpaStorage mpaStorage,
                        FilmDaoImpl filmStorage,
                        UserDaoImpl userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreStorage = genreStorage;
        this.mpaStorage = mpaStorage;
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    @Override
    public void addLike(int id, int userId) {
        if (!filmStorage.isFilmExists(id)) throw new NotFoundFilmException("Film not found");
        if (!userStorage.isUserExists(userId)) throw new NotFoundUserException("User not found");
        String sql = "INSERT INTO LIKES (user_id, film_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, id);
        log.info("User id = {} add like to film id = {}", userId, id);
    }

    @Override
    public void removeLike(int id, int userId) {
        if (!filmStorage.isFilmExists(id)) throw new NotFoundFilmException("Film not found");
        if (!userStorage.isUserExists(userId)) throw new NotFoundUserException("User not found");
        if (!isLikeExist(userId, id)) throw new NotFoundUserException("User didn't add like to film");
        String sql = "DELETE FROM LIKES WHERE user_id = ? AND film_id = ?";
        jdbcTemplate.update(sql, userId, id);
    }

    @Override
    public boolean isLikeExist(int userId, int filmId) {
        String sql = "SELECT * FROM LIKES WHERE user_id = ? AND film_id = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, userId, filmId);
        return userRows.next();
    }
    @Override
    public List<Film> getPopular(int count) {
        String sql = "SELECT FILMS.FILM_ID, NAME, DESCRIPTION, RELEASEDATE, DURATION, RATE_ID , " +
                "COUNT(L.USER_ID) as RATING FROM FILMS LEFT JOIN LIKES L on FILMS.FILM_ID = L.FILM_ID " +
                "GROUP BY FILMS.FILM_ID " +
                "ORDER BY RATING DESC LIMIT ?";
        System.out.println(count);
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> new Film(
                rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("releaseDate").toLocalDate(),
                rs.getInt("duration"),
                genreStorage.getFilmGenres(rs.getInt("film_id")),
                mpaStorage.getMpa(rs.getInt("rate_id")),
                rs.getInt("rating")
        ), count);
        return films;
    }
}
