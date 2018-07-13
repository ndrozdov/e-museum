package ru.mkrf.label.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import ru.mkrf.label.entity.ResponseEntity;
import ru.mkrf.label.service.BaseService;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractController<T> {
    @Autowired
    private BaseService<T> service;

    public ResponseEntity<T> getById(Integer id) throws SQLException, IllegalAccessException {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK.value());
    }

    public ResponseEntity<List<T>> getAll() throws SQLException, IllegalAccessException {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK.value());
    }

    public void delete(Integer id) throws SQLException, IllegalAccessException {
        service.delete(id);
    }

    public  ResponseEntity<T> save(T t, Integer parentId) throws SQLException, IllegalAccessException {
        return new ResponseEntity<>(service.save(t, parentId), HttpStatus.OK.value());
    }
}
