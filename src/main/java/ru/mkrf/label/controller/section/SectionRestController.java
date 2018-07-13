package ru.mkrf.label.controller.section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mkrf.label.controller.AbstractController;
import ru.mkrf.label.controller.exception.LabelAuthorizationException;
import ru.mkrf.label.entity.ResponseEntity;
import ru.mkrf.label.entity.db.Section;
import ru.mkrf.label.entity.web.SectionWeb;
import ru.mkrf.label.service.section.SectionService;
import ru.mkrf.label.service.user.UserService;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@RestController
public class SectionRestController extends AbstractController<Section>{
    @Autowired
    private SectionService service;

    @Autowired
    private UserService userService;

    @GetMapping(value = "rest/section")
    public ResponseEntity<SectionWeb> getById(
            @RequestParam Integer id
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(service.getBySectionId(id), HttpStatus.OK.value());
    }

    @GetMapping(value = "rest/sections")
    public ResponseEntity<List<SectionWeb>> getAll(
             @RequestParam(name = "parent_id") Integer parentId
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(service.getAllByTreeParentId(parentId), HttpStatus.OK.value());
    }

    @PostMapping(value = "rest/section")
    public ResponseEntity<SectionWeb> save(
            @RequestParam("parent_id") Integer parentId
            , @RequestBody SectionWeb obj
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession( sessionId );

        return new ResponseEntity<>(service.save(obj, parentId), HttpStatus.OK.value());
    }
}
