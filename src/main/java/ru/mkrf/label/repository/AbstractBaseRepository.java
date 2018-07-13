package ru.mkrf.label.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.mkrf.label.entity.db.GlobalId;
import ru.mkrf.label.entity.db.Tree;
import ru.mkrf.label.repository.tree.TreeRepository;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractBaseRepository<T extends GlobalId> {
    @Autowired
    private NamedParameterJdbcTemplate namedPreparedStatement;

    @Autowired
    private TreeRepository treeRepository;

    public T getValueByParameters(String sql, SqlParameterSource parameters, RowMapper<T> rowMapper) throws SQLException, IllegalAccessException {
        try {
            return namedPreparedStatement.queryForObject(sql, parameters, rowMapper);
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        }
    }

    public List<T> getValuesByParameters(String sql, SqlParameterSource parameters, RowMapper<T> rowMapper) throws SQLException, IllegalAccessException {
        return namedPreparedStatement.query(sql, parameters, rowMapper);
    }

    public T insert(String sql, T t, Integer parentId) throws SQLException, IllegalAccessException {
        Tree tree = treeRepository.save(new Tree(parentId));

        t.setTreeId(tree.getId());
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource( t );

        return namedPreparedStatement.update(sql, paramSource) != 0 ? t : null;
    }

    public T update(String sql, T t) throws SQLException, IllegalAccessException {
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource( t );

        return namedPreparedStatement.update(sql, paramSource) != 0 ? t : null;
    }

    public void update(String sql, SqlParameterSource parameters) throws SQLException, IllegalAccessException {
        namedPreparedStatement.update(sql, parameters);
    }

    public void delete(Integer id) throws SQLException, IllegalAccessException {
        treeRepository.delete(id);
    }

    public void delete(String sql, SqlParameterSource parameters) throws SQLException, IllegalAccessException {
        treeRepository.delete(sql, parameters);
    }
}