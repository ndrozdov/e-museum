package ru.mkrf.label.entity.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.mkrf.label.entity.db.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigurationRowMapper implements RowMapper<Configuration> {
    @Override
    public Configuration mapRow(ResultSet rs, int rowNum) throws SQLException {
        Configuration configuration = new Configuration();

        configuration.setId(rs.getInt("rowid"));
        configuration.setName(rs.getString("name"));
        configuration.setValue(rs.getString("value"));

        return configuration;
    }
}
