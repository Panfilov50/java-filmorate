package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {


    void nextId();
    List<User> getUsers();
    User createUser(User user);
    User updateUser(User user);
    void deleteUserById(int id);
    User findUserById(int id);
    User addFriend(int userId, int friendId);
    User removeFriend(int userId, int friendId);
    List<User> findAllFriends(int userId);
    List<User> findCommonFriends(int userId, int otherUserId);

}
