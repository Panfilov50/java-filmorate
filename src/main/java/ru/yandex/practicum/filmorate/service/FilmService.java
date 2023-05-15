package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;


import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Film findFilmById(int filmId) {
        return filmStorage.findFilmById(filmId);
    }

    public Film addLikeFilms(int filmId, int userId){
       return filmStorage.addLikeFilms(filmId,userId);
    }

    public List<Film> getBestFilms(int count) {
        return filmStorage.getBestFilms(count);
    }

    public Film deleteLike(int filmId, int userId) {
        return filmStorage.deleteLike(filmId, userId);
    }
}
