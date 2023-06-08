package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;


import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Primary
@Component("UserDaoImpl")
public class UserDaoImpl implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

        @Override
        public Optional<User> createUser(User user) {
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("USERS")
                    .usingGeneratedKeyColumns("user_id");
            user.setId(simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue());
            log.info("New user added: {}", user);
            return Optional.of(user);
        }

    @Override
    public User updateUser(User user) {
        if (isUserExists(user.getId())) {
            String sqlQuery = "UPDATE USERS SET " +
                    "email = ?, login = ?, name = ?, birthday = ? " +
                    "WHERE user_id = ?";
            jdbcTemplate.update(sqlQuery,
                    user.getEmail(),
                    user.getLogin(),
                    user.getName(),
                    user.getBirthday(),
                    user.getId());
            log.info("User {} has been successfully updated", user);
            return user;
        } else {
            throw new NotFoundUserException(String.format("Attempt to update user with " +
                    "absent id = %d", user.getId()));
        }
    }

    @Override
    public List<User> getUsers() {
        String sql = "SELECT * FROM USERS ";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                rs.getInt("user_id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate())
        );
    }
    @Override
    public void deleteUserById(int id) {
        if (isUserExists(id)) {
            String sql = "DELETE FROM USERS WHERE user_id = ?";
            jdbcTemplate.update(sql, id);
        } else throw new NotFoundUserException(String.format("Attempt to delete user with " +
                "absent id = %d", id));
    }

    @Override
    public Optional<User> findUserById(int id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * FROM USERS WHERE user_id = ?", id);
        if (userRows.first()) {
            User user = new User(
                    userRows.getInt("user_id"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    userRows.getDate("birthday").toLocalDate()
            );
            log.info("Found user id = {}", id);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }
    public boolean isUserExists(int id) {
        String sql = "SELECT * FROM USERS WHERE user_id = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, id);
        return userRows.first();
    }

    @Override
    public Optional<User> addFriend(int userId, int friendId) throws NotFoundUserException {
        Optional<User> user = findUserById(userId);
        try {
            findUserById(friendId);
        } catch (RuntimeException e) {
            throw new NotFoundUserException(String.format("Attempt to delete user with " +
                    "absent id = %d", friendId));
        }
        String sqlQuery = "INSERT INTO FRIENDSHIPS (user_id, friend_id) VALUES(?, ?)";
        jdbcTemplate.update(sqlQuery, userId, friendId);
        return user;
    }
    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        User user = User.builder()
                .id(resultSet.getInt("user_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
        user.setFriends(getFriendsByUserId(user.getId()).stream().map(User::getId).collect(Collectors.toSet()));
        return user;
    }
    public List<User> getFriendsByUserId(Integer id) {
        String sqlQuery = "SELECT user_id, email, login, name, birthday FROM users WHERE user_id IN" +
                "(SELECT friend_id FROM friends WHERE user_id=?)";
        return new ArrayList<>(jdbcTemplate.query(sqlQuery, this::mapRowToUser, id));
    }

    @Override
    public Optional<User> removeFriend(int userId, int friendId) {
        Optional<User> user = findUserById(userId);
        String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
        return user;
    }

    @Override
    public List<User> findAllFriends(int userId) {
        String sqlQuery = "SELECT user_id, email, login, name, birthday FROM users WHERE user_id IN" +
                "(SELECT friend_id FROM friends WHERE user_id=?)";
        return new ArrayList<>(jdbcTemplate.query(sqlQuery, this::mapRowToUser, userId));
    }

    @Override
    public List<User> findCommonFriends(int userId, int otherUserId) {
        return null;
    }

}
