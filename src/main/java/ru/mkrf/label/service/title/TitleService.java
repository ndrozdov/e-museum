package ru.mkrf.label.service.title;

import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.service.BaseService;

import java.sql.SQLException;
import java.util.List;

public interface TitleService extends BaseService<Title> {
    List<Title> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException;
}