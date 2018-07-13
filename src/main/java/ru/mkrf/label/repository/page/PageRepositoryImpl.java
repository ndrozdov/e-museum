package ru.mkrf.label.repository.page;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mkrf.label.entity.db.Page;
import ru.mkrf.label.entity.rowmapper.PageRowMapper;
import ru.mkrf.label.repository.AbstractBaseRepository;
import ru.mkrf.label.repository.terminal.TerminalRepository;
import ru.mkrf.label.repository.tree.TreeRepository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class PageRepositoryImpl extends AbstractBaseRepository<Page> implements PageRepository {
    private static final String GET_ALL_BY_TREE_PARENT_ID = "SELECT p.* FROM tree t INNER JOIN page p ON t.id = p.tree_id WHERE t.parent_id = :parentId";

    @Override
    public List<Page> getAll() throws SQLException, IllegalAccessException {
        return null;
    }

    @Override
    public Page getById(Integer id) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("treeId", id);

        return super.getValueByParameters(Page.GET_BY_ID, parameters, new PageRowMapper());
    }

    @Override
    public List<Page> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("parentId", treeParentId);

        return super.getValuesByParameters(GET_ALL_BY_TREE_PARENT_ID, parameters, new PageRowMapper());
    }

    @Override
    @Transactional("transactionManager")
    public Page save(Page page, Integer treeParentId) throws SQLException, IllegalAccessException {
        if(page.isNew()) {
            return super.insert(Page.INSERT, page, treeParentId);
        } else {
            return super.update(Page.UPDATE, page);
        }
    }

    @Override
    public void delete(Integer id) throws SQLException, IllegalAccessException {
        super.delete(id);
    }
}
