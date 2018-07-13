package ru.mkrf.label.service.section;

import org.springframework.stereotype.Service;
import ru.mkrf.label.entity.db.Page;
import ru.mkrf.label.entity.db.Section;
import ru.mkrf.label.entity.web.SectionWeb;
import ru.mkrf.label.service.BaseService;

import java.sql.SQLException;
import java.util.List;

@Service
public interface SectionService extends BaseService<Section> {
    List<SectionWeb> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException;
    SectionWeb save(SectionWeb sectionWeb, Integer treeParentId) throws SQLException, IllegalAccessException;
    SectionWeb getBySectionId(Integer sectionId) throws SQLException, IllegalAccessException;
}
