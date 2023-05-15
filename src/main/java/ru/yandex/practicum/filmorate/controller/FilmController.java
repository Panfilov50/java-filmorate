package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;


import java.util.*;


@RequestMapping("/films")
@RestController
@Slf4j
public class FilmController {
    private final FilmService filmService;
    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }
    @GetMapping
    public Collection<Film> getAll() {
        log.debug("Получили список всех фильмов.");
        return filmService.getFilms();
    }
    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable("filmId") int filmId){
        log.debug("Получение фильма по id {}.", filmId);
        return filmService.findFilmById(filmId);
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        log.debug("Добавили фильм: {}", film);
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film put(@NonNull @Valid @RequestBody Film film) {
        filmService.updateFilm(film);
        log.debug("Обновили фильм: {}", film);
        return film;
    }
    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable("filmId") int filmId, @PathVariable ("userId")int userId){
        log.debug("Пользователь с id {} ставит лайк фильму с id {}", userId, filmId);
        return filmService.addLikeFilms(filmId, userId);
    }
    @DeleteMapping("/{filmId}/like/{userId}")
    public Film deleteLike (@PathVariable("filmId") int filmId, @PathVariable ("userId")int userId){
        log.debug("Пользователь с id {} удаляет лайк к фильму с id {}", userId, filmId);
        return filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getBestFilms(
            @RequestParam(value = "count", defaultValue = "10", required = false) int count) {
        return filmService.getBestFilms(count);
    }



}
