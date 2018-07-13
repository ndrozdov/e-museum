package ru.mkrf.label.repository.tree;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.mkrf.label.entity.db.Tree;

import java.sql.SQLException;
import java.util.List;

public interface TreeRepository {
    List<Tree> getAll() throws SQLException, IllegalAccessException;
    Tree getById(Integer id) throws SQLException, IllegalAccessException;
    Tree save(Tree tree) throws SQLException, IllegalAccessException;
    void changeParent(Integer parentId, Integer childId) throws SQLException, IllegalAccessException;
    void delete(Integer id) throws SQLException, IllegalAccessException;
    void delete(String sql, SqlParameterSource parameters) throws SQLException, IllegalAccessException;
    List<Tree> getAllChild(Integer parentId) throws SQLException, IllegalAccessException;
    Integer getParentIdByChildId(Integer childId) throws SQLException, IllegalAccessException;
}
