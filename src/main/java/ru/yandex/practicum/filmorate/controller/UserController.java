package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;


import ru.yandex.practicum.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    int id = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        user.setId(id);
        id++;
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User put(@RequestBody User user) {
        users.put(user.getId(), user);
        return user;
    }
}
