package ru.mkrf.label.entity.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.mkrf.label.entity.db.Page;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PageRowMapper implements RowMapper<Page> {
    @Override
    public Page mapRow(ResultSet rs, int rowNum) throws SQLException {
        Page page = new Page();

        page.setTreeId(rs.getInt("tree_id"));
        page.setContent(rs.getString("content"));
        page.setFullscreen(rs.getInt("fullscreen"));
        page.setDeleted(rs.getInt("deleted"));
        page.setSortord(rs.getInt("sortord"));

        return page;
    }
}