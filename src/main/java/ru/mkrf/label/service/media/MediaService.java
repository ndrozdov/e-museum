
package ru.mkrf.label.service.media;

import org.springframework.web.multipart.MultipartFile;
import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.entity.web.MediaWeb;
import ru.mkrf.label.service.BaseService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface MediaService extends BaseService<Media> {
	MediaWeb getMediaWebById(Integer treeId, Integer treeParentId) throws SQLException, IllegalAccessException;
	List<MediaWeb> getAllMediaWebByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException;
	void addMediaToParent(MediaWeb media, Integer parentId) throws SQLException, IllegalAccessException;
//	void deleteMediaFromParent(Integer mediaId, Integer parentId) throws SQLException, IllegalAccessException;
	List<Media> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException;
	Media uploadMedia(MultipartFile file) throws SQLException, IllegalAccessException, IOException;
	List<Media> getByIds(String ids) throws SQLException, IllegalAccessException;
}