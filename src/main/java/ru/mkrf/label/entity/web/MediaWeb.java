package ru.mkrf.label.entity.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.entity.db.Title;

import java.nio.file.Paths;

public class MediaWeb {
    private Integer treeId;
    private String type;
    private Title title;
    private String path;
    private Integer sortord;

    public MediaWeb() {
    }

    public MediaWeb(Media media, String type, Title title, Integer sortord) {
        this.treeId = media.getTreeId();
        this.type = type;
        this.title = title;
        this.sortord = sortord;
        this.path = Paths.get(media.getUrl(), media.getFileName()).toString();
    }

    @JsonProperty("id")
    public Integer getTreeId() {
        return treeId;
    }

    public void setTreeId(Integer treeId) {
        this.treeId = treeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Integer getSortord() {
        return sortord;
    }

    public void setSortord(Integer sortord) {
        this.sortord = sortord;
    }

    @JsonIgnore
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaWeb mediaWeb = (MediaWeb) o;

        return treeId != null ? treeId.equals(mediaWeb.treeId) : mediaWeb.treeId == null;

    }
}
