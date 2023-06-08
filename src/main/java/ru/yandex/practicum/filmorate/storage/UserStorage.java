package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    List<User> getUsers();

    Optional<User> createUser(User user);

    User updateUser(User user);

    void deleteUserById(int id);

    Optional<User> findUserById(int id);

    Optional<User> addFriend(int userId, int friendId) throws NotFoundUserException;

    Optional<User> removeFriend(int userId, int friendId);

    List<User> findAllFriends(int userId);

    List<User> findCommonFriends(int userId, int otherUserId);

}
