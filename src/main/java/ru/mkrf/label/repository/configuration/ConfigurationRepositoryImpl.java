package ru.mkrf.label.repository.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.mkrf.label.entity.db.Configuration;
import ru.mkrf.label.entity.rowmapper.ConfigurationRowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class ConfigurationRepositoryImpl implements ConfigurationRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedPreparedStatement;

    @Override
    public List<Configuration> getAll() throws SQLException, IllegalAccessException {
        return namedPreparedStatement.query(Configuration.GET_ALL, new MapSqlParameterSource(), new ConfigurationRowMapper());
    }

    @Override
    public Configuration getById(Integer id) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("rowid", id);

        try {
            return namedPreparedStatement.queryForObject(Configuration.GET_BY_ID, parameters, new ConfigurationRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Configuration save(Configuration configuration) throws SQLException, IllegalAccessException {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource( configuration );

        if(configuration.isNew()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String[] columnNames = new String[] { "rowid" };

            namedPreparedStatement.update(Configuration.INSERT, parameters, keyHolder, columnNames);
            Map<String, Object> keys = keyHolder.getKeys();
            Integer rowid = ((Integer)keys.get("last_insert_rowid()"));
            configuration.setId(rowid);

            return configuration;
        }
        else {
            return namedPreparedStatement.update(Configuration.UPDATE, parameters) == 0 ? null : configuration;
        }
    }

    @Override
    public void delete(Integer id) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("rowid", id);

        namedPreparedStatement.update(Configuration.DELETE, parameters);
    }
}
