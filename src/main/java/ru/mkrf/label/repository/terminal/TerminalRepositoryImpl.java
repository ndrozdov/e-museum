package ru.mkrf.label.repository.terminal;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mkrf.label.entity.db.Terminal;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ru.mkrf.label.entity.rowmapper.TerminalRowMapper;
import ru.mkrf.label.repository.AbstractBaseRepository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class TerminalRepositoryImpl extends AbstractBaseRepository<Terminal> implements TerminalRepository {
	@Override
	public List<Terminal> getAll() throws SQLException, IllegalAccessException {
		return super.getValuesByParameters(Terminal.GET_ALL, new MapSqlParameterSource(), new TerminalRowMapper());
	}

	@Override
	public Terminal getById(Integer id) throws SQLException, IllegalAccessException {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("treeId", id);

		return super.getValueByParameters(Terminal.GET_BY_ID, parameters, new TerminalRowMapper());
	}

	@Override
	@Transactional("transactionManager")
	public Terminal save(Terminal terminal, Integer treeParenId) throws SQLException, IllegalAccessException {
		if(terminal.isNew()) {
			return super.insert(Terminal.INSERT, terminal, treeParenId);
		} else {
			super.update(Terminal.UPDATE, terminal);

			return getById(terminal.getTreeId());
		}
	}

	@Override
	public void delete(Integer id) throws SQLException, IllegalAccessException {
		super.delete(id);
	}

	@Override
	public void incrementTerminalVersion(Integer treeId) throws SQLException, IllegalAccessException {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("treeId", treeId);

		super.update(Terminal.INCREMENT_VERSION, parameters);
	}

	@Override
	public void setTerminalVersion(Integer treeId, Integer terminalVersion) throws SQLException, IllegalAccessException {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("terminalVersion", terminalVersion);
		parameters.addValue("treeId", treeId);

		super.update(Terminal.SET_TERMINAL_VERSION, parameters);
	}
}