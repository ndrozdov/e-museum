package ru.mkrf.label.service.tree;

import java.sql.SQLException;

public interface TreeService
{
    void changeParent(Integer parentId, Integer childId) throws SQLException, IllegalAccessException;
}