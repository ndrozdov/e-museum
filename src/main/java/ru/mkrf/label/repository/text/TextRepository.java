package ru.mkrf.label.repository.text;

import ru.mkrf.label.entity.db.Text;
import ru.mkrf.label.repository.BaseRepository;

import java.sql.SQLException;
import java.util.List;

public interface TextRepository extends BaseRepository<Text> {
    List<Text> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException;
    void merge(List<Text> texts, Integer parentId) throws SQLException, IllegalAccessException;
}
