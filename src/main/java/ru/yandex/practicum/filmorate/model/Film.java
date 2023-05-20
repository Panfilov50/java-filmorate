package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.Set;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import javax.xml.transform.Source;

@Data
public class Film {
    private int filmId;

    private Set<Integer> usersLike;
    @NotBlank
    private final String name;
    @Size(max = 200)
    private final String description;
    private final LocalDate releaseDate;
    @Positive
    private final int duration;
}
