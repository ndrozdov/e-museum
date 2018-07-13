package ru.mkrf.label.repository.user;

import ru.mkrf.label.entity.db.User;
import ru.mkrf.label.repository.BaseRepository;

import java.sql.SQLException;

public interface UserRepository{
    User getByLoginAndHash(User user) throws IllegalAccessException, SQLException;
}
