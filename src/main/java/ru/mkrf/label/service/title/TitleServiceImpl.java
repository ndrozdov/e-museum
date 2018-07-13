package ru.mkrf.label.service.title;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.repository.title.TitleRepository;
import ru.mkrf.label.service.AbstractBaseService;
import ru.mkrf.label.service.BaseService;

import java.sql.SQLException;
import java.util.List;

@Service
public class TitleServiceImpl extends AbstractBaseService<Title> implements TitleService {
    @Autowired
    private TitleRepository repository;

    @Override
    public List<Title> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
        return repository.getAllByTreeParentId(treeParentId);
    }
}
