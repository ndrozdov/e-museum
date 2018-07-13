package ru.mkrf.label.service.configuration;

import ru.mkrf.label.entity.db.Configuration;

import java.sql.SQLException;
import java.util.List;

public interface ConfigurationService {
    List<Configuration> getAll() throws SQLException, IllegalAccessException;
    Configuration getById(Integer id) throws SQLException, IllegalAccessException;
    Configuration save(Configuration configuration) throws SQLException, IllegalAccessException;
    void delete(Integer id) throws SQLException, IllegalAccessException;
}
