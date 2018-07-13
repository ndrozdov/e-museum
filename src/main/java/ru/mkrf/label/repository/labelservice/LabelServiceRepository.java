package ru.mkrf.label.repository.labelservice;

import ru.mkrf.label.entity.ExhibitConfig;
import ru.mkrf.label.entity.ResponseJSConfig;
import ru.mkrf.label.entity.jsconfig.Content;

import java.sql.SQLException;
import java.util.List;

public interface LabelServiceRepository {
    List<ExhibitConfig> getConfigContents(Integer terminalId) throws SQLException;
    ResponseJSConfig getJSConfig(Integer terminalId) throws SQLException;
}
