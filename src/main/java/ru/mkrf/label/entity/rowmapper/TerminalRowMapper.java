package ru.mkrf.label.entity.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.mkrf.label.entity.db.Terminal;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.mkrf.label.util.rowmapper.RowMapperUtil.checkString;

public class TerminalRowMapper implements RowMapper<Terminal> {
    @Override
    public Terminal mapRow(ResultSet rs, int rowNum) throws SQLException {
        Terminal terminal = new Terminal();

        terminal.setTreeId(checkString(rs.getString("tree_id")));
        terminal.setTemplateId(checkString(rs.getString("template_id")));
        terminal.setTerminalVersion(checkString(rs.getString("terminal_version")));
        terminal.setVersion(checkString(rs.getString("version")));
        terminal.setLastActiveTime(checkString(rs.getString("last_active_time")));
        terminal.setDeleted(rs.getInt("deleted"));
        terminal.setSortord(rs.getInt("sortord"));

        return terminal;
    }
}
