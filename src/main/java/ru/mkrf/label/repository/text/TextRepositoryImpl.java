package ru.mkrf.label.repository.text;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mkrf.label.entity.db.GlobalId;
import ru.mkrf.label.entity.db.Text;
import ru.mkrf.label.entity.rowmapper.TextRowMapper;
import ru.mkrf.label.repository.AbstractBaseRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TextRepositoryImpl extends AbstractBaseRepository<Text> implements TextRepository {
    private static final String GET_ALL_BY_TREE_PARENT_ID = "SELECT txt.* FROM tree tr INNER JOIN text txt ON tr.id = txt.tree_id WHERE tr.parent_id = :parentId;";

    @Override
    public List<Text> getAll() throws SQLException, IllegalAccessException {
        return super.getValuesByParameters(Text.GET_ALL, new MapSqlParameterSource(), new TextRowMapper());
    }

    @Override
    public List<Text> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("parentId", treeParentId);

        return super.getValuesByParameters(GET_ALL_BY_TREE_PARENT_ID, parameters, new TextRowMapper());
    }

    @Override
    public Text getById(Integer id) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("treeId", id);

        return super.getValueByParameters(Text.GET_BY_ID, parameters, new TextRowMapper());
    }

    @Override
    @Transactional("transactionManager")
    public Text save(Text title, Integer parentId) throws SQLException, IllegalAccessException {
        if(title.isNew()) {
            return super.insert(Text.INSERT, title, parentId);
        } else {
            return super.update(Text.UPDATE, title);
        }
    }

    @Override
    public void delete(Integer id) throws SQLException, IllegalAccessException {
        super.delete(id);
    }

    @Override
    public void merge(List<Text> texts, Integer parentId) throws SQLException, IllegalAccessException {
        List<Text> _texts = texts.stream().filter(el -> el.getTreeId() != null).collect(Collectors.toList());

        Set<Integer> setIds = getAllByTreeParentId(parentId)
                .stream()
                .filter(el -> el.getTreeId() != null)
                .map(GlobalId::getTreeId)
                .collect(Collectors.toSet());

        for (Text t : _texts) {
            if ( setIds.contains(t.getTreeId()) ) {
                save(t, parentId);
                setIds.remove(t.getTreeId());
            }
        }

        for(Integer id : setIds)
            super.delete(id);

        _texts = texts.stream().filter(el -> el.getTreeId() == null).collect(Collectors.toList());

        for(Text t: _texts)
            save(t, parentId);
    }
}
