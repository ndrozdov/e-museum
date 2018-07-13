package ru.mkrf.label.repository.labelservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.mkrf.label.entity.ExhibitConfig;
import ru.mkrf.label.entity.ResponseJSConfig;
import ru.mkrf.label.entity.db.Tree;
import ru.mkrf.label.entity.jsconfig.Content;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class LabelServiceRepositoryImpl implements LabelServiceRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedPreparedStatement;

    @Override
    public List<ExhibitConfig> getConfigContents(Integer terminalId) throws SQLException {
//        Map namedParameters = new HashMap<>();
//        namedParameters.put("terminal_id", terminalId);

        return null;
    }

    @Override
    public ResponseJSConfig getJSConfig(Integer terminalId) throws SQLException {
        ResponseJSConfig result;
        Map<String, Integer> namedParameters = new HashMap<>();
        namedParameters.put("terminal_id", terminalId);

        List<ResponseJSConfig> responseJSConfigs = namedPreparedStatement.query(
                "SELECT\n " +
                        "  'content/media/' || m.file_name AS bigMedia " +
                        "  , 'content/media/' || m.file_name AS smallMedia " +
                        "FROM " +
                        "  terminal t" +
                        "    LEFT JOIN media m ON t.media_id = m.ROWID " +
                        "WHERE\n " +
                        "  t.ROWID = :terminal_id;"
                , namedParameters
                , (rs, rowNum) -> new ResponseJSConfig(rs.getString(1), rs.getString(2), null)
        );

        if(!CollectionUtils.isEmpty(responseJSConfigs)) {
            result = responseJSConfigs.get(0);
            result.setContents(getConfigContents(terminalId));
            return result;
        }

        return null;
    }
}