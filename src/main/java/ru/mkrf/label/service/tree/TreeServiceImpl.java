package ru.mkrf.label.service.tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mkrf.label.repository.tree.TreeRepository;

import java.sql.SQLException;

@Service
public class TreeServiceImpl implements TreeService {
    @Autowired
    private TreeRepository repository;

    @Override
    public void changeParent(Integer parentId, Integer childId) throws SQLException, IllegalAccessException {
        repository.changeParent(parentId, childId);
    }
}
