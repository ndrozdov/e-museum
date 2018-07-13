package ru.mkrf.label.service.media;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.mkrf.label.entity.db.GlobalId;
import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.entity.db.TreeMedia;
import ru.mkrf.label.entity.web.MediaWeb;
import ru.mkrf.label.repository.media.MediaRepository;
import ru.mkrf.label.repository.title.TitleRepository;
import ru.mkrf.label.repository.treemedia.TreeMediaRepository;
import ru.mkrf.label.service.AbstractBaseService;
import ru.mkrf.label.util.configuration.LabelConfig;
import ru.mkrf.label.util.to.MultipartFileZipToMedias;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.sql.SQLException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MediaServiceImpl extends AbstractBaseService<Media> implements MediaService {
	@Autowired
	private MediaRepository mediaRepository;

	@Autowired
	private TreeMediaRepository treeMediaRepository;

	@Autowired
	private TitleRepository titleRepository;

	@Autowired
	private LabelConfig config;

	@Override
	public MediaWeb getMediaWebById(Integer treeId, Integer treeParentId) throws SQLException, IllegalAccessException {
		Media media = mediaRepository.getById(treeId);
		TreeMedia treeMedia = treeMediaRepository.getByTreeIdAndMediaTreeId(treeParentId, treeId);
		Title title = titleRepository.getById(treeMedia.getTitleId());

		return new MediaWeb(media, treeMedia.getType(), title, treeMedia.getSortord());
	}

	@Override
	public List<MediaWeb> getAllMediaWebByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
		List<MediaWeb> medias = new ArrayList<>();
		List<Integer> ids = mediaRepository.getAllByTreeParentId(treeParentId).stream().map(GlobalId::getTreeId).collect(Collectors.toList());

		for(Integer id: ids)
			medias.add(getMediaWebById(id, treeParentId));

		return medias.stream().sorted(new Comparator<MediaWeb>() {
			@Override
			public int compare(MediaWeb o1, MediaWeb o2) {
				return o1.getSortord() - o2.getSortord();
			}
		}).collect(Collectors.toList());
	}

	@Override
	public void addMediaToParent(MediaWeb media, Integer parentId) throws SQLException, IllegalAccessException {
		treeMediaRepository.insert(new TreeMedia(media, parentId));
	}
//
//	@Override
//	public void deleteMediaFromParent(Integer mediaId, Integer parentId) throws SQLException, IllegalAccessException {
//		treeMediaRepository.delete(new TreeMedia(mediaId, parentId));
//	}


	@Override
	public List<Media> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
		return mediaRepository.getAllByTreeParentId(treeParentId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager")
	public Media uploadMedia(MultipartFile file) throws SQLException, IllegalAccessException, IOException {
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		String time = "" + LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		Path path = Paths.get(config.getPathToMedia(), fileName);

		Media media = new Media();
		media.setOriginalFileName(file.getOriginalFilename());
		media.setFileName(fileName);
		media.setFileSize((int)file.getSize());
		media.setUploadDate(time);
		media.setUrl("content/media");
		media.setMimeType(file.getContentType());
		media.setDeleted(0);

		byte[] bytes = file.getBytes();
		BufferedOutputStream stream =
				new BufferedOutputStream(new FileOutputStream(path.toFile()));
		stream.write(bytes);
		stream.close();

		super.save(media, null);

		if(file.getContentType().equalsIgnoreCase("application/zip")) {
			List<Media> medias = MultipartFileZipToMedias.unzippingArchive(file, config);

			for(Media el : medias) {
				super.save(el, null);

				addMediaToParent(
						new MediaWeb(
								el
								, null
								, null
								, null
						)
						, media.getTreeId());
			}
		}

		return media;
	}

	@Override
	public List<Media> getByIds(String ids) throws SQLException, IllegalAccessException {
		return mediaRepository.getByIds(ids);
	}
}