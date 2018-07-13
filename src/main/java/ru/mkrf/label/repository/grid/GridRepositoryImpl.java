package ru.mkrf.label.repository.grid;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mkrf.label.entity.db.GlobalId;
import ru.mkrf.label.entity.db.Grid;
import ru.mkrf.label.entity.rowmapper.GridRowMapper;
import ru.mkrf.label.repository.AbstractBaseRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class GridRepositoryImpl extends AbstractBaseRepository<Grid> implements GridRepository {
    private static final String GET_ALL_BY_BY_TREE_PARENT_ID = "SELECT g.* FROM tree t INNER JOIN grid g ON t.id = g.tree_id WHERE t.parent_id = :parentId";
    private static final String DELETE_ALL_BY_TREE_PARENT_ID = "DELETE FROM tree WHERE id IN (SELECT g.tree_id FROM grid g INNER JOIN tree t ON t.id = g.tree_id WHERE t.parent_id = :parentId);";

    @Override
    public List<Grid> getAll() throws SQLException, IllegalAccessException {
        return super.getValuesByParameters(Grid.GET_ALL, new MapSqlParameterSource(), new GridRowMapper());
    }

    @Override
    public Grid getById(Integer id) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("treeId", id);

        return super.getValueByParameters(Grid.GET_BY_ID, parameters, new GridRowMapper());
    }

    @Override
    @Transactional("transactionManager")
    public Grid save(Grid grid, Integer treeParentId) throws SQLException, IllegalAccessException {
        if(grid.isNew())
            return super.insert(Grid.INSERT, grid, treeParentId);
        else
            return super.update(Grid.UPDATE, grid);
    }

    @Override
    public List<Grid> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("parentId", treeParentId);

        return super.getValuesByParameters(GET_ALL_BY_BY_TREE_PARENT_ID, parameters, new GridRowMapper());
    }

    @Override
    public void deleteAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("parentId", treeParentId);

        super.delete(DELETE_ALL_BY_TREE_PARENT_ID, parameters);
    }

    @Override
    public void merge(List<Grid> grids, Integer parentId) throws SQLException, IllegalAccessException {
        List<Grid> _grids = grids.stream().filter(el -> el.getTreeId() != null).collect(Collectors.toList());

        Set<Integer> setIds = getAllByTreeParentId(parentId)
                .stream()
                .filter(el -> el.getTreeId() != null)
                .map(GlobalId::getTreeId)
                .collect(Collectors.toSet());

        for (Grid t : _grids) {
            if ( setIds.contains(t.getTreeId()) ) {
                save(t, parentId);
                setIds.remove(t.getTreeId());
            }
        }

        for(Integer id : setIds)
            super.delete(id);

        _grids = grids.stream().filter(el -> el.getTreeId() == null).collect(Collectors.toList());

        for(Grid t: _grids)
            save(t, parentId);
    }
}
