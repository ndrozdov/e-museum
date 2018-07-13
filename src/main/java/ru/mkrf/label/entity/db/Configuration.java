package ru.mkrf.label.entity.db;

import static ru.mkrf.label.util.PreparedStatementUtil.*;
import static ru.mkrf.label.util.PreparedStatementUtil.genDeleteQuery;

public class Configuration extends BaseEntity {
	public static final String GET_BY_ID = "SELECT rowid, * FROM configuration;";
	public static final String GET_ALL = "SELECT rowid, * FROM configuration";
	public static final String INSERT = "INSERT INTO configuration (name, value) VALUES (:name, :value);";
	public static final String UPDATE = "UPDATE configuration SET name = :name, value = :value WHERE rowid = :id";
	public static final String DELETE = genDeleteQuery(Configuration.class);

	private String name;
	private String value;

	public Configuration() {

	}

	public Configuration(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}