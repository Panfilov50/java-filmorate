package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@AllArgsConstructor
@Slf4j
public class MpaController {

    private final FilmService filmService;

    @GetMapping
    public List<Mpa> getMpaList() {
        log.info("запрос списка рейтингов");
        return filmService.getMpaList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mpa getMpa(@PathVariable int id) {
        log.info("запрос рейтинга по id");
        return filmService.getMpa(id);
    }
}