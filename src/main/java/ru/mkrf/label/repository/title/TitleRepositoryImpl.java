package ru.mkrf.label.repository.title;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mkrf.label.entity.db.GlobalId;
import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.entity.rowmapper.TitleRowMapper;
import ru.mkrf.label.repository.AbstractBaseRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TitleRepositoryImpl extends AbstractBaseRepository<Title> implements TitleRepository {
    private static final String GET_ALL_BY_TREE_PARENT_ID = "SELECT tit.* FROM tree tr INNER JOIN title tit ON tr.id = tit.tree_id WHERE tr.parent_id = :parentId;";
    private static final String DELETE_ALL_BY_TREE_PARENT_ID = "DELETE FROM tree WHERE id IN (SELECT tit.tree_id FROM title tit INNER JOIN tree t ON t.id = tit.tree_id WHERE t.parent_id = :parentId);";

    @Override
    public List<Title> getAll() throws SQLException, IllegalAccessException {
        return super.getValuesByParameters(Title.GET_ALL, new MapSqlParameterSource(), new TitleRowMapper());
    }

    @Override
    public List<Title> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("parentId", treeParentId);

        return super.getValuesByParameters(GET_ALL_BY_TREE_PARENT_ID, parameters, new TitleRowMapper());
    }

    @Override
    public Title getById(Integer id) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("treeId", id);

        return super.getValueByParameters(Title.GET_BY_ID, parameters, new TitleRowMapper());
    }

    @Override
    @Transactional("transactionManager")
    public Title save(Title title, Integer parentId) throws SQLException, IllegalAccessException {
        if(title.isNew())
            return super.insert(Title.INSERT, title, parentId);
        else
            return super.update(Title.UPDATE, title);
    }

    @Override
    public void delete(Integer id) throws SQLException, IllegalAccessException {
        super.delete(id);
    }

    @Override
    public void deleteAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("parentId", treeParentId);

        super.delete(DELETE_ALL_BY_TREE_PARENT_ID, parameters);
    }

    @Override
    public void merge(List<Title> titles, Integer parentId) throws SQLException, IllegalAccessException {
        try {

            List<Title> _titles = titles.stream().filter(el -> el.getTreeId() != null).collect(Collectors.toList());

            Set<Integer> setIds = getAllByTreeParentId(parentId)
                    .stream()
                    .filter(el -> el.getTreeId() != null)
                    .map(GlobalId::getTreeId)
                    .collect(Collectors.toSet());

            for (Title t : _titles) {
                if (setIds.contains(t.getTreeId())) {
                    save(t, parentId);
                    setIds.remove(t.getTreeId());
                }
            }

            for (Integer id : setIds)
                super.delete(id);

            _titles = titles.stream().filter(el -> el.getTreeId() == null).collect(Collectors.toList());

            for (Title t : _titles)
                save(t, parentId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
