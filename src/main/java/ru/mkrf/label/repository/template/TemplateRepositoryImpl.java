package ru.mkrf.label.repository.template;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mkrf.label.entity.db.Template;
import ru.mkrf.label.entity.rowmapper.TemplateRowMapper;
import ru.mkrf.label.repository.AbstractBaseRepository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class TemplateRepositoryImpl extends AbstractBaseRepository<Template> implements TemplateRepository {
    @Override
    public List<Template> getAll() throws SQLException, IllegalAccessException {
        return super.getValuesByParameters(Template.GET_ALL, new MapSqlParameterSource(), new TemplateRowMapper());
    }

    @Override
    public Template getById(Integer id) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("treeId", id);

        return super.getValueByParameters(Template.GET_BY_ID, parameters, new TemplateRowMapper());
    }

    @Override
    @Transactional("transactionManager")
    public Template save(Template template, Integer treeParentId) throws SQLException, IllegalAccessException {
        if(template.isNew()) {
            return super.insert(Template.INSERT, template, treeParentId);
        } else {
            return super.update(Template.UPDATE, template);
        }
    }
}
