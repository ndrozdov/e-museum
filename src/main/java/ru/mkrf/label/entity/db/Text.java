package ru.mkrf.label.entity.db;

import static ru.mkrf.label.util.PreparedStatementUtil.*;
import static ru.mkrf.label.util.PreparedStatementUtil.genDeleteQuery;

public class Text extends GlobalId {
    public static final String GET_BY_ID = "SELECT * FROM text WHERE tree_id = :treeId";
    public static final String GET_ALL = genSelectQuery(Text.class);
    public static final String INSERT = genInsertQuery(Text.class);
    public static final String UPDATE = genUpdateQueryWithTreeId(Text.class);
    public static final String DELETE = genDeleteQuery(Text.class);

    private String text;

    public Text() {
    }

    public Text(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
