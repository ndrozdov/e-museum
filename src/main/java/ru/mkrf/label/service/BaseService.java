package ru.mkrf.label.service;

import java.sql.SQLException;
import java.util.List;

public interface BaseService<T> {
    List<T> getAll() throws SQLException, IllegalAccessException;
    T getById(Integer id) throws SQLException, IllegalAccessException;
    T save(T t, Integer parentId) throws SQLException, IllegalAccessException;
    void delete(Integer id) throws SQLException, IllegalAccessException;
}
