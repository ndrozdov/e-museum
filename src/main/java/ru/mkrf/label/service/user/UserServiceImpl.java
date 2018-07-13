package ru.mkrf.label.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mkrf.label.controller.exception.LabelAuthentificationException;
import ru.mkrf.label.controller.exception.LabelAuthorizationException;
import ru.mkrf.label.entity.db.User;
import ru.mkrf.label.repository.user.UserRepository;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private Map<String, User> users;

    @Autowired
    private UserRepository repository;

    @Override
    public String checkLogin(User user) throws IllegalAccessException, SQLException, LabelAuthentificationException {
        User userFromDB = repository.getByLoginAndHash(user);
        String sessionId;

        if (userFromDB != null) {
            sessionId = UUID.randomUUID().toString().replaceAll("-", "");
            users.put(sessionId, userFromDB);
            return sessionId;
        } else {
            throw new LabelAuthentificationException("incorrect login/password");
        }
    }

    @Override
    public User checkSession(String sessionId) throws LabelAuthorizationException {
        if (sessionId == null)
            throw new LabelAuthorizationException("incorrect sessionId");

        User user = users.get(sessionId);

        if(user != null)
            return users.get(sessionId);
        else
            throw new LabelAuthorizationException("incorrect sessionId");
    }
}
