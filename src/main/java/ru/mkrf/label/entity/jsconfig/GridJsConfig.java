package ru.mkrf.label.entity.jsconfig;

import ru.mkrf.label.entity.db.Grid;

public class GridJsConfig {
    private Integer rows;
    private Integer cols;

    public GridJsConfig() {
    }

    public GridJsConfig(Grid grid) {
        this.rows = grid.getRows();
        this.cols = grid.getCols();
    }

    public GridJsConfig(Integer rows, Integer cols) {
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
