package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public Optional<User> createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public void deleteUserById(int id) {
        userStorage.deleteUserById(id);
    }

    public Optional<User> findUserById(int id) {
        return userStorage.findUserById(id);
    }

    public Optional<User> addFriend(int userId, int friendId) {
        return userStorage.addFriend(userId, friendId);
    }

    public Optional<User> removeFriend(int userId, int friendId) {
        return userStorage.removeFriend(userId, friendId);
    }

    public List<User> findAllFriends(int userId) {
        return userStorage.findAllFriends(userId);
    }

    public List<User> findCommonFriends(int userId, int otherUserId) {
        return userStorage.findCommonFriends(userId, otherUserId);
    }
}
