package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {
    FilmController filmController = new FilmController();
    @Test
    public void testFilmNameNull() throws ValidationFilmException {
        Film film = new Film(null, "TestDescription", LocalDate.of(2000, 01, 01), 120);
        assertThrows(
                ValidationFilmException.class, () -> filmController.validation(film)
        );
    }
    @Test
    public void testFilmNameEmpty() throws ValidationFilmException {
        Film film = new Film(" ", "TestDescription", LocalDate.of(2000, 01, 01), 120);
        assertThrows(
                ValidationFilmException.class, () -> filmController.validation(film)
        );
    }

    @Test
    public void testFilmDurationLength201() throws ValidationFilmException {
        Film film = new Film("TestName", "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", LocalDate.of(2000, 01, 01), 120);
        assertThrows(
                ValidationFilmException.class, () -> filmController.validation(film)
        );
    }

    @Test
    public void testFilmDurationLength200() throws ValidationFilmException {
        Film film = new Film("TestName", "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", LocalDate.of(2000, 01, 01), 120);
        assertEquals(filmController.validation(film), film);
    }
    @Test
    public void testFilmDurationLength199() throws ValidationFilmException {
        Film film = new Film("TestName", "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", LocalDate.of(2000, 01, 01), 120);
        assertEquals(filmController.validation(film), film);
    }
    @Test
    public void testFilmReleaseDateAfterValidation() throws ValidationFilmException {
        Film film = new Film("TestName", "TestDescription", LocalDate.of(1000, 01, 01), 120);
        assertThrows(
                ValidationFilmException.class, () -> filmController.validation(film)
        );
    }

    @Test
    public void testFilmReleaseDateBeforeValidation() throws ValidationFilmException {
        Film film = new Film("TestName", "TestDescription", LocalDate.of(2000, 01, 01), 120);
        assertEquals(filmController.validation(film), film);
    }

    @Test
    public void testFilmDurationNegativeNumber() throws ValidationFilmException {
        Film film = new Film("TestName", "TestDescription", LocalDate.of(2000, 01, 01), -1);
        assertThrows(
                ValidationFilmException.class, () -> filmController.validation(film)
        );
    }

    @Test
    public void testFilmDurationPositiveNumber() throws ValidationFilmException {
        Film film = new Film("TestName", "TestDescription", LocalDate.of(2000, 01, 01), 1);
        assertEquals(filmController.validation(film), film);
    }

}



  //  private int id;
  //  private final String name;
  //  private String description;
  //  private LocalDate releaseDate;
  //  private int duration;