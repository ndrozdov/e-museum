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
import ru.mkrf.label.entity.db.*;
import ru.mkrf.label.entity.web.MediaWeb;
import ru.mkrf.label.entity.web.PageWeb;
import ru.mkrf.label.service.media.MediaService;
import ru.mkrf.label.service.page.PageService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class, SpringServiceConfig.class, SpringMVCConfig.class} )
public class PageControllerTest {
    @Autowired
    private MediaService mediaService;

    @Autowired
    private PageService pageService;

    @Test
    public void testSaveController() throws SQLException, IllegalAccessException {
        List<Media> medias = new ArrayList<>();
        medias.add(new Media("MediaOne"));
        medias.add(new Media("MediaTwo"));
        medias.add(new Media("MediaThree"));

        for(Media media: medias) {
            mediaService.save(media, null);
        }

        Page page = new Page(null, "content", 1, 0, 0);
        List<Title> titles = Arrays.asList(new Title("titleOne"));
        List<Text> texts = Arrays.asList(new Text("textOne"), new Text("textTwo"), new Text("textThree"));
        List<MediaWeb> mediasWeb = medias.stream()
                .map(el -> new MediaWeb(
                    el
                    , "type"
                    , new Title("title" + el.getOriginalFileName())
                    , 0)
                ).collect(Collectors.toList());

        PageWeb pageWeb = new PageWeb(page, titles, texts, mediasWeb);
        pageService.save(pageWeb, null);

        PageWeb _pageWeb = pageService.getByTreeId(pageWeb.getTreeId());
        Assert.assertNotNull(_pageWeb);

        pageWeb.setContent("new Content");
        page.setDeleted(1);
        page.setSortord(1);

        for(MediaWeb mediaWeb : pageWeb.getMedias()) {
            mediaWeb.setType("new type");
            mediaWeb.getTitle().setTitle("new title");
        }

        pageService.save(pageWeb, null);
        _pageWeb = pageService.getByTreeId(pageWeb.getTreeId());
        Assert.assertNotNull(_pageWeb);
    }
}
