package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    int id = 1;
    private final Map<Integer, Film> films = new HashMap<>();
    public Film validation (Film film){
        LocalDate isValidation = LocalDate.of(1895, 12, 28);
        if(film.getName() == null || film.getName().isBlank()){
            throw new ValidationFilmException("Название фильма не может быть пустым");
        }
        if(film.getDescription().length()>200){
            throw new ValidationFilmException("Максимальная длина описания — 200 символов");
        }
        if(film.getReleaseDate() == null || film.getReleaseDate().isBefore(isValidation)){
            throw new ValidationFilmException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if(film.getDuration()<0) {
            throw new ValidationFilmException("Продолжительность фильма должна быть положительной.");
        }
        return film;
    }
    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        validation(film);
        film.setId(id);
        id++;
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film put(@RequestBody Film film) {
        validation(film);
        films.put(film.getId(), film);
        return film;
    }
}
