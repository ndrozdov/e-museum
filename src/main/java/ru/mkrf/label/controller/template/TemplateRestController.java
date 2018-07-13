package ru.mkrf.label.controller.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mkrf.label.controller.AbstractController;
import ru.mkrf.label.controller.exception.LabelAuthorizationException;
import ru.mkrf.label.entity.ResponseEntity;
import ru.mkrf.label.entity.db.Template;
import ru.mkrf.label.entity.web.TemplateWeb;
import ru.mkrf.label.service.template.TemplateService;
import ru.mkrf.label.service.user.UserService;

import java.sql.SQLException;
import java.util.List;

@RestController
public class TemplateRestController extends AbstractController<Template> {
    @Autowired
    private TemplateService service;

    @Autowired
    private UserService userService;

    @GetMapping(value = "rest/template")
    public ResponseEntity<TemplateWeb> getById(
            @RequestParam Integer id
            , @RequestHeader(name = "sessionId") String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(service.getByTreeId(id), HttpStatus.OK.value());
    }

    @GetMapping(value = "rest/templates")
    public ResponseEntity<List<TemplateWeb>> getAll(
            @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(service.getAllTemplateWeb(), HttpStatus.OK.value());
    }

    @PostMapping(value = "rest/template")
    public ResponseEntity<TemplateWeb> save(
            @RequestBody TemplateWeb templateWeb
            , @RequestHeader(name = "sessionId") String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(service.save(templateWeb), HttpStatus.OK.value());
    }
}
