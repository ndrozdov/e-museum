package ru.mkrf.label.repository.tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.mkrf.label.entity.db.Tree;
import ru.mkrf.label.entity.rowmapper.TreeRowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class TreeRepositoryImpl implements TreeRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedPreparedStatement;

    @Override
    public List<Tree> getAll() throws SQLException, IllegalAccessException {
        return getValuesByParameters(Tree.GET_ALL, new MapSqlParameterSource(), new TreeRowMapper());
    }

    @Override
    public Tree getById(Integer id) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("rowid", id);

        return getValueByParameters(Tree.GET_BY_ID, parameters, new TreeRowMapper());
    }

    @Override
    public Tree save(Tree tree) throws SQLException, IllegalAccessException {
        return tree.isNew() ? insert(Tree.INSERT, tree) : update(Tree.UPDATE, tree);
    }

    private Tree getValueByParameters(String sql, SqlParameterSource parameters, RowMapper<Tree> rowMapper) throws SQLException, IllegalAccessException {
        try {
            return namedPreparedStatement.queryForObject(sql, parameters, rowMapper);
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        }
    }

    public List<Tree> getValuesByParameters(String sql, SqlParameterSource parameters, RowMapper<Tree> rowMapper) throws SQLException, IllegalAccessException {
        return namedPreparedStatement.query(sql, parameters, rowMapper);
    }

    private Tree insert(String sql, Tree t) throws SQLException, IllegalAccessException {
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource( t );

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] columnNames = new String[] { "rowid" };

        namedPreparedStatement.update(sql, paramSource, keyHolder, columnNames);
        Map<String, Object> keys = keyHolder.getKeys();
        Integer rowid = ((Integer)keys.get("last_insert_rowid()"));
        t.setId(rowid);

        return t;
    }

    private Tree update(String sql, Tree t) throws SQLException, IllegalAccessException {
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource( t );

        return namedPreparedStatement.update(sql, paramSource) != 0 ? t : null;
    }

    @Override
    public void delete(Integer id) throws SQLException, IllegalAccessException {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);

        namedPreparedStatement.update(Tree.DELETE, namedParameters);
    }


    @Override
    public void delete(String sql, SqlParameterSource parameters) throws SQLException, IllegalAccessException {
        namedPreparedStatement.update(sql, parameters);
    }

    @Override
    public List<Tree> getAllChild(Integer parentId) throws SQLException, IllegalAccessException {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("parent_id", parentId);

        return namedPreparedStatement.query(Tree.GET_ALL_CHILD_ID, namedParameters, new TreeRowMapper());
    }

    @Override
    public void changeParent(Integer parentId, Integer childId) throws SQLException, IllegalAccessException {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("parentId", parentId);
        namedParameters.addValue("childId", childId);

        namedPreparedStatement.update(Tree.CHANGE_PARENT, namedParameters);
    }

    @Override
    public Integer getParentIdByChildId(Integer childId) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("childId", childId);

        try {
            return namedPreparedStatement.queryForObject(Tree.GET_TERMINAL_ID_BY_CHILD_ID, parameters, Integer.class);
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        }
    }
}
