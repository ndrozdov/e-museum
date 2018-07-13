package controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.mkrf.label.config.SpringDBConfig;
import ru.mkrf.label.config.SpringMVCConfig;
import ru.mkrf.label.config.SpringServiceConfig;
import ru.mkrf.label.entity.db.GlobalId;
import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.entity.db.Template;
import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.entity.web.MediaWeb;
import ru.mkrf.label.entity.web.TemplateWeb;
import ru.mkrf.label.service.media.MediaService;
import ru.mkrf.label.service.template.TemplateService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class, SpringServiceConfig.class, SpringMVCConfig.class} )
public class TemplateControllerTest {
    @Autowired
    private TemplateService templateService;

    @Autowired
    private MediaService mediaService;

    @Test
    public void testSaveObjectWeb() throws SQLException, IllegalAccessException {
//        List<Media> medias = new ArrayList<>();
//        medias.add(new Media("MediaOne"));
//        medias.add(new Media("MediaTwo"));
//        medias.add(new Media("MediaThree"));
//
//        for(Media media: medias) {
//            mediaService.save(media, null);
//        }
//
//        Template template = new Template(null, 0, 0);
//        List<Title> titles = Arrays.asList(new Title("titleOne"), new Title("titleTwo"), new Title("titleThree"));
//        List<MediaWeb> mediaIds = medias.stream().map(GlobalId::getTreeId).collect(Collectors.toList());
//
//        TemplateWeb templateWeb = new TemplateWeb(template, titles, mediaIds);
//        templateService.save(templateWeb);
//
//        TemplateWeb _templateWeb = templateService.getByTreeId(templateWeb.getTreeId());
//        Assert.assertNotNull(_templateWeb);
//
//        _templateWeb.getTitles().get(0).setTitle("TitleOneNew");
//        _templateWeb.getTitles().remove(1);
//        templateService.save(_templateWeb);
//        _templateWeb = templateService.getByTreeId(_templateWeb.getTreeId());
//
//        Assert.assertNotNull(_templateWeb);
    }
}
