package ru.yandex.practicum.filmorate;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;


import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ValidatorFilmTest {
    private static Validator validator;
    private static FilmStorage filmStorage;
    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testFilmNameNull() throws ValidationFilmException {
        Film film = new Film(1,null, "TestDescription", LocalDate.of(2000, 01, 01), 120, 1,null,0);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
          assertEquals( 1, constraintViolations.size() );
        assertEquals( "не должно быть пустым", constraintViolations.iterator().next().getMessage());
    }
    @Test
    public void testFilmNameEmpty() throws ValidationFilmException {
        Film film = new Film(1," ", "TestDescription", LocalDate.of(2000, 01, 01), 120, 1,null,0);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "не должно быть пустым", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testFilmDurationLength201() throws ValidationFilmException {
        Film film = new Film(1,"TestName", "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", LocalDate.of(2000, 01, 01), 120,1,null,0);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "размер должен находиться в диапазоне от 0 до 200", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testFilmDurationLength200() throws ValidationFilmException {
        Film film = new Film(1,"TestName", "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", LocalDate.of(2000, 01, 01), 120,1,null,0);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
        assertEquals( 0, constraintViolations.size() );
    }
    @Test
    public void testFilmDurationLength199() throws ValidationFilmException {
        Film film = new Film(1,"TestName", "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", LocalDate.of(2000, 01, 01), 120,1,null,0);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
        assertEquals( 0, constraintViolations.size() );
    }
    @Test
    public void testFilmReleaseDateAfterValidation() throws ValidationFilmException {
        Film film = new Film(1,"TestName", "TestDescription", LocalDate.of(2000, 01, 01), 120,1,null,0);
        assertEquals(film.getReleaseDate(),LocalDate.of(2000, 01, 01));
    }

    @Test
    public void testFilmDurationNegativeNumber() throws ValidationFilmException {
        Film film = new Film(1,"TestName", "TestDescription", LocalDate.of(2000, 01, 01), -10,1,null,0);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "должно быть больше 0", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testFilmDurationPositiveNumber() throws ValidationFilmException {
        Film film = new Film(1,"TestName", "TestDescription", LocalDate.of(2000, 01, 01), 1,1,null,0);
        Set<ConstraintViolation<Film>> constraintViolations =
                validator.validate(film);
        assertEquals( 0, constraintViolations.size() );
    }
}



