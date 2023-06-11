package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final LikesStorage likesStorage;
    private final FilmGenreStorage filmGenreStorage;

    public Film createFilm(Film film) {
        filmStorage.createFilm(film);
        addGenreForFilm(film);
        return film;
    }

    public Film changeFilm(Film film) {
        log.info("Запрос изменения фильма1");
        try {
            filmStorage.changeFilm(film);
        } catch (Exception e) {
            throw new FilmNotFoundException("Такого фильма не существует!");
        }
        filmGenreStorage.deleteByFilmId(film.getId());
        addGenreForFilm(film);
        return film;
    }

    public List<Film> findAllFilms() {
        log.info("Запрос фильмов");
        var films = filmStorage.findAllFilms();
        var mpaList = mpaStorage.getAllMpa();
        var genres = genreStorage.getAllGenres();
        var filmGenres = filmGenreStorage.getAllFilmGenre();
        var likes = likesStorage.getAllLikes();
        films.forEach(film -> setAllPar(film, mpaList, genres, filmGenres, likes));
        return films;
    }

    public Film findFilmById(int filmId) {
        log.info("Запрос фильма по id");
        var film = filmStorage.findFilmById(filmId);
        var mpaList = mpaStorage.getAllMpa();
        var genres = genreStorage.getAllGenres();
        var filmGenres = filmGenreStorage.getLikesFilmId(film.getId());
        var likes = likesStorage.getLikesWithFilmId(film.getId());
        setAllPar(film, mpaList, genres, filmGenres, likes);
        return film;
    }

    public void addLike(int userId, int filmId) {
        Likes like = likesStorage.getLikesCurrentUserWithFilmId(userId, filmId);
        if (like == null) {
            likesStorage.addLike(new Likes(filmId, userId));
        }
    }

    public void removeLike(int userId, int filmId) {
        Likes like = likesStorage.getLikesCurrentUserWithFilmId(userId, filmId);
        if (like == null) {
            throw new FilmNotFoundException("лайк не найден");
        }
        likesStorage.removeLike(new Likes(filmId, userId));
    }

    public List<Film> getBestFilms(int count) {
        var films = findAllFilms();
        return films.stream().sorted(Comparator.comparingInt(film -> film.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Genres getGenres(int id) {
        return genreStorage.findGenreById(id);
    }

    public List<Genres> getGenresList() {
        return genreStorage.getAllGenres();
    }

    public Mpa getMpa(int id) {
        return mpaStorage.getMpaById(id);
    }

    public List<Mpa> getMpaList() {
        var mpaList = mpaStorage.getAllMpa();
        List<Mpa> list = new ArrayList<>(mpaList);
        list.sort(Comparator.comparing(Mpa::getId));
        return list;
    }

    private void addGenreForFilm(Film film) {
        if (film.getGenres() != null && film.getGenres().size() > 0) {
            List<Genres> ratingList = film.getGenres();
            Set<Integer> uniqueId = new HashSet<>();
            ratingList.removeIf(rating -> !uniqueId.add(rating.getId()));
            film.setGenres(ratingList);
            for (var genre : film.getGenres()) {
                var filmGenre = new FilmGenre(film.getId(), genre.getId());
                filmGenreStorage.add(filmGenre);
            }
        }
    }

    private void setAllPar(Film film, Set<Mpa> mpaList, List<Genres> genres,
                           List<FilmGenre> filmGenres, Set<Likes> likes) {
        List<Genres> genreByFilm = new ArrayList<>();
        filmGenres.stream()
                .filter(f -> f.getFilmId() == film.getId())
                .forEach(f -> genreByFilm.add(new Genres(f.getGenreId(),
                                genres.stream()
                                        .filter(g -> g.getId() == f.getGenreId())
                                        .findAny().orElseThrow(() -> new FilmNotFoundException("Жанр не найден"))
                                        .getName())));

        film.setGenres(genreByFilm);
        film.getMpa().setName(mpaList.stream()
                .filter(m -> m.getId() == film.getMpa().getId())
                        .findAny().orElseThrow(() -> new FilmNotFoundException("Жанр не найден"))
                        .getName());
        Set<Integer> likesByFilm = new HashSet<>();
        likes.stream()
                .filter(l -> l.getFilmId() == film.getId())
                .forEach(l -> likesByFilm.add(l.getUserId()));
        film.setLikes(likesByFilm);

    }
}