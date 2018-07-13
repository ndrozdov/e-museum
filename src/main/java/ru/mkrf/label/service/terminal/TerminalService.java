
package ru.mkrf.label.service.terminal;

import ru.mkrf.label.entity.db.Terminal;
import ru.mkrf.label.entity.web.TerminalWeb;
import ru.mkrf.label.service.BaseService;

import java.sql.SQLException;
import java.util.List;

public interface TerminalService extends BaseService<Terminal> {
    TerminalWeb save(TerminalWeb terminalWeb) throws SQLException, IllegalAccessException;
    TerminalWeb getByTerminalId(Integer id) throws SQLException, IllegalAccessException;
    List<TerminalWeb> getAllTerminalWeb() throws SQLException, IllegalAccessException;
    void setTerminalVersion(Integer treeId, Integer terminalVersion) throws SQLException, IllegalAccessException;
}