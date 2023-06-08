package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.exception.NotFoundFilmException;
import ru.yandex.practicum.filmorate.exception.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikesStorage {

     void addLike(int id, int userId);

     void removeLike(int id, int userId);

     boolean isLikeExist(int userId, int filmId);
     List<Film> getPopular(int count);
}

