package ru.mkrf.label.entity.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.mkrf.label.entity.db.Section;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.mkrf.label.util.rowmapper.RowMapperUtil.checkString;

public class SectionRowMapper implements RowMapper<Section> {
    @Override
    public Section mapRow(ResultSet rs, int rowNum) throws SQLException {
        Section section = new Section();

        section.setTreeId(checkString(rs.getString("tree_id")));
        section.setSortord(rs.getInt("sortord"));
        section.setDeleted(rs.getInt("deleted"));

        return section;
    }
}
