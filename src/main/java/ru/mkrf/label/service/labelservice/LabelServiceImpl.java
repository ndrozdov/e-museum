package ru.mkrf.label.service.labelservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mkrf.label.entity.ResponseJSConfig;
import ru.mkrf.label.entity.db.BaseEntity;
import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.entity.db.Terminal;
import ru.mkrf.label.entity.db.Tree;
import ru.mkrf.label.entity.jsconfig.Content;
import ru.mkrf.label.entity.web.MediaWeb;
import ru.mkrf.label.entity.web.PageWeb;
import ru.mkrf.label.entity.web.SectionWeb;
import ru.mkrf.label.repository.labelservice.LabelServiceRepository;
import ru.mkrf.label.repository.media.MediaRepository;
import ru.mkrf.label.repository.tree.TreeRepository;
import ru.mkrf.label.service.media.MediaService;
import ru.mkrf.label.service.page.PageService;
import ru.mkrf.label.service.section.SectionService;
import ru.mkrf.label.service.terminal.TerminalService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelServiceRepository repository;

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private PageService pageService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private TerminalService terminalService;

    @Override
    public List<Media> getConfigContents(Integer terminalId) throws SQLException, IllegalAccessException {
        Terminal terminal = terminalService.getById(terminalId);
        List<Tree> trees = treeRepository.getAllChild(terminalId);
        List<Integer> ids = trees != null ? trees.stream().map(BaseEntity::getId).collect(Collectors.toList()) : null;

        if(terminal != null && terminal.getTemplateId() != null)
            ids.add(terminal.getTemplateId());

        List<Media> medias = new ArrayList<>();
        List<Media> _medias;

        for(Integer id: ids) {
            _medias = mediaRepository.getAllByTreeParentId(id);
            medias.addAll(_medias);
        }

        return medias;
    }

    @Override
    public ResponseJSConfig getJSConfig(Integer terminalId) throws SQLException {
        return repository.getJSConfig(terminalId);
    }

    @Override
    public List<Content> getConfigContent(Integer terminalId) throws SQLException, IllegalAccessException {
        List<Tree> trees = treeRepository.getAllChild( terminalId ).stream().collect(Collectors.toList());
        List<Content> contents = new ArrayList<>();
        List<MediaWeb> medias;
        SectionWeb sectionWeb;
        PageWeb pageWeb;
        Content content;

        for(Tree tree : trees) {
            sectionWeb = sectionService.getBySectionId(tree.getId());

            if(sectionWeb != null && sectionWeb.getDeleted() != 1) {
                medias = mediaService.getAllMediaWebByTreeParentId(sectionWeb.getTreeId());
                content = new Content(sectionWeb, tree, medias);
            } else {
                pageWeb = pageService.getByTreeId(tree.getId());
                medias = pageWeb != null ? mediaService.getAllMediaWebByTreeParentId(pageWeb.getTreeId()) : null;
                content = pageWeb != null && pageWeb.getDeleted() != 1 ? new Content(pageWeb, tree, medias) : null;
            }

            if(content == null)
                continue;

            contents.add(content);
        }

        Tree tree = treeRepository.getById( terminalId );

        List<Content> result = recursive(contents, tree);

        return result;
    }

    private List<Content> recursive(List<Content> contents, Tree parent) throws SQLException, IllegalAccessException {
        List<Content> _contents = contents.stream()
                .filter(el -> Objects.equals(el.getTree().getParentId(), parent.getId()))
                .sorted()
                .collect(Collectors.toList());
        List<Content> result = new ArrayList<>();

        for(Content content : _contents) {
            content.setContents(
                    recursive(contents.stream()
                            .filter(el -> !Objects.equals(el.getTree().getParentId(), parent.getId()))
                            .collect(Collectors.toList())
                    , content.getTree()));

            result.add(content);
        }

        return result;
    }
}
