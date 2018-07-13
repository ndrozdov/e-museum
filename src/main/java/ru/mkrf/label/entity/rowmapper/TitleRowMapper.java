package ru.mkrf.label.entity.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.mkrf.label.entity.db.Title;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.mkrf.label.util.rowmapper.RowMapperUtil.checkString;

public class TitleRowMapper implements RowMapper<Title> {
    @Override
    public Title mapRow(ResultSet rs, int rowNum) throws SQLException {
        Title title = new Title();

        title.setTreeId(checkString(rs.getString("tree_id")));
        title.setTitle(rs.getString("title"));

        return title;
    }
}
