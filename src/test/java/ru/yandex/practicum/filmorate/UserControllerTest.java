package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ValidationFilmException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    UserController userController = new UserController();

    @Test
    public void testUserEmailEmpty() throws ValidationFilmException {
        User user = new User(" ","Test"," ", LocalDate.of(2000,01,01));
        assertThrows(
                UserAlreadyExistException.class, () -> userController.validation(user)
        );
    }

    @Test
    public void testUserEmailCorrect() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","Test"," ", LocalDate.of(2000,01,01));
        assertEquals(userController.validation(user), user);
    }

    @Test
    public void testUserLoginNull() throws ValidationFilmException {
        User user = new User(" ",null," ", LocalDate.of(2000,01,01));
        assertThrows(
                UserAlreadyExistException.class, () -> userController.validation(user)
        );
    }

    @Test
    public void testUserLoginEmpty() throws ValidationFilmException {
        User user = new User(" "," "," ", LocalDate.of(2000,01,01));
        assertThrows(
                UserAlreadyExistException.class, () -> userController.validation(user)
        );
    }

    @Test
    public void testUserLoginCorrect() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin"," ", LocalDate.of(2000,01,01));
        assertEquals(userController.validation(user), user);
    }

    @Test
    public void testUserNameNull() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin",null, LocalDate.of(2000,01,01));
        userController.validation(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void testUserNameEmpty() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin"," ", LocalDate.of(2000,01,01));
        userController.validation(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void testUserNameCorrect() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin","TestName", LocalDate.of(2000,01,01));
        assertEquals(user.getName(), "TestName");
    }
    @Test
    public void testUserBirthdayNull() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin","TestName", null);
        assertThrows(
                UserAlreadyExistException.class, () -> userController.validation(user)
        );
    }

    @Test
    public void testUserBirthdayBeforeNow() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin","TestName", LocalDate.now().plusYears(1));
        assertThrows(
                UserAlreadyExistException.class, () -> userController.validation(user)
        );
    }

    @Test
    public void testUserBirthdayCorrect() throws ValidationFilmException {
        User user = new User("TestEmail@test.com","TestLogin","TestName", LocalDate.of(2000,01,01));
        assertEquals(userController.validation(user), user);
    }
}
