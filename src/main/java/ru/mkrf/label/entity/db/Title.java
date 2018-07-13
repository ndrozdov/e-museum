package ru.mkrf.label.entity.db;

import static ru.mkrf.label.util.PreparedStatementUtil.*;
import static ru.mkrf.label.util.PreparedStatementUtil.genDeleteQuery;

public class Title extends GlobalId {
    public static final String GET_BY_ID = "SELECT * FROM title WHERE tree_id = :treeId";
    public static final String GET_ALL = "SELECT * FROM title WHERE tree_id = :treeId";
    public static final String INSERT = genInsertQuery(Title.class);
    public static final String UPDATE = genUpdateQueryWithTreeId(Title.class);
    public static final String DELETE = genDeleteQuery(Title.class);

    private String title;

    public Title() {
    }

    public Title(Integer treeId, String title) {
        super(treeId);
        this.title = title;
    }

    public Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
