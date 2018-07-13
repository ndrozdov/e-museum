package ru.mkrf.label.repository;

import java.sql.SQLException;
import java.util.List;

public interface BaseRepository<T> {
    List<T> getAll() throws SQLException, IllegalAccessException;
    T getById(Integer id) throws SQLException, IllegalAccessException;
    T save(T t, Integer treeParentId) throws SQLException, IllegalAccessException;
    void delete(Integer id) throws SQLException, IllegalAccessException;
}