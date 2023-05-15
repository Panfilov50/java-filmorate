package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.exception.ValidationFilmException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
 /*    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserEmailEmpty() throws ValidationFilmException {
        User user = new User(" ","Test"," ", LocalDate.of(2000,01,01));
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "должно иметь формат адреса электронной почты", constraintViolations.iterator().next().getMessage());
    }
    @Test
    public void testUserEmailNull() throws ValidationFilmException {
        User user = new User(null,"TestLogin"," ", LocalDate.of(2000,01,01));
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "не должно равняться null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testUserEmailCorrect() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin"," ", LocalDate.of(2000,01,01));
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);
        assertEquals( 0, constraintViolations.size() );
        assertEquals(user.getEmail(), "TestEmail@test.com");
    }

    @Test
    public void testUserLoginNull() throws ValidationFilmException {
        User user = new User("TestEmail@test.com",null,"TestName", LocalDate.of(2000,01,01));
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "не должно равняться null", constraintViolations.iterator().next().getMessage());
    }


    @Test
    public void testUserLoginEmpty() throws ValidationFilmException {
        User user = new User("TestEmail@test.com"," "," ", LocalDate.of(2000,01,01));
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "не должно быть пустым", constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testUserLoginCorrect() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin"," ", LocalDate.of(2000,01,01));
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);
        assertEquals( 0, constraintViolations.size() );
        assertEquals(user.getLogin(), "TestLogin");
    }

    @Test
    public void testUserNameNull() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin",null, LocalDate.of(2000,01,01));
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void testUserNameEmpty() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin"," ", LocalDate.of(2000,01,01));
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void testUserNameCorrect() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin","TestName", LocalDate.of(2000,01,01));
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);
        assertEquals( 0, constraintViolations.size() );
        assertEquals(user.getName(), "TestName");
    }
    @Test
    public void testUserBirthdayNull() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin","TestName", null);
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "не должно равняться null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testUserBirthdayBeforeNow() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin","TestName", LocalDate.now().plusYears(1));
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "должно содержать прошедшую дату", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testUserBirthdayCorrect() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin","TestName", LocalDate.of(2000,01,01));
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);
        assertEquals( 0, constraintViolations.size() );
        assertEquals(user.getBirthday(), LocalDate.of(2000,01,01));
    }

  */
}
