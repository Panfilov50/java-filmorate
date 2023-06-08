package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {


    Film addFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getFilms();

    Optional<Film> findFilmById(int filmId);
    void deleteFilm(Film film);

    Film addLikeFilms(int filmId, int userId);

    Film deleteLike(int filmId, int userId);

    List<Film> getBestFilms(int count);

}
