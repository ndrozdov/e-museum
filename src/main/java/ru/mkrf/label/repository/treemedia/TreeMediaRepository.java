package ru.mkrf.label.repository.treemedia;

import ru.mkrf.label.entity.db.Tree;
import ru.mkrf.label.entity.db.TreeMedia;

import java.sql.SQLException;

public interface TreeMediaRepository {
    boolean insert(TreeMedia treeMedia) throws SQLException, IllegalAccessException;
    boolean update(TreeMedia treeMedia) throws SQLException, IllegalAccessException;
    void delete(TreeMedia treeMedia) throws SQLException, IllegalAccessException;
    TreeMedia getByTreeIdAndMediaTreeId(Integer mediaTreeId, Integer treeId) throws SQLException, IllegalAccessException;
}
