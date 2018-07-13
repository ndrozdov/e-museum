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
import ru.mkrf.label.entity.web.SectionWeb;
import ru.mkrf.label.repository.terminal.TerminalRepository;
import ru.mkrf.label.service.media.MediaService;
import ru.mkrf.label.service.section.SectionService;
import ru.mkrf.label.service.terminal.TerminalService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class, SpringServiceConfig.class, SpringMVCConfig.class} )
public class SectionControllerTest {
    @Autowired
    private SectionService sectionService;

    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private MediaService mediaService;

    @Test
    public void testSaveController() throws SQLException, IllegalAccessException {
        Terminal terminal = new Terminal();
        terminalRepository.save(terminal, null);

        List<Media> medias = new ArrayList<>();
        medias.add(new Media("MediaOne"));
        medias.add(new Media("MediaTwo"));
        medias.add(new Media("MediaThree"));

        for(Media media: medias) {
            mediaService.save(media, null);
        }

        Section section = new Section(null, 0, 0);
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("titleSectionOne"));
        titles.add(new Title("titleSectionTwo"));
        titles.add(new Title("titleSectionThree"));

        List<MediaWeb> mediasWeb = medias.stream()
                .map(el -> new MediaWeb(
                        el
                        , "type"
                        , new Title("title" + el.getOriginalFileName())
                        , 0)
                ).collect(Collectors.toList());
        Grid grid = new Grid(1, 2);


        SectionWeb sectionWeb = new SectionWeb(section, grid, titles, mediasWeb);
        sectionService.save(sectionWeb, terminal.getTreeId());

        SectionWeb _sectionWeb = sectionService.getBySectionId(sectionWeb.getTreeId());
        Assert.assertNotNull(_sectionWeb);

        sectionWeb.getTitles().remove(0);
        sectionWeb.getTitles().forEach(el -> el.setTitle("new " + el.getTitle()));

        sectionWeb.getGrid().setCols(5);

        sectionWeb.getMedias().remove(0);
        sectionWeb.getMedias().forEach(el -> el.getTitle().setTitle("new " + el.getTitle().getTitle()));
        sectionService.save(sectionWeb, terminal.getTreeId());

        _sectionWeb = sectionService.getBySectionId(sectionWeb.getTreeId());
        Assert.assertNotNull(_sectionWeb);

    }
}
