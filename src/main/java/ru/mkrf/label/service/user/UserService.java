package ru.mkrf.label.service.user;

import ru.mkrf.label.controller.exception.LabelAuthentificationException;
import ru.mkrf.label.controller.exception.LabelAuthorizationException;
import ru.mkrf.label.entity.db.User;

import java.sql.SQLException;

public interface UserService {
    String checkLogin(User user) throws IllegalAccessException, SQLException, LabelAuthentificationException;
    User checkSession(String sessionId) throws LabelAuthorizationException;
}
