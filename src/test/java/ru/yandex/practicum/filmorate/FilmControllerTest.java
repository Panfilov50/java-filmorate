package ru.yandex.practicum.filmorate;


import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RestController
public class FilmControllerTest {
  /*  private static Validator validator;
    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    private final InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmControllerTest(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }
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
                ValidationFilmException.class, () -> inMemoryFilmStorage.validation(film)
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
*/
}



