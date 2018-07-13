package ru.mkrf.label.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mkrf.label.repository.BaseRepository;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractBaseService<T> {
    @Autowired
    private BaseRepository<T> repository;

    public List<T> getAll() throws SQLException, IllegalAccessException {
        return repository.getAll();
    }

    public T getById(Integer id) throws SQLException, IllegalAccessException {
        return repository.getById(id);
    }

    public T save(T t, Integer parentId) throws SQLException, IllegalAccessException {
        return repository.save(t, parentId);
    }

    public void delete(Integer id) throws SQLException, IllegalAccessException {
        repository.delete(id);
    }
}
