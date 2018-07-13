package ru.mkrf.label.controller.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mkrf.label.controller.exception.LabelAuthorizationException;
import ru.mkrf.label.entity.ResponseEntity;
import ru.mkrf.label.entity.db.Configuration;
import ru.mkrf.label.service.configuration.ConfigurationService;
import ru.mkrf.label.service.user.UserService;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ConfigurationController {
    @Autowired
    private UserService userService;

    @Autowired
    private ConfigurationService configurationService;

    @GetMapping(value = "rest/configuration")
    public ResponseEntity<Configuration> getById(
            @RequestParam Integer id
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(configurationService.getById(id), HttpStatus.OK.value());
    }

    @GetMapping(value = "rest/configurations")
    public ResponseEntity<List<Configuration>> getAll(
            @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(configurationService.getAll(), HttpStatus.OK.value());
    }

    @DeleteMapping(value = "rest/configuration")
    public void delete(
            @RequestParam Integer id
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        configurationService.delete(id);
    }

    @PostMapping(value = "rest/configuration")
    public ResponseEntity<Configuration> save(
            @RequestBody Configuration obj
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(configurationService.save(obj), HttpStatus.OK.value());
    }
}
