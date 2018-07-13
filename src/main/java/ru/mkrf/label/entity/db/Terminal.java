package ru.mkrf.label.entity.db;
import static ru.mkrf.label.util.PreparedStatementUtil.*;

public class Terminal extends GlobalId {
	public static final String GET_BY_ID = "SELECT * FROM terminal WHERE tree_id = :treeId";
	public static final String GET_ALL = "SELECT * FROM terminal";
	public static final String INSERT = genInsertQuery(Terminal.class);
	public static final String UPDATE =
			"UPDATE terminal SET " +
					"tree_id = :treeId " +
					", template_id = :templateId " +
					", last_active_time = :lastActiveTime" +
					", deleted = :deleted " +
					", sortord = :sortord " +
					", version = CASE WHEN version IS NULL THEN 1 ELSE version + 1 END " +
					", terminal_version = :terminalVersion " +
			"WHERE " +
				"tree_id = :treeId;";
	public static final String INCREMENT_VERSION = "UPDATE terminal SET version = CASE WHEN version IS NULL THEN 1 ELSE  version + 1 END WHERE tree_id = :treeId;";
	public static final String SET_TERMINAL_VERSION = "UPDATE terminal SET terminal_version = :terminalVersion WHERE tree_id = :treeId;";
	public static final String DELETE = genDeleteQuery(Terminal.class);

	private Integer templateId;
	private Integer version;
	private Integer terminalVersion;
	private Integer lastActiveTime;
	private Integer deleted;
	private Integer sortord;

	public Terminal() {

	}

	public Terminal(Integer treeId, Integer templateId, Integer version, Integer terminalVersion, Integer lastActiveTime, Integer deleted, Integer sortord) {
		super.setTreeId(treeId);
		this.templateId = templateId;
		this.version = version;
		this.terminalVersion = terminalVersion;
		this.lastActiveTime = lastActiveTime;
		this.deleted = deleted;
		this.sortord = sortord;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getTreeId() {
		return treeId;
	}

	public void setTreeId(Integer treeId) {
		this.treeId = treeId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getTerminalVersion() {
		return terminalVersion;
	}

	public void setTerminalVersion(Integer terminalVersion) {
		this.terminalVersion = terminalVersion;
	}

	public Integer getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(Integer lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getSortord() {
		return sortord;
	}

	public void setSortord(Integer sortord) {
		this.sortord = sortord;
	}

	@Override
	public String toString() {
		return "Terminal{" +
				", version=" + version +
				", terminalVersion=" + terminalVersion +
				", lastActiveTime=" + lastActiveTime +
				", deleted=" + deleted +
				", sortord=" + sortord +
				'}';
	}
}