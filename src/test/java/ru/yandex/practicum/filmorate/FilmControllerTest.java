package ru.yandex.practicum.filmorate;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FilmControllerTest {
    private static Validator validator;
    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    FilmController filmController = new FilmController();
    @Test
    public void testFilmNameNull() throws ValidationFilmException {
        Film film = new Film(null, "TestDescription", LocalDate.of(2000, 01, 01), 120);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
          assertEquals( 1, constraintViolations.size() );
        assertEquals( "не должно быть пустым", constraintViolations.iterator().next().getMessage());
    }
    @Test
    public void testFilmNameEmpty() throws ValidationFilmException {
        Film film = new Film(" ", "TestDescription", LocalDate.of(2000, 01, 01), 120);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "не должно быть пустым", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testFilmDurationLength201() throws ValidationFilmException {
        Film film = new Film("TestName", "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", LocalDate.of(2000, 01, 01), 120);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "размер должен находиться в диапазоне от 0 до 200", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testFilmDurationLength200() throws ValidationFilmException {
        Film film = new Film("TestName", "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", LocalDate.of(2000, 01, 01), 120);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
        assertEquals( 0, constraintViolations.size() );
    }
    @Test
    public void testFilmDurationLength199() throws ValidationFilmException {
        Film film = new Film("TestName", "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", LocalDate.of(2000, 01, 01), 120);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
        assertEquals( 0, constraintViolations.size() );
    }
    @Test
    public void testFilmReleaseDateAfterValidation() throws ValidationFilmException {
        Film film = new Film("TestName", "TestDescription", LocalDate.of(2000, 01, 01), 120);
        assertEquals(film.getReleaseDate(),LocalDate.of(2000, 01, 01));
    }

    @Test
    public void testFilmReleaseDateBeforeValidation() throws ValidationFilmException {
        Film film = new Film("TestName", "TestDescription", LocalDate.of(1000, 01, 01), 120);
        assertThrows(
                ValidationFilmException.class, () -> filmController.validation(film)
        );
    }

    @Test
    public void testFilmDurationNegativeNumber() throws ValidationFilmException {
        Film film = new Film("TestName", "TestDescription", LocalDate.of(2000, 01, 01), -10);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "должно быть больше 0", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testFilmDurationPositiveNumber() throws ValidationFilmException {
        Film film = new Film("TestName", "TestDescription", LocalDate.of(2000, 01, 01), 1);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
        assertEquals( 0, constraintViolations.size() );
    }

}



  //  private int id;
  //  private final String name;
  //  private String description;
  //  private LocalDate releaseDate;
  //  private int duration;