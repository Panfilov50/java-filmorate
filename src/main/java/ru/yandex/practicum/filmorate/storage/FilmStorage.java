package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {


    void nextId();

    Film validation(Film film);

    Film addFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getFilms();

    Film findFilmById(int filmId);

    Film addLikeFilms(int filmId, int userId);

    Film deleteLike(int filmId, int userId);

    List<Film> getBestFilms(int count);

}
