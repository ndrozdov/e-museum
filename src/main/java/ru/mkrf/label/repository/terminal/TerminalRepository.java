package ru.mkrf.label.repository.terminal;

import ru.mkrf.label.entity.db.Terminal;
import ru.mkrf.label.repository.BaseRepository;

import java.sql.SQLException;


public interface TerminalRepository extends BaseRepository<Terminal> {
    void incrementTerminalVersion(Integer treeId) throws SQLException, IllegalAccessException;
    void setTerminalVersion(Integer treeId, Integer terminalVersion) throws SQLException, IllegalAccessException;
}