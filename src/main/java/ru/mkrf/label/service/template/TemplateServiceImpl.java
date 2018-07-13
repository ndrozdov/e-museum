package ru.mkrf.label.service.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mkrf.label.entity.db.GlobalId;
import ru.mkrf.label.entity.db.Template;
import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.entity.web.MediaWeb;
import ru.mkrf.label.entity.web.TemplateWeb;
import ru.mkrf.label.repository.media.MediaRepository;
import ru.mkrf.label.repository.template.TemplateRepository;
import ru.mkrf.label.repository.terminal.TerminalRepository;
import ru.mkrf.label.repository.title.TitleRepository;
import ru.mkrf.label.repository.tree.TreeRepository;
import ru.mkrf.label.service.AbstractBaseService;
import ru.mkrf.label.service.media.MediaService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateServiceImpl extends AbstractBaseService<Template> implements TemplateService {
    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @Override
    public TemplateWeb getByTreeId(Integer id) throws SQLException, IllegalAccessException {
        Template template = templateRepository.getById(id);

        if (template == null)
            return null;

        List<Title> titles = titleRepository.getAllByTreeParentId(template.getTreeId());
        List<MediaWeb> medias = mediaService.getAllMediaWebByTreeParentId(template.getTreeId());

        return new TemplateWeb(template, titles, medias);
    }

    @Override
    public List<TemplateWeb> getAllTemplateWeb() throws SQLException, IllegalAccessException {
        List<Template> templates = super.getAll();
        List<TemplateWeb> templateWebs = new ArrayList<>();

        for(Template template : templates)
            templateWebs.add(getByTreeId(template.getTreeId()));

        return templateWebs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager")
    public TemplateWeb save(TemplateWeb templateWeb) throws SQLException, IllegalAccessException {
        templateRepository.save(templateWeb, null);
        titleRepository.merge(templateWeb.getTitles(), templateWeb.getTreeId());
        mediaRepository.mergeMediaList(templateWeb.getMedias(), templateWeb.getTreeId());

        terminalRepository.incrementTerminalVersion(
                treeRepository.getParentIdByChildId(templateWeb.getTreeId())
        );

        return templateWeb;
    }
}
