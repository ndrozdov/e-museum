package ru.mkrf.label.repository.media;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mkrf.label.entity.db.GlobalId;
import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.entity.db.TreeMedia;
import ru.mkrf.label.entity.rowmapper.MediaRowMapper;
import ru.mkrf.label.entity.web.MediaWeb;
import ru.mkrf.label.repository.AbstractBaseRepository;
import ru.mkrf.label.repository.title.TitleRepository;
import ru.mkrf.label.repository.treemedia.TreeMediaRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class MediaRepositoryImpl extends AbstractBaseRepository<Media> implements MediaRepository {
    @Autowired
    private TreeMediaRepository treeMediaRepository;

    @Autowired
    private TitleRepository titleRepository;

    private static final String GET_ALL_BY_TREE_MEDIA_TREE_ID = "" +
            "SELECT m.* FROM media m INNER JOIN media_tree tm ON tm.media_tree_id = m.tree_id WHERE tm.tree_id = :parentTreeId";

    @Override
    public List<Media> getAll() throws SQLException, IllegalAccessException {
        return super.getValuesByParameters(Media.GET_ALL, new MapSqlParameterSource(), new MediaRowMapper());
    }

    @Override
    public List<Media> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("parentTreeId", treeParentId);

        return super.getValuesByParameters(GET_ALL_BY_TREE_MEDIA_TREE_ID, parameters, new MediaRowMapper());
    }

    @Override
    public Media getById(Integer id) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("treeId", id);

        return super.getValueByParameters(Media.GET_BY_ID, parameters, new MediaRowMapper());
    }

    @Override
    @Transactional("transactionManager")
    public Media save(Media media, Integer treeParentId) throws SQLException, IllegalAccessException {
        if(media.isNew()) {
            return super.insert(Media.INSERT, media, null);
        } else {
            return super.update(Media.UPDATE, media);
        }
    }

    @Override
    @Transactional("transactionManager")
    public void mergeMediaList(List<MediaWeb> medias, Integer parentId) throws SQLException, IllegalAccessException {
        List<MediaWeb> _medias = medias != null ?
                medias.stream().filter(el -> el.getTreeId() != null).collect(Collectors.toList())
                : new ArrayList<>();

        Set<Integer> setIds = getAllByTreeParentId(parentId)
                .stream()
                .map(GlobalId::getTreeId)
                .collect(Collectors.toSet());

        for (MediaWeb m : _medias) {
            if ( setIds.contains(m.getTreeId()) ) {
                titleRepository.save(m.getTitle(), null);
                treeMediaRepository.update(
                        new TreeMedia(
                                m,
                                parentId
                        )
                );
                setIds.remove(m.getTreeId());
            } else {
                titleRepository.save(m.getTitle(), null);
                treeMediaRepository.insert(
                        new TreeMedia(
                                m,
                                parentId
                        )
                );
            }
        }

        for(Integer id : setIds)
            treeMediaRepository.delete(new TreeMedia(id, parentId, null, null, null));
    }

    @Override
    public List<Media> getByIds(String ids) throws SQLException, IllegalAccessException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList()));

        return getValuesByParameters(Media.GET_BY_IDS, parameters, new MediaRowMapper());
    }
}
