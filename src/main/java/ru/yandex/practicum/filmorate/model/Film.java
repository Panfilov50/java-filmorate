package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.Set;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
public class Film {
    private int filmId;
    private Set<Integer> usersLike;

    @NotBlank
    private final String name;
    @Size(min = 0, max = 200)
    private final String description;
    private final LocalDate releaseDate;
    @Positive
    private final int duration;


}
