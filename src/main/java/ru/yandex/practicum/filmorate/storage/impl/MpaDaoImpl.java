package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.MPANotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.Collection;

@Component
public class MpaDaoImpl implements MpaStorage {
    JdbcTemplate jdbcTemplate;
    public MpaDaoImpl (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Mpa getMpa(int mpaId) {
        String sql = "SELECT MPA_NAME FROM RATES_MPA WHERE MPA_ID = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, mpaId);
        if (userRows.next()) {
            return new Mpa(mpaId,
                    userRows.getString("mpa_name"));
        }
        else throw new MPANotFoundException("Attempt to get mpa with absent id");
    }
@Override
    public Collection<Mpa> getAllMpa () {
        return jdbcTemplate.query("SELECT * FROM RATES_MPA",
                ((rs, rowNum) -> new Mpa(
                        rs.getInt("mpa_id"),
                        rs.getString("mpa_name"))
                ));
    }
}
