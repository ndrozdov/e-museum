package ru.mkrf.label.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResponseJSConfig implements RowMapper<ResponseJSConfig> {
    private String bigMedia;
    private String smallMedia;
    private List<ExhibitConfig> contents;

    public ResponseJSConfig() {
    }

    public ResponseJSConfig(String bigMedia, String smallMedia, List<ExhibitConfig> contents) {
        this.bigMedia = bigMedia;
        this.smallMedia = smallMedia;
        this.contents = contents;
    }

    public String getBigMedia() {
        return bigMedia;
    }

    public void setBigMedia(String bigMedia) {
        this.bigMedia = bigMedia;
    }

    public String getSmallMedia() {
        return smallMedia;
    }

    public void setSmallMedia(String smallMedia) {
        this.smallMedia = smallMedia;
    }

    public List<ExhibitConfig> getContents() {
        return contents;
    }

    public void setContents(List<ExhibitConfig> contents) {
        this.contents = contents;
    }

    @Override
    public ResponseJSConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
