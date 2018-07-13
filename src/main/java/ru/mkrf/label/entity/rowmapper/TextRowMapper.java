package ru.mkrf.label.entity.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.mkrf.label.entity.db.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.mkrf.label.util.rowmapper.RowMapperUtil.checkString;

public class TextRowMapper implements RowMapper<Text> {
    @Override
    public Text mapRow(ResultSet rs, int rowNum) throws SQLException {
        Text text = new Text();

        text.setTreeId(checkString(rs.getString("tree_id")));
        text.setText(rs.getString("text"));

        return text;
    }
}
