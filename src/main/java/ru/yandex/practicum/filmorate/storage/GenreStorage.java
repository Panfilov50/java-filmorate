package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public interface GenreStorage {
    Set<Genre> getFilmGenres(int filmId);
    void updateGenresOfFilm(Film film);
    Collection<Genre> getAllGenres ();
    Genre getGenre(int id);
}