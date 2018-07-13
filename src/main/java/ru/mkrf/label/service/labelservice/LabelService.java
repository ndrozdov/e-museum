package ru.mkrf.label.service.labelservice;

import ru.mkrf.label.entity.ResponseJSConfig;
import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.entity.jsconfig.Content;

import java.sql.SQLException;
import java.util.List;

public interface LabelService {
    List<Content> getConfigContent(Integer terminalId) throws SQLException, IllegalAccessException;
    List<Media> getConfigContents(Integer terminalId) throws SQLException, IllegalAccessException;
    ResponseJSConfig getJSConfig(Integer terminalId) throws SQLException;
}
