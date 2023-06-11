package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/genres")
@AllArgsConstructor
@Slf4j
public class GenreController {
    private final FilmService filmService;

    @GetMapping
    public List<Genres> getGenreList() {
        log.info("запрос списка жанров");
        return filmService.getGenresList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genres getGenre(@PathVariable int id) {
        log.info("запрос жанра по id");
        return filmService.getGenres(id);
    }
}