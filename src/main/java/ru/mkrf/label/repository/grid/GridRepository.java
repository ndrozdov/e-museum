package ru.mkrf.label.repository.grid;

import ru.mkrf.label.entity.db.Grid;
import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.repository.BaseRepository;

import java.sql.SQLException;
import java.util.List;

public interface GridRepository extends BaseRepository<Grid> {
    List<Grid> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException;
    void deleteAllByTreeParentId(Integer treeParentId)throws SQLException, IllegalAccessException;
    void merge(List<Grid> grids, Integer parentId) throws SQLException, IllegalAccessException;
}
