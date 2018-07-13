package ru.mkrf.label.repository.title;

import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.repository.BaseRepository;

import java.sql.SQLException;
import java.util.List;

public interface TitleRepository extends BaseRepository<Title> {
    List<Title> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException;
    void deleteAllByTreeParentId(Integer treeParentId)throws SQLException, IllegalAccessException;
    void merge(List<Title> titles, Integer parentId) throws SQLException, IllegalAccessException;
}
