package ru.mkrf.label.service.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mkrf.label.entity.db.Configuration;
import ru.mkrf.label.repository.configuration.ConfigurationRepository;

import java.sql.SQLException;
import java.util.List;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    @Autowired
    private ConfigurationRepository repository;

    @Override
    public List<Configuration> getAll() throws SQLException, IllegalAccessException {
        return repository.getAll();
    }

    @Override
    public Configuration getById(Integer id) throws SQLException, IllegalAccessException {
        return repository.getById(id);
    }

    @Override
    public Configuration save(Configuration configuration) throws SQLException, IllegalAccessException {
        return repository.save(configuration);
    }

    @Override
    public void delete(Integer id) throws SQLException, IllegalAccessException {
        repository.delete(id);
    }
}
