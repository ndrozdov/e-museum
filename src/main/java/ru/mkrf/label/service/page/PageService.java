package ru.mkrf.label.service.page;

import ru.mkrf.label.entity.db.Page;
import ru.mkrf.label.entity.web.PageWeb;
import ru.mkrf.label.entity.web.TerminalWeb;
import ru.mkrf.label.service.BaseService;

import java.sql.SQLException;
import java.util.List;

public interface PageService extends BaseService<Page> {
    PageWeb getByTreeId(Integer id) throws SQLException, IllegalAccessException;
    PageWeb save(PageWeb pageWeb, Integer parentId) throws SQLException, IllegalAccessException;
    List<PageWeb> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException;
}
