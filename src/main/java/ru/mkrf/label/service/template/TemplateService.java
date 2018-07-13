package ru.mkrf.label.service.template;

import ru.mkrf.label.entity.db.Template;
import ru.mkrf.label.entity.web.TemplateWeb;
import ru.mkrf.label.service.BaseService;

import java.sql.SQLException;
import java.util.List;

public interface TemplateService extends BaseService<Template> {
    TemplateWeb getByTreeId(Integer id) throws SQLException, IllegalAccessException;
    List<TemplateWeb> getAllTemplateWeb() throws SQLException, IllegalAccessException;
    TemplateWeb save(TemplateWeb templateWeb) throws SQLException, IllegalAccessException;
}
