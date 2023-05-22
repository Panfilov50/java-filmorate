package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.Getter;

@Data
public class ErrorResponse {
    private final String error;
}
