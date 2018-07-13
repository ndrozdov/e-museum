package ru.mkrf.label.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mkrf.label.controller.AbstractController;
import ru.mkrf.label.controller.exception.LabelAuthorizationException;
import ru.mkrf.label.entity.ResponseEntity;
import ru.mkrf.label.entity.db.Page;
import ru.mkrf.label.entity.web.PageWeb;
import ru.mkrf.label.service.page.PageService;
import ru.mkrf.label.service.user.UserService;
import java.sql.SQLException;
import java.util.List;

@RestController
public class PageRestController extends AbstractController<Page> {
    @Autowired
    private UserService userService;

    @Autowired
    private PageService pageService;

    @GetMapping(value = "rest/page")
    public ResponseEntity<PageWeb> getById(
            @RequestParam Integer id
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(pageService.getByTreeId(id), HttpStatus.OK.value());
    }

    @GetMapping(value = "rest/pages")
    public ResponseEntity<List<PageWeb>> getAll(
            @RequestParam(name = "parent_id") Integer parentId
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

//        if(parentId != null)
            return new ResponseEntity<>(pageService.getAllByTreeParentId(parentId), HttpStatus.OK.value());

//        return super.getAll();
    }

    @PostMapping(value = "rest/page")
    public ResponseEntity<PageWeb> save(
            @RequestParam("parent_id") Integer parentId,
            @RequestBody PageWeb obj
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(pageService.save(obj, parentId), HttpStatus.OK.value());
    }
}
