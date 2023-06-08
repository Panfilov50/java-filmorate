package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class MPAService {
    private final MpaStorage mpaStorage;

    public Collection<Mpa> getAllMpa () {
        return mpaStorage.getAllMpa();
    }

    public Mpa getMpa(int id) {
        return mpaStorage.getMpa(id);
    }
}
