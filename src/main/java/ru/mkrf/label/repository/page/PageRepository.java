package ru.mkrf.label.repository.page;


import ru.mkrf.label.entity.db.Page;
import ru.mkrf.label.repository.BaseRepository;

import java.sql.SQLException;
import java.util.List;

public interface PageRepository extends BaseRepository<Page> {
    List<Page> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException;
}
