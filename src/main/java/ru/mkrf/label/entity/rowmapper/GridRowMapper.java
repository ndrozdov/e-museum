package ru.mkrf.label.entity.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.mkrf.label.entity.db.Grid;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GridRowMapper implements RowMapper<Grid> {
    @Override
    public Grid mapRow(ResultSet rs, int rowNum) throws SQLException {
        Grid grid = new Grid();

        grid.setTreeId(Integer.parseInt(rs.getString( "tree_id" )));
        grid.setCols(rs.getInt( "cols" ));
        grid.setRows(rs.getInt( "rows" ));

        return grid;
    }
}
