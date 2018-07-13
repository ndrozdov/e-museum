package ru.mkrf.label.entity.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.mkrf.label.entity.db.TreeMedia;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.mkrf.label.util.rowmapper.RowMapperUtil.checkString;

public class TreeMediaRowMapper implements RowMapper<TreeMedia> {
    @Override
    public TreeMedia mapRow(ResultSet rs, int rowNum) throws SQLException {
        TreeMedia treeMedia = new TreeMedia();

        treeMedia.setTreeId(checkString(rs.getString("tree_id")));
        treeMedia.setMediaTreeId(checkString(rs.getString("media_tree_id")));
        treeMedia.setTitleId(checkString(rs.getString("title_id")));
        treeMedia.setType(rs.getString("type"));
        treeMedia.setSortord(rs.getInt("sortord"));

        return treeMedia;
    }


}
