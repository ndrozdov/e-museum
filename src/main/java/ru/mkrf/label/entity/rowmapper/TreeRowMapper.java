package ru.mkrf.label.entity.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.mkrf.label.entity.db.Tree;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TreeRowMapper implements RowMapper<Tree> {
    @Override
    public Tree mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tree tree = new Tree();

        tree.setId(rs.getInt("id"));
        tree.setParentId(rs.getInt("parent_id"));

        return tree;
    }
}
