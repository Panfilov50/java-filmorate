package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    public User valid(User user) {
        if ((user.getEmail().isBlank() || user.getEmail().isEmpty() || !user.getEmail().contains("@"))) {
            log.error("User email is empty or invalid {}", user.getName());
            throw new ValidationException("Имейл не может быть пустым или не содержать символ @");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("User login is empty or invalid {}", user.getName());
            throw new ValidationException("Название не может быть пустым или содержать пробелы");
        }
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            log.error("User name is empty {}", user.getName());
            user.setName(user.getLogin());
        }
        if (user.getBirthday().equals(LocalDate.now()) || user.getBirthday().isAfter(LocalDate.now())) {
            log.error("User birthday is invalid. {}", user.getName());
            throw new ValidationException("Неверная дата рождения");
        }
        return user;
    }

    public User createUser(User user) {
        valid(user);
        return userStorage.createUser(user);
    }

    public User changeUser(int id, User user) {
        valid(user);
        return userStorage.changeUser(id, user);
    }

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User findUserById(int id) {
        return userStorage.findUserById(id);
    }

    public Friends addFriend(int userId, int friendId) {
        var friends = friendsStorage.findCommonFriends(userId, friendId);
        if (friends == null) {
            try {
                return friendsStorage.addFriend(new Friends(userId, friendId, false));
            } catch (Exception e) {
                throw new UserNotFoundException("Нет данных о ждужбе");
            }
        } else if (friends.isStatus()) {
            return friends;
        } else if (friends.getFriendId() == userId) {
            friends.setStatus(true);
            return friendsStorage.update(friends);
        }
        return friends;
    }

    public void removeFriend(int userId, int friendId) {
        var friends = friendsStorage.findCommonFriends(userId, friendId);
        if (friends == null) {
            throw new UserNotFoundException("Нет данных о ждужбе");
        } else {
            if (friends.isStatus()) {
                if (friends.getUserId() == userId) {
                    friends.setUserId(friendId);
                    friends.setFriendId(userId);
                }
                friends.setStatus(false);
                friendsStorage.update(friends);
            } else {
                if (friends.getUserId() == userId) {
                    friendsStorage.deleteFriend(friends);
                }
            }
        }
    }

    public List<User> findAllFriends(int userId) {
        var friends = friendsStorage.findAllFriends(userId);
        List<User> users = new ArrayList<>();
        friends.forEach(f -> {
            if (f.getUserId() == userId) {
                var user = userStorage.findUserById(f.getFriendId());
                users.add(user);
            } else {
                var user = userStorage.findUserById(f.getUserId());
                users.add(user);
            }
        });
        return users;
    }

    public List<User> findCommonFriends(int userId, int otherUserId) {
        var friendshipsByUser1 = friendsStorage.findAllFriends(userId);
        var friendshipsByUser2 = friendsStorage.findAllFriends(otherUserId);
        List<Integer> friendIdByUser1 = new ArrayList<>();
        List<Integer> friendIdByUser2 = new ArrayList<>();
        friendshipsByUser1.forEach(f -> {
            if (f.getUserId() == userId) {
                friendIdByUser1.add(f.getFriendId());
            } else {
                friendIdByUser1.add(f.getUserId());
            }
        });
        friendshipsByUser2.forEach(f -> {
            if (f.getUserId() == userId) {
                friendIdByUser2.add(f.getFriendId());
            } else {
                friendIdByUser2.add(f.getUserId());
            }
        });
        friendIdByUser1.retainAll(friendIdByUser2);
        List<User> users = new ArrayList<>();
        for (var commonFriendId : friendIdByUser1) {
            users.add(userStorage.findUserById(commonFriendId));
        }
        return users;
    }
}