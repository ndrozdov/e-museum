package ru.mkrf.label.entity.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.mkrf.label.entity.web.MediaWeb;

public class TreeMedia {
    public static final String GET_BY_TREE_ID_AND_MEDIA_TREE_ID = "SELECT * FROM media_tree WHERE media_tree_id = :mediaTreeId AND tree_id = :treeId";
    public static final String INSERT = "INSERT INTO media_tree (media_tree_id, tree_id, type, title_id, sortord) VALUES(:mediaTreeId, :treeId, :type, :titleId, :sortord)";
    public static final String UPDATE = "UPDATE media_tree SET type = :type, title_id = :titleId, sortord = :sortord WHERE media_tree_id = :mediaTreeId AND tree_id = :treeId;";
    public static final String DELETE = "DELETE FROM media_tree WHERE media_tree_id = :mediaTreeId AND tree_id = :treeId";

    private Integer mediaTreeId;
    private Integer treeId;
    private String type;
    private Integer titleId;
    private Integer sortord;

    public TreeMedia() {
    }

    public TreeMedia(Integer mediaTreeId, Integer treeId, String type, Integer titleId, Integer sortord) {
        this.mediaTreeId = mediaTreeId;
        this.treeId = treeId;
        this.type = type;
        this.titleId = titleId;
        this.sortord = sortord;
    }

    public TreeMedia(MediaWeb mediaWeb, Integer parentId) {
        this.treeId = parentId;
        this.mediaTreeId = mediaWeb.getTreeId();
        this.type = mediaWeb.getType();
        this.sortord = mediaWeb.getSortord();
        this.titleId = mediaWeb.getTitle() != null ? mediaWeb.getTitle().getTreeId() : null;
    }

    public Integer getMediaTreeId() {
        return mediaTreeId;
    }

    public void setMediaTreeId(Integer mediaTreeId) {
        this.mediaTreeId = mediaTreeId;
    }

    public Integer getTreeId() {
        return treeId;
    }

    public void setTreeId(Integer treeId) {
        this.treeId = treeId;
    }

    public Integer getTitleId() {
        return titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSortord() {
        return sortord;
    }

    public void setSortord(Integer sortord) {
        this.sortord = sortord;
    }
}
