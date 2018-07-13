package ru.mkrf.label.entity.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.mkrf.label.entity.db.Template;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.mkrf.label.util.rowmapper.RowMapperUtil.checkString;

public class TemplateRowMapper implements RowMapper<Template> {
    @Override
    public Template mapRow(ResultSet rs, int rowNum) throws SQLException {
        Template template = new Template();

        template.setTreeId(checkString(rs.getString("tree_id")));
        template.setDeleted(rs.getInt("deleted"));
        template.setSortord(rs.getInt("sortord"));

        return template;
    }
}