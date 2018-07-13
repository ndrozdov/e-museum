package repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.mkrf.label.config.SpringDBConfig;
import ru.mkrf.label.config.SpringServiceConfig;
import ru.mkrf.label.entity.ResponseJSConfig;
import ru.mkrf.label.entity.db.*;
import ru.mkrf.label.entity.jsconfig.Content;
import ru.mkrf.label.entity.web.MediaWeb;
import ru.mkrf.label.entity.web.PageWeb;
import ru.mkrf.label.entity.web.SectionWeb;
import ru.mkrf.label.repository.media.MediaRepository;
import ru.mkrf.label.repository.page.PageRepository;
import ru.mkrf.label.repository.section.SectionRepository;
import ru.mkrf.label.repository.terminal.TerminalRepository;
import ru.mkrf.label.repository.tree.TreeRepository;
import ru.mkrf.label.repository.treemedia.TreeMediaRepository;
import ru.mkrf.label.service.labelservice.LabelService;
import ru.mkrf.label.service.page.PageService;
import ru.mkrf.label.service.section.SectionService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class, SpringServiceConfig.class} )
public class TestRecursive {
    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private PageService pageService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private LabelService labelService;

    @Test
    public void testReqursiveQuery() throws SQLException, IllegalAccessException {
        Terminal terminal = new Terminal();
        terminalRepository.save(terminal, null);

        List<Media> medias = new ArrayList<>();

        for(int i = 0; i < 30; i++) {
            medias.add(new Media("Media " + i));
        }

        for(Media media : medias) {
            mediaRepository.save(media, null);
        }

        SectionWeb sectionWebOne = new SectionWeb(
                new Section(null, 0, 0)
                , new Grid(1,2)
                , Arrays.asList(new Title("sectionOneTitleOne"), new Title("sectionOneTitleTwo"))
                , medias.subList(0, 3).stream()
                        .map(el ->
                                new MediaWeb(
                                        el
                                        , "type"
                                        , new Title("media" + el.getTreeId() + "Title")
                                        , 0))
                        .collect(Collectors.toList())
        );

        SectionWeb sectionWebTwo = new SectionWeb(
                new Section(null, 0, 0)
                , new Grid(2,3)
                , Arrays.asList(new Title("sectionWebTwoOne"), new Title("sectionWebTwoTwo"))
                , medias.subList(4, 5).stream()
                .map(el ->
                        new MediaWeb(
                                el
                                , "type"
                                , new Title("media" + el.getTreeId() + "Title")
                                , 0))
                .collect(Collectors.toList())
        );

        sectionService.save(sectionWebOne, terminal.getTreeId());
        sectionService.save(sectionWebTwo, terminal.getTreeId());

        SectionWeb sectionWebThree = new SectionWeb(
                new Section(null, 0, 0)
                , new Grid(2,3)
                , Arrays.asList(new Title("sectionWebThreeOne"), new Title("sectionWebThreeTwo"))
                , medias.subList(6, 8).stream()
                .map(el ->
                        new MediaWeb(
                                el
                                , "type"
                                , new Title("media" + el.getTreeId() + "Title")
                                , 0))
                .collect(Collectors.toList())
        );

        SectionWeb sectionWebFour = new SectionWeb(
                new Section(null, 0, 0)
                , new Grid(3, 1)
                , Arrays.asList(new Title("sectionWebFourOne"), new Title("sectionWebFourTwo"))
                , medias.subList(9, 14).stream()
                .map(el ->
                        new MediaWeb(
                                el
                                , "type"
                                , new Title("media" + el.getTreeId() + "Title")
                                , 0))
                .collect(Collectors.toList())
        );

        sectionService.save(sectionWebThree, sectionWebTwo.getTreeId());
        sectionService.save(sectionWebFour, sectionWebThree.getTreeId());

        PageWeb pageOneWebTerminal = new PageWeb(
                new Page(null, "PageOneWebTerminal", null, null, null)
                , Arrays.asList(new Title("pageOneWebTerminalTitle"))
                , Arrays.asList(new Text("pageOneWebTerminalText"))
                , medias.subList(15, 18).stream()
                .map(el ->
                        new MediaWeb(
                                el
                                , "type"
                                , new Title("media" + el.getTreeId() + "Title")
                                , 0))
                .collect(Collectors.toList())
        );
        PageWeb pageTwoWebTerminal =
                new PageWeb(
                        new Page(null, "pageTwoWebTerminal", null, null, null)
                        , Arrays.asList(new Title("pageTwoWebTerminalTitle"))
                        , Arrays.asList(new Text("pageTwoWebTerminalText"))
                        , medias.subList(19, 21).stream()
                        .map(el ->
                                new MediaWeb(
                                        el
                                        , "type"
                                        , new Title("media" + el.getTreeId() + "Title")
                                        , 0))
                        .collect(Collectors.toList())
                );

        PageWeb pageOneWebSectionOne =
                new PageWeb(
                    new Page(null, "pageOneWebSectionOne", null, null, null)
                    , Arrays.asList(new Title("pageOneWebSectionOneTitle"))
                    , Arrays.asList(new Text("pageOneWebSectionOneTitle"))
                    , medias.subList(23, 27).stream()
                            .map(el ->
                                    new MediaWeb(
                                            el
                                            , "type"
                                            , new Title("media" + el.getTreeId() + "Title")
                                            , 0))
                            .collect(Collectors.toList())
        );
        PageWeb pageTwoWebSectionOne =
                new PageWeb(
                    new Page(null, "pageTwoWebSectionOne", null, null, null)
                    , Arrays.asList(new Title("pageTwoWebSectionOneTitle"))
                    , Arrays.asList(new Text("pageTwoWebSectionOneText"))
                    , medias.subList(28, 30).stream()
                    .map(el ->
                            new MediaWeb(
                                    el
                                    , "type"
                                    , new Title("media" + el.getTreeId() + "Title")
                                    , 0))
                    .collect(Collectors.toList())
                );

        PageWeb pageThreeWebSectionOne =
                new PageWeb(
                        new Page(null, "pageThreeWebSectionOne", null, null, null)
                        , Arrays.asList(new Title("Title"))
                        , Arrays.asList(new Text("Text"))
                        , null
                );

        PageWeb pageOneWebSectionThree = new PageWeb(
                new Page(null, "pageOneWebSectionFour", null, null, null)
                , Arrays.asList(new Title("Title"))
                , Arrays.asList(new Text("Text"))
                , null
        );

        PageWeb pageOneSectionFour = new PageWeb(
                new Page(null, "pageOneSectionFive", null, null, null)
                , Arrays.asList(new Title("Title"))
                , Arrays.asList(new Text("Text"))
                , null
        );

        pageService.save(pageOneWebTerminal, terminal.getTreeId());
        pageService.save(pageTwoWebTerminal, terminal.getTreeId());
        pageService.save(pageOneWebSectionOne, sectionWebOne.getTreeId());
        pageService.save(pageTwoWebSectionOne, sectionWebOne.getTreeId());
        pageService.save(pageThreeWebSectionOne, sectionWebOne.getTreeId());
        pageService.save(pageOneWebSectionThree, sectionWebThree.getTreeId());
        pageService.save(pageOneSectionFour, sectionWebFour.getTreeId());

        List<Content> contents = labelService.getConfigContent(terminal.getTreeId());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/javascript;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String res =
                null;
        try {
            res = "window.Settings = {\n" +
                    "\t \"terminal\":\n"
                    + objectMapper.writeValueAsString(contents) + "\n\t" +
                    "\n}";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(res);
    }

    @Test
    public void testCreateRecursiveTree() throws SQLException, IllegalAccessException {
        Integer parentId = 243;
        List<Tree> trees = treeRepository.getAllChild( parentId ).stream().collect(Collectors.toList());
        Tree tree = treeRepository.getById(parentId);

        List<TestTreeObj> result = recursive(trees, tree);

        System.out.println(result.size());
    }

    private static List<TestTreeObj> recursive(List<Tree> trees, Tree parent) {
        List<Tree> _trees = trees.stream().filter(el -> Objects.equals(el.getParentId(), parent.getId())).collect(Collectors.toList());
        List<TestTreeObj> result = new ArrayList<>();

        for(Tree tree : _trees) {
            result.add(new TestTreeObj(
                    tree
                    , recursive(
                            trees.stream()
                                    .filter(el -> !Objects.equals(el.getParentId(), parent.getId()))
                                    .collect(Collectors.toList())
                            , tree
                    )
            ));
        }

        return result;
    }
}
