package ru.mkrf.label.entity.db;

import static ru.mkrf.label.util.PreparedStatementUtil.*;
import static ru.mkrf.label.util.PreparedStatementUtil.genDeleteQuery;

public class Grid extends GlobalId {
    public static final String GET_BY_ID = "SELECT * FROM grid WHERE tree_id = :treeId";
    public static final String GET_ALL = genSelectQuery(Grid.class);
    public static final String INSERT = genInsertQuery(Grid.class);
    public static final String UPDATE = genUpdateQueryWithTreeId(Grid.class);
    public static final String DELETE = genDeleteQuery(Grid.class);

    private Integer rows;
    private Integer cols;

    public Grid() {
    }

    public Grid(Integer rows, Integer cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }
}
