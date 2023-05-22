package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundFilmException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ValidationFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final UserStorage userStorage;
    private int id = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Film validation(Film film) {
        LocalDate isValidation = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(isValidation)) {
            throw new ValidationFilmException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        return film;
    }

    @Override
    public void nextId() {
        id++;
    }

    @Override
    public Film addFilm(Film film) {
        validation(film);
        film.setFilmId(id);
        nextId();
        films.put(film.getFilmId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getFilmId())) {
            films.put(film.getFilmId(), film);
        } else {
            throw new NotFoundFilmException("Такого фильма нет в базе.");
        }
        return film;
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film findFilmById(int id) {
        if (films.isEmpty() || !films.containsKey(id)) {
            throw new NotFoundFilmException(String.format("Фильм № %d не найден", id));
        } else {
            return films.get(id);
        }

    }

    @Override
    public Film addLikeFilms(int filmId, int userId) {
        if (!films.containsKey(filmId)) {
            throw new NotFoundFilmException(String.format("Фильма с id %d не существует.", filmId));
        }
        userStorage.findUserById(userId);
        films.get(filmId).getUsersLike().add(userId);
        films.put(films.get(filmId).getFilmId(), films.get(filmId));
        return films.get(filmId);
    }

    @Override
    public Film deleteLike(int filmId, int userId) {
        if (films.isEmpty() || !films.containsKey(filmId)) {
            throw new ValidationFilmException("Фильма с таким ID с таким нет.");
        } else if (!userStorage.getUsers().contains(userStorage.findUserById(userId))) {
            throw new NotFoundFilmException(String.format("Пользователь с id %d не существует.", userId));
        } else if (!films.get(filmId).getUsersLike().contains(userId)) {
            throw new UserAlreadyExistException("Пользователья с таким ID еше не ставил лайк этому фильму.");
        } else {
            films.get(filmId).getUsersLike().remove(userId);
            return (films.get(filmId));
        }

    }

    @Override
    public List<Film> getBestFilms(int count) {
        if (films.isEmpty()) {
            throw new NotFoundFilmException("Список фильмов пуст.");
        }
        return films.values().stream()
                .sorted(this::compareFilmsReverse)
                .limit(count)
                .collect(Collectors.toList());
    }

    private int compareFilmsReverse(Film f1, Film f2) {
        int comp1 = 0;
        int comp2 = 0;
        if (f1.getUsersLike() != null) {
            comp1 = f1.getUsersLike().size();
        }
        if (f2.getUsersLike() != null) {
            comp2 = f2.getUsersLike().size();
        }
        return comp2 - comp1;
    }
}