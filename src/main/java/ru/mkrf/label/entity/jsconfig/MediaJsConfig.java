package ru.mkrf.label.entity.jsconfig;

import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.entity.web.MediaWeb;

import java.nio.file.Paths;

public class MediaJsConfig {
    private String type;
    private String src;
    private String title;
    private Integer sortord;

    public MediaJsConfig() {
    }

    public MediaJsConfig(Media media) {
        this.src = Paths.get(media.getUrl(), media.getFileName()).toString();
    }

    public MediaJsConfig(MediaWeb mediaWeb) {
        this.type = mediaWeb.getType();
        this.src = mediaWeb.getPath();
        this.title = mediaWeb.getTitle() != null ? mediaWeb.getTitle().getTitle() : null;
        this.sortord = mediaWeb.getSortord();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSortord() {
        return sortord;
    }

    public void setSortord(Integer sortord) {
        this.sortord = sortord;
    }
}
