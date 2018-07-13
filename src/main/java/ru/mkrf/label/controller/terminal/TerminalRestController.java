package ru.mkrf.label.controller.terminal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mkrf.label.controller.AbstractController;
import ru.mkrf.label.controller.exception.LabelAuthorizationException;
import ru.mkrf.label.entity.ResponseEntity;
import ru.mkrf.label.entity.db.Terminal;
import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.entity.web.TerminalWeb;
import ru.mkrf.label.repository.terminal.TerminalRepository;
import ru.mkrf.label.service.terminal.TerminalService;
import ru.mkrf.label.service.title.TitleService;
import ru.mkrf.label.service.user.UserService;

import java.sql.SQLException;
import java.util.List;

@RestController
public class TerminalRestController extends AbstractController<Terminal> {
    @Autowired
    private UserService userService;

    @Autowired
    private TerminalService terminalService;

    @GetMapping(value = "rest/terminal")
    public ResponseEntity<TerminalWeb> getById(
            @RequestParam Integer id
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(terminalService.getByTerminalId(id), HttpStatus.OK.value());
    }

    @GetMapping(value = "rest/terminals")
    public ResponseEntity<List<TerminalWeb>> getAll(
            @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(terminalService.getAllTerminalWeb(), HttpStatus.OK.value());
    }

    @PostMapping(value = "rest/terminal")
    public ResponseEntity<TerminalWeb> save(
             @RequestBody TerminalWeb obj
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        return new ResponseEntity<>(terminalService.save(obj), HttpStatus.OK.value());
    }
}
