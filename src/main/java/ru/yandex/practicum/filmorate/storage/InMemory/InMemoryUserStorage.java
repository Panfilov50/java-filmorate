package ru.yandex.practicum.filmorate.storage.InMemory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;


import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component("inMemoryUserStorage")
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {

    private int id = 1;
    public final Map<Integer, User> users = new HashMap<>();

    public void nextId() {
        id++;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> createUser(User user) {
        user.setId(id);
        nextId();
        users.put(user.getId(), user);
        return Optional.of(user);
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new NotFoundUserException(String.format("Пользователь № %d не найден", user.getId()));
        }
        return user;
    }

    @Override
    public void deleteUserById(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundUserException(String.format("Пользователь № %d не найден", id));
        } else {
            users.remove(id);
        }
    }

    @Override
    public Optional<User> findUserById(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundUserException(String.format("Пользователь № %d не найден", id));
        } else {
            return Optional.ofNullable(users.get(id));
        }
    }

    @Override
    public Optional<User> addFriend(int userId, int friendId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundUserException(String.format("Пользователя с id %d не существует.", userId));
        }
        if (!users.containsKey(friendId)) {
            throw new NotFoundUserException(String.format("Пользователя с id %d не существует.", friendId));
        }
        users.get(userId).getFriends().add(friendId);
        users.get(friendId).getFriends().add(userId);
        log.info("Пользователь с id: {} добавил в друзья пользователя с id: {}", userId, friendId);

        return null;
    }

    @Override
    public Optional<User> removeFriend(int userId, int friendId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundUserException(String.format("Пользователя с id %d не существует.", userId));
        }
        if (!users.containsKey(friendId)) {
            throw new NotFoundUserException(String.format("Пользователя с id %d не существует.", friendId));
        }

        users.get(userId).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(userId);
        log.info("Пользователь с id: {} удалил из друзей пользователя с id: {}", userId, friendId);
        return null;
    }

    @Override
    public List<User> findAllFriends(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundUserException(String.format("Пользователя с id %d не существует.", id));
        }
        Set<Integer> userFriends = users.get(id).getFriends();
        log.info("all friends found");
        return userFriends.stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findCommonFriends(int firstUserId, int secondUserId) {
        if (!users.containsKey(firstUserId)) {
            throw new NotFoundUserException(String.format("Пользователя с id %d не существует.", firstUserId));
        }
        if (!users.containsKey(secondUserId)) {
            throw new NotFoundUserException(String.format("Пользователя с id %d не существует.", secondUserId));
        }
        Set<Integer> firstUserFriends = users.get(firstUserId).getFriends();
        Set<Integer> secondUserFriends = users.get(secondUserId).getFriends();
        log.info("common friends found");
        return firstUserFriends.stream()
                .filter(secondUserFriends::contains)
                .map(users::get)
                .collect(Collectors.toList());
    }
}
