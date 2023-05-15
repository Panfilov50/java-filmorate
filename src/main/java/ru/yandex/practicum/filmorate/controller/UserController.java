package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import ru.yandex.practicum.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        log.debug("Список пользователей");
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.debug("Получили пользователя с id: {}", id);
        return userService.findUserById(id);
    }

    @PostMapping()
    public User createUser(@RequestBody User user) {
        log.debug("Добавили: {}", user);
        return userService.createUser(user);
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) {
        log.debug("Обновили: {}", user);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable int id) {
        log.debug("Удалили пользователя по id: {}", id);
        userService.deleteUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Добавили пользователя с id {}, в друзья к пользователю с id {}", friendId, id);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Удалили пользователя с id {}, из друзей пользователя с id {}", friendId, id);
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsUser(@PathVariable int id) {
        log.debug("Список друзей пользователя с id: {}", id);
        return userService.findAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getUsers(@PathVariable int id, @PathVariable int otherId) {
        log.debug("Список общих друзей пользователей: {}, {}", id, otherId);
        return userService.findCommonFriends(id, otherId);
    }
}
