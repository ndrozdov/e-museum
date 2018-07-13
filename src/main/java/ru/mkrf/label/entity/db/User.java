package ru.mkrf.label.entity.db;

import static ru.mkrf.label.util.PreparedStatementUtil.*;
import static ru.mkrf.label.util.PreparedStatementUtil.genDeleteQuery;

public class User extends BaseEntity {
    public static final String GET_BY_ID = genSelectQueryByRowId(Title.class);
    public static final String GET_ALL = genSelectQuery(User.class);
    public static final String INSERT = genInsertQuery(User.class);
    public static final String UPDATE = genUpdateQuery(User.class);
    public static final String DELETE = genDeleteQuery(User.class);

    public static final String GET_BY_LOGIN_AND_HASH = "SELECT rowid, login, hash FROM users WHERE login = :login AND hash = :hash";
//    public static final String UPDATE = "UPDATE users SET hash = :hash WHERE login = :login";

    private String login;
    private String hash;

    public User() {
    }

    public User(String login, String hash) {
        this.login = login;
        this.hash = hash;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
