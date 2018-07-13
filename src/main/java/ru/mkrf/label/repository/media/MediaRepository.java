package ru.mkrf.label.repository.media;

import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.entity.web.MediaWeb;
import ru.mkrf.label.repository.BaseRepository;

import java.sql.SQLException;
import java.util.List;

public interface MediaRepository extends BaseRepository<Media> {
    List<Media> getByIds(String ids) throws SQLException, IllegalAccessException;
    List<Media> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException;
    void mergeMediaList(List<MediaWeb> medias, Integer parentId) throws SQLException, IllegalAccessException;
}
