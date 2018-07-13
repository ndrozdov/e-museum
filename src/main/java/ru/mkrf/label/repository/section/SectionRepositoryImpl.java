package ru.mkrf.label.repository.section;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mkrf.label.entity.db.Section;
import ru.mkrf.label.entity.rowmapper.SectionRowMapper;
import ru.mkrf.label.repository.AbstractBaseRepository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class SectionRepositoryImpl extends AbstractBaseRepository<Section> implements SectionRepository {
    private static final String GET_ALL_BY_TREE_PARENT_ID = "SELECT s.* FROM tree t INNER JOIN section s ON t.id = s.tree_id WHERE t.parent_id = :parentId";

    @Override
    public List<Section> getAll() throws SQLException, IllegalAccessException {
        return null;
    }

    @Override
    public List<Section> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("parentId", treeParentId);

        return super.getValuesByParameters(GET_ALL_BY_TREE_PARENT_ID, parameters, new SectionRowMapper());
    }

    @Override
    public Section getById(Integer id) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("treeId", id);

        return super.getValueByParameters(Section.GET_BY_ID, parameters, new SectionRowMapper());
    }

    @Override
    @Transactional("transactionManager")
    public Section save(Section section, Integer treeParentId) throws SQLException, IllegalAccessException {
        if(section.isNew()) {
            return super.insert(Section.INSERT, section, treeParentId);
        } else {
            return super.update(Section.UPDATE, section);
        }
    }
}
