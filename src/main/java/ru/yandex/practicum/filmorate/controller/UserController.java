package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;

import ru.yandex.practicum.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    int id = 1;
    private final Map<Integer, User> users = new HashMap<>();
    public User validation (User user){
        if(user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")){
            throw new UserAlreadyExistException("Адрес электронной указан не корректно.");
        }
        if(user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")){
            throw new UserAlreadyExistException("Логин не может быть пустым и содержать пробелы.");
        }
        if(user.getName() == null || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        if(user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            throw new UserAlreadyExistException("Не корректная дата рождения.");
        }
        return user;
    }

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        validation(user);
        user.setId(id);
        id++;
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User put(@RequestBody User user) {
        validation(user);
        users.put(user.getId(), user);
        return user;
    }
}
