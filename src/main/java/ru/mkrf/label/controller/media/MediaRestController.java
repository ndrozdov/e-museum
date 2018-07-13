package ru.mkrf.label.controller.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mkrf.label.controller.AbstractController;
import ru.mkrf.label.controller.exception.LabelAuthorizationException;

import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.entity.ResponseEntity;
import ru.mkrf.label.service.media.MediaService;
import ru.mkrf.label.service.user.UserService;
import ru.mkrf.label.util.configuration.LabelConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

@RestController
public class MediaRestController extends AbstractController<Media> {
    @Autowired
    private MediaService service;

    @Autowired
    private UserService userService;

    @Autowired
    private LabelConfig config;

    @GetMapping(value = "rest/media")
    public ResponseEntity<Media> getById(
            @RequestParam Integer id
    ) throws SQLException, IllegalAccessException {
        return super.getById(id);
    }

    @GetMapping(value = "rest/medias")
    public ResponseEntity<List<Media>> getAll(
            @RequestParam(name = "parent_id", required = false) Integer parentId
            , @RequestParam(name = "ids", required = false) String ids
            , @RequestHeader(name = "sessionId", required = false) String sessionId
            , HttpServletRequest request
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        if(request.getParameterMap().size() > 1)
            throw new IllegalArgumentException("to many request parameters");

        if(parentId != null)
            return new ResponseEntity<>(service.getAllByTreeParentId(parentId), HttpStatus.OK.value());

        if(ids != null)
            return new ResponseEntity<>(service.getByIds(ids), HttpStatus.OK.value());

        return super.getAll();
    }

    @PostMapping(value = "rest/media")
    public ResponseEntity<Media> save(
            @RequestBody Media obj
    ) throws IllegalAccessException, SQLException {
        return super.save(obj, null);
    }

    @PostMapping(value = "rest/media/upload")
    public @ResponseBody ResponseEntity<List<Media>> uploadMedias(
            @RequestParam("files") MultipartFile[] files
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws IOException, LabelAuthorizationException, IllegalAccessException, SQLException {
        userService.checkSession(sessionId);
        List<Media> medias = new ArrayList<>();

        for(MultipartFile file : files) {
            if(file.isEmpty()) {
                return new ResponseEntity("You failed to upload because the file was empty", HttpStatus.BAD_REQUEST.value());
            }

            medias.add(service.uploadMedia(file));
        }

        return new ResponseEntity<>(medias, HttpStatus.OK.value());
    }

    @GetMapping(value = "rest/media/download")
    public org.springframework.http.ResponseEntity download(
            @RequestParam(name = "id") Integer id
            , @RequestHeader(name = "sessionId", required = false) String sessionId
    ) throws SQLException, IllegalAccessException, IOException, LabelAuthorizationException {
        Media media = super.getById(id).getData();
        Path path = Paths.get(config.getPathToMedia(), media.getFileName());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", media.getMimeType());

        org.springframework.http.ResponseEntity response =
                new org.springframework.http.ResponseEntity(
                        Files.readAllBytes(path)
                        , headers
                        , HttpStatus.OK);

        return response;
    }
}
