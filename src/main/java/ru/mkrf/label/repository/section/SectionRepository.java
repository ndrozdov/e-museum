package ru.mkrf.label.repository.section;

import ru.mkrf.label.entity.db.Section;
import ru.mkrf.label.repository.BaseRepository;

import java.sql.SQLException;
import java.util.List;

public interface SectionRepository extends BaseRepository<Section> {
    List<Section> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException;
}
