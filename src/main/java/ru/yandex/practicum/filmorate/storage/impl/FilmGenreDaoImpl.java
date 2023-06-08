package ru.yandex.practicum.filmorate.storage.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

@Component("FilmGenreDaoImpl")
@Data
@Slf4j
public class FilmGenreDaoImpl implements GenreStorage {

    JdbcTemplate jdbcTemplate;
    public FilmGenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Set<Genre> getFilmGenres(int filmId) {
        String sql = "SELECT GENRES.GENRE_ID, GENRE FROM FILM_GENRES JOIN GENRES " +
                "ON FILM_GENRES.GENRE_ID = GENRES.GENRE_ID " +
                "WHERE FILM_ID = ?";
        return new TreeSet<>(jdbcTemplate.query(sql, (rs, rowNum) -> new Genre(
                        rs.getInt("genre_id"),
                        rs.getString("genre")),
                filmId
        ));
    }

    @Override
    public void updateGenresOfFilm(Film film) {
        jdbcTemplate.update("DELETE FROM FILM_GENRES WHERE film_id = ?", film.getId());
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update("INSERT INTO FILM_GENRES (film_id, genre_id) VALUES (?, ?)",
                        film.getId(), genre.getId());
            }
        }
    }
    @Override
    public Collection<Genre> getAllGenres () {
        return jdbcTemplate.query("SELECT * FROM GENRES",
                ((rs, rowNum) -> new Genre(
                        rs.getInt("genre_id"),
                        rs.getString("genre"))
                ));
    }
    @Override
    public Genre getGenre(int id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT GENRE FROM GENRES WHERE GENRE_ID = ?", id);
        if (userRows.next()) {
            Genre genre = new Genre(
                    id,
                    userRows.getString("genre")
            );
            log.info("Response genre = {} ", genre);
            return genre;
        } else throw new GenreNotFoundException("Attempt to get genre with absent id");
    }
}
