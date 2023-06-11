package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor__ = @Autowired)
public class FilmTests {
    private  final DBFilmStorage filmStorage;
    private final DBFilmGenreStorage filmGenreStorage;
    private final DBLikesStorage likeStorage;
    private final DBUserStorage userStorage;
    private final DBMpaStorage mpaStorage;

    private void assertThatTest(Film filmTest){
        assertThat(filmTest).isNotNull();
        assertThat(filmTest.getId()).isNotZero();
        assertThat(filmTest.getName()).isEqualTo(filmTest.getName());
        assertThat(filmTest.getDescription()).isEqualTo(filmTest.getDescription());
        assertThat(filmTest.getReleaseDate()).isEqualTo(filmTest.getReleaseDate());
        assertThat(filmTest.getDuration()).isEqualTo(filmTest.getDuration());
        assertThat(filmTest.getMpa()).isEqualTo(filmTest.getMpa());
    }
    private Film createFilm1() {
        Film film1 = new Film(1, "AAA", "AAA",
                LocalDate.of(2000, 1, 1), 1, new Mpa(1, "G"));
        return film1;
    }
    private Film createFilm2() {
        Film film2 = new Film(2, "BBB", "BBB",
                LocalDate.of(2000, 1, 2), 2, new Mpa(2, "PG"));
        return film2;
    }

    @Test
    void testCreateFilm() {
        final Film film = filmStorage.createFilm(createFilm1());
        assertThatTest(film);
    }

    @Test
    void testChangeFilm() {
        final Film saved = filmStorage.createFilm(createFilm1());
        final int filmId = saved.getId();
        createFilm2().setId(filmId);

        final Film updated = filmStorage.changeFilm(createFilm2());
        assertThatTest(updated);
    }

    @Test
    void findAllFilms() {
        Film film3 = filmStorage.createFilm(new Film(3, "CCC", "CCC",
                LocalDate.of(2000, 1, 3), 3, new Mpa(1, null)));
        Film film4 = filmStorage.createFilm(new Film(4, "DDD", "DDD",
                LocalDate.of(2000, 1, 4), 3, new Mpa(2, null)));

        List<Film> allFilms = filmStorage.findAllFilms();
        System.out.println(allFilms);

        assertThat(allFilms).isNotNull();
        assertThat(allFilms.size()).isEqualTo(6);
        assertThat(allFilms.get(4)).isEqualTo(film3);
        assertThat(allFilms.get(5)).isEqualTo(film4);
    }

    @Test
    void testFindById() {
        Film film5 = filmStorage.createFilm(new Film(5, "EEE", "EEE",
                LocalDate.of(2000, 1, 5), 5, new Mpa(5, null)));
        filmStorage.createFilm(film5);
        Film createdFilm = filmStorage.findFilmById(2);

        assertThat(createdFilm).isEqualTo(film5);
    }
}