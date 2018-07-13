package ru.mkrf.label.repository.treemedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.mkrf.label.entity.db.TreeMedia;
import ru.mkrf.label.entity.rowmapper.TreeMediaRowMapper;

import java.sql.SQLException;

@Repository
public class TreeMediaRepositoryImpl implements TreeMediaRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedPreparedStatement;

    @Override
    public boolean insert(TreeMedia treeMedia) throws SQLException, IllegalAccessException {
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource( treeMedia );

        return namedPreparedStatement.update(TreeMedia.INSERT, paramSource) != 0;
    }

    @Override
    public boolean update(TreeMedia treeMedia) throws SQLException, IllegalAccessException {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource( treeMedia );

        return namedPreparedStatement.update(TreeMedia.UPDATE, parameters) != 0;
    }

    @Override
    public void delete(TreeMedia treeMedia) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("mediaTreeId", treeMedia.getMediaTreeId());
        parameters.addValue("treeId", treeMedia.getTreeId());

        namedPreparedStatement.update(TreeMedia.DELETE, parameters);
    }

    @Override
    public TreeMedia getByTreeIdAndMediaTreeId(Integer treeId, Integer mediaTreeId) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("mediaTreeId", mediaTreeId);
        parameters.addValue("treeId", treeId);

        return namedPreparedStatement.queryForObject(TreeMedia.GET_BY_TREE_ID_AND_MEDIA_TREE_ID, parameters, new TreeMediaRowMapper());
    }
}
