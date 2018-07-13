package ru.mkrf.label.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mkrf.label.controller.AbstractController;
import ru.mkrf.label.controller.exception.LabelAuthentificationException;
import ru.mkrf.label.controller.exception.LabelAuthorizationException;
import ru.mkrf.label.entity.ResponseEntity;
import ru.mkrf.label.entity.db.User;
import ru.mkrf.label.service.user.UserService;

import java.sql.SQLException;

@RestController
public class UserRestController {
    @Autowired
    private UserService service;

    @GetMapping(value = "/rest/login")
    public ResponseEntity<String> loginUser(
            @RequestParam String login
            , @RequestParam String hash
    ) throws IllegalAccessException, SQLException, LabelAuthentificationException {
        return new ResponseEntity<>(service.checkLogin(new User(login, hash)), HttpStatus.OK.value());
    }
}