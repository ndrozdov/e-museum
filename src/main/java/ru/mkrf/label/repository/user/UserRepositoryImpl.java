package ru.mkrf.label.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mkrf.label.entity.db.User;
import ru.mkrf.label.entity.rowmapper.UserRowMapper;

import java.sql.SQLException;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedPreparedStatement;

    @Override
    public User getByLoginAndHash(User user) throws IllegalAccessException, SQLException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("login", user.getLogin());
        parameters.addValue("hash", user.getHash());

        try {
            return namedPreparedStatement.queryForObject(User.GET_BY_LOGIN_AND_HASH, parameters, new UserRowMapper());
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        }
    }

}
