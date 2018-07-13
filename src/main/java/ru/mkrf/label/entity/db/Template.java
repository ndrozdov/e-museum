package ru.mkrf.label.entity.db;

import static ru.mkrf.label.util.PreparedStatementUtil.*;
import static ru.mkrf.label.util.PreparedStatementUtil.genDeleteQuery;

public class Template extends GlobalId {
	public static final String GET_BY_ID = "SELECT * FROM template WHERE tree_id = :treeId";
	public static final String GET_ALL = "SELECT * FROM template" ;
	public static final String INSERT = genInsertQuery(Template.class);
	public static final String UPDATE = genUpdateQueryWithTreeId(Template.class);
	public static final String DELETE = genDeleteQuery(Template.class);

	private Integer deleted;
	private Integer sortord;

	public Template() {

	}

	public Template(Integer treeId, Integer deleted, Integer sortord) {
		super(treeId);
		this.deleted = deleted;
		this.sortord = sortord;
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
}