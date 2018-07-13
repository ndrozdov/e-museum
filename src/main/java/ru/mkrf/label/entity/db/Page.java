package ru.mkrf.label.entity.db;

import static ru.mkrf.label.util.PreparedStatementUtil.*;
import static ru.mkrf.label.util.PreparedStatementUtil.genDeleteQuery;

public class Page extends GlobalId {
    public static final String GET_BY_ID = "SELECT * FROM page WHERE tree_id = :treeId";
    public static final String GET_ALL = genSelectQuery(Page.class);
    public static final String INSERT = genInsertQuery(Page.class);
    public static final String UPDATE = genUpdateQueryWithTreeId(Page.class);
    public static final String DELETE = genDeleteQuery(Page.class);

    private String content;
    private Integer fullscreen;
    private Integer deleted;
    private Integer sortord;

    public Page() {
    }

    public Page(Integer treeId, String content, Integer fullscreen, Integer deleted, Integer sortord) {
        super(treeId);
        this.content = content;
        this.fullscreen = fullscreen;
        this.deleted = deleted;
        this.sortord = sortord;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(Integer fullscreen) {
        this.fullscreen = fullscreen;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getSortord() {
        return sortord;
    }

    public void setSortord(Integer sortord) {
        this.sortord = sortord;
    }
}
