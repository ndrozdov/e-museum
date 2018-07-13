package ru.mkrf.label.entity.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.mkrf.label.entity.db.Media;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.mkrf.label.util.rowmapper.RowMapperUtil.checkString;

public class MediaRowMapper implements RowMapper<Media> {
    @Override
    public Media mapRow(ResultSet rs, int rowNum) throws SQLException {
        Media media = new Media();

        media.setTreeId(checkString(rs.getString("tree_id")));
        media.setOriginalFileName(rs.getString("original_file_name"));
        media.setFileName(rs.getString("file_name"));
        media.setUploadDate(rs.getString("upload_date"));
        media.setUrl(rs.getString("url"));
        media.setFileSize(rs.getInt("file_size"));
        media.setMimeType(rs.getString("mime_type"));
        media.setDeleted(checkString(rs.getString("deleted")));

        return media;
    }
}
