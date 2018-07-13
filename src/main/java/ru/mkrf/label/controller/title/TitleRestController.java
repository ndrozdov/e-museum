package ru.mkrf.label.controller.title;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mkrf.label.controller.AbstractController;
import ru.mkrf.label.controller.exception.LabelAuthorizationException;
import ru.mkrf.label.entity.ResponseEntity;
import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.service.title.TitleService;
import ru.mkrf.label.service.user.UserService;

import java.sql.SQLException;
import java.util.List;

@RestController
public class TitleRestController extends AbstractController<Title> {
    @Autowired
    private UserService userService;

    @Autowired
    private TitleService titleService;

    @GetMapping(value = "rest/title")
    public ResponseEntity<Title> getById(
            @RequestParam Integer id
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return super.getById(id);
    }

    @GetMapping(value = "rest/titles")
    public ResponseEntity<List<Title>> getAll(
            @RequestParam(name = "parent_tree_id", required = false) Integer parentTreeId
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        if(parentTreeId != null)
            titleService.getAllByTreeParentId(parentTreeId);

        return super.getAll();
    }

    @PostMapping(value = "rest/title")
    public ResponseEntity<Title> save(
            @RequestParam("parent_id") Integer parent_id
            , @RequestBody Title obj
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        System.out.println(parent_id);
        userService.checkSession(sessionId);

        return super.save(obj, parent_id);
    }
}
