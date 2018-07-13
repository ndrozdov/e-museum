package ru.mkrf.label.entity.db;

import static ru.mkrf.label.util.PreparedStatementUtil.*;
import static ru.mkrf.label.util.PreparedStatementUtil.genDeleteQuery;

public class Section extends GlobalId {
    public static final String GET_BY_ID = "SELECT * FROM section WHERE tree_id = :treeId";
    public static final String GET_ALL = "SELECT * FROM section";
    public static final String INSERT = genInsertQuery(Section.class);
    public static final String UPDATE = genUpdateQueryWithTreeId(Section.class);
    public static final String DELETE = genDeleteQuery(Section.class);

    private Integer deleted;
    private Integer sortord;

    public Section() {
    }

    public Section(Integer treeId, Integer deleted, Integer sortord) {
        super(treeId);
        this.deleted = deleted;
        this.sortord = sortord;
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