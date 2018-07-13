package ru.mkrf.label.entity.db;

import static ru.mkrf.label.util.PreparedStatementUtil.*;
import static ru.mkrf.label.util.PreparedStatementUtil.genDeleteQuery;

public class Media extends GlobalId {
	public static final String GET_BY_ID = "SELECT * FROM media WHERE tree_id = :treeId;";
	public static final String GET_BY_IDS = "SELECT * FROM media WHERE tree_id IN ( :ids );";
	public static final String GET_ALL =
			"SELECT * FROM media m\n" +
			"WHERE m.tree_id NOT IN (\n" +
			"  SELECT mt.media_tree_id FROM media m\n" +
			"    INNER JOIN media_tree mt ON mt.tree_id = m.tree_id\n" +
			")";
	public static final String INSERT = genInsertQuery(Media.class);
	public static final String UPDATE = genUpdateQueryWithTreeId(Media.class);
	public static final String DELETE = genDeleteQuery(Media.class);

	private String originalFileName;
	private String fileName;
	private String uploadDate;
	private String url;
	private Integer fileSize;
	private String mimeType;
	private Integer deleted;

	public Media() {

	}

	public Media(String originalFileName) {
		this.originalFileName = originalFileName;
		this.fileName = originalFileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
}