package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.TreeSet;

import lombok.*;
import jakarta.validation.constraints.*;
@Data
public class User {
    private int id;
    private TreeSet<Integer> friends;
    @NotNull
    @Email
    private final String email;
    @NotBlank
    private final String login;
    private String name;
    @NotNull
    @Past
    private final LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.birthday = birthday;
        if (name == null || name.isBlank()){
            this.name = login;
        } else {
            this.name = name;
        }
    }
}
