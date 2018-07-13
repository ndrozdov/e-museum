package ru.mkrf.label.controller.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.mkrf.label.controller.exception.LabelAuthorizationException;
import ru.mkrf.label.controller.media.MediaRestController;
import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.entity.db.Terminal;
import ru.mkrf.label.entity.jsconfig.Content;
import ru.mkrf.label.entity.jsconfig.TerminalJSConfig;
import ru.mkrf.label.entity.web.TerminalWeb;
import ru.mkrf.label.repository.media.MediaRepository;
import ru.mkrf.label.service.labelservice.LabelService;
import ru.mkrf.label.service.terminal.TerminalService;
import ru.mkrf.label.service.tree.TreeService;
import ru.mkrf.label.service.user.UserService;
import ru.mkrf.label.util.PreparedStatementUtil;
import ru.mkrf.label.util.configuration.LabelConfig;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class ServiceController {
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private TerminalService terminalService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private TreeService treeService;

    @Autowired
    private UserService userService;

    @Autowired
    private LabelConfig config;

    @GetMapping(
            value = "rest/service/sync",
            produces = "text/plain;charset=UTF-8"
    )
    public @ResponseBody
    String sync(
            @RequestParam(name = "terminal_id") Integer terminalId
            , @RequestParam(name = "version") Integer version
    ) throws Exception {
        String contextPath = servletContext.getContextPath();
        StringBuilder builder = new StringBuilder();

        String urlMedia =  contextPath + "/" + PreparedStatementUtil.getUrlRestMethod(
                MediaRestController.class.getDeclaredMethod("download", Integer.class, String.class)
        );

        String urlConfig = contextPath + "/" + PreparedStatementUtil.getUrlRestMethod(
                ServiceController.class.getDeclaredMethod("getConfig", Integer.class)
        );

        String urlConfigVersion = contextPath + "/" + PreparedStatementUtil.getUrlRestMethod(
                ServiceController.class.getDeclaredMethod("getConfigVersion", Integer.class)
        );

        Terminal terminal = terminalService.getById(terminalId);
        terminalService.setTerminalVersion(terminalId, version);

        if(terminal == null || terminal.getTreeId() == null || version.equals(terminal.getVersion()))
            return "";

        List<Media> medias = labelService.getConfigContents(terminalId);

        for(Media media : medias) {
            if("application/zip".equalsIgnoreCase(media.getMimeType())) {
                for(Media _media: mediaRepository.getAllByTreeParentId(media.getTreeId())) {
                    builder.append(
                            genSyncFile(
                                    urlMedia + "?id=" + _media.getTreeId()
                                    , _media.getUrl() + _media.getOriginalFileName()))
                            .append("\n");
                }
            } else {
                builder.append(
                    genSyncFile(
                        urlMedia + "?id=" + media.getTreeId()
                        , "content/media/" + media.getFileName()))
                    .append("\n");
            }
        }

        builder.append(
                genSyncFile(
                        urlConfig + "?terminal_id=" + terminalId
                        , "content/config.js"))
                .append("\n");

        builder.append(
                genSyncFile(
                        urlConfigVersion + "?terminal_id=" + terminalId
                        , "content/config_version.txt"))
                .append("\n");

        terminal.setTerminalVersion(version);

        return builder.toString();
    }

    @GetMapping(
            value = "rest/service/conf",
            produces = "text/plain;charset=UTF-8"
    )
    public org.springframework.http.ResponseEntity getConfig(
            @RequestParam(name = "terminal_id") Integer terminalId
//            , @RequestHeader(name = "sessionId") String sessionId
    ) throws SQLException, IllegalAccessException, NoSuchMethodException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/javascript;charset=UTF-8");

        String res = JSConfig(terminalId);

        return new org.springframework.http.ResponseEntity(
                res,
                headers,
                HttpStatus.OK
        );
    }

    @GetMapping(
            value = "rest/service/conf_version",
            produces = "text/plain;charset=UTF-8"
    )
    public org.springframework.http.ResponseEntity getConfigVersion(
            @RequestParam(name = "terminal_id") Integer terminalId
//            , @RequestHeader(name = "sessionId") String sessionId
    ) throws SQLException, IllegalAccessException, NoSuchMethodException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain;charset=UTF-8");
        Terminal terminal = terminalService.getByTerminalId(terminalId);

        String res = "version = " + terminal.getTerminalVersion();

        return new org.springframework.http.ResponseEntity(
                res,
                headers,
                HttpStatus.OK
        );
    }

    @GetMapping( value = "rest/download/zip" )
    public void syncByZip(
            @RequestParam(name = "terminal_id") Integer terminalId
            , HttpServletResponse response
    ) throws SQLException, IllegalAccessException, NoSuchMethodException, IOException {
        Terminal terminal = terminalService.getById(terminalId);

        try (
                OutputStream os = response.getOutputStream();
                ZipOutputStream zipOut = new ZipOutputStream(os);
        ) {
            zipOut.setLevel(Deflater.NO_COMPRESSION);
            List<Media> medias = labelService.getConfigContents( terminalId );

            response.addHeader("Content-Type", "application/zip");
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("Terminal-" + terminal.getTreeId() + ".zip", "UTF-8"));

            for (Media media : medias) {
                if ( "application/zip".equalsIgnoreCase(media.getMimeType() ) ) {
                    for(Media zipMedia : mediaRepository.getAllByTreeParentId(media.getTreeId())) {
                        ZipEntry zipEntry = new ZipEntry(Paths.get(zipMedia.getUrl(), zipMedia.getOriginalFileName()).toString());
                        zipOut.putNextEntry(zipEntry);
                        Files.copy(Paths.get(config.getPathToMedia(), zipMedia.getFileName()), zipOut);
                        os.flush();
                    }
                } else {
                    try {
                        ZipEntry zipEntry =
                                new ZipEntry(
                                        Paths.get(media.getUrl(), media.getFileName()).toString()
                                );
                        zipOut.putNextEntry(zipEntry);
                        Files.copy(Paths.get(config.getPathToMedia(), media.getFileName()), zipOut);
                        os.flush();
                    } catch (Exception ignored) {

                    }
                }
            }

            String res = JSConfig(terminalId);
            File jsConfig = new File("config.js");
            FileOutputStream fs = new FileOutputStream(jsConfig);
            fs.write(res.getBytes("UTF-8"));
            fs.close();

            FileInputStream fis = new FileInputStream(jsConfig);
            ZipEntry zipEntry = new ZipEntry("content/" + jsConfig.getName());

            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;

            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }

            os.flush();
        }
    }

    @PostMapping(value = "rest/service/change_parent")
    public void changeParent(
            @RequestParam(name = "child_id") Integer childId
            , @RequestBody Integer parentId
            , @RequestHeader(name = "sessionId") String sessionId
    ) throws SQLException, IllegalAccessException, LabelAuthorizationException {
        userService.checkSession(sessionId);

        treeService.changeParent(parentId, childId);
    }

    private static String genSyncFile(String url, String path) {
        String template =
                "url=\":url\"\n" +
                "output=\":path\"\n";

        return template
                .replace(":url", url)
                .replace(":path", path);
    }

    private String JSConfig(Integer terminalId) throws SQLException, IllegalAccessException, JsonProcessingException {
        TerminalWeb terminalWeb = terminalService.getByTerminalId(terminalId);
        List<Content> contents = labelService.getConfigContent(terminalId);
        TerminalJSConfig terminalJSConfig = new TerminalJSConfig(terminalWeb, contents);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        return "window.Settings = {\n" +
                "'terminal':" +
                objectMapper.writeValueAsString(terminalJSConfig) + "\n\t" +
                "\n}";
    }
}

