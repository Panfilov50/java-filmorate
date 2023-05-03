package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import lombok.*;
import jakarta.validation.constraints.*;
import ru.yandex.practicum.filmorate.exception.ValidationFilmException;

@Data
public class Film {
    private int id;

    @NotBlank
    private final String name;
    @Size(min = 0, max = 200)
    private final String description;
    // Я так и не нашел какое ограничение могу использовать для валидации даты X>Y, по этому оставил свое.
    private final LocalDate releaseDate;
    @Positive
    private final int duration;


}
