package ru.mkrf.label.service.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mkrf.label.entity.db.*;
import ru.mkrf.label.entity.web.MediaWeb;
import ru.mkrf.label.entity.web.PageWeb;
import ru.mkrf.label.repository.media.MediaRepository;
import ru.mkrf.label.repository.page.PageRepository;
import ru.mkrf.label.repository.terminal.TerminalRepository;
import ru.mkrf.label.repository.text.TextRepository;
import ru.mkrf.label.repository.title.TitleRepository;
import ru.mkrf.label.repository.tree.TreeRepository;
import ru.mkrf.label.service.AbstractBaseService;
import ru.mkrf.label.service.media.MediaService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PageServiceImpl extends AbstractBaseService<Page> implements PageService {
    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @Override
    public PageWeb getByTreeId(Integer id) throws SQLException, IllegalAccessException {
        Page page = super.getById(id);

        if(page == null)
            return null;

        List<Title> titles = titleRepository.getAllByTreeParentId(page.getTreeId());
        List<Text> texts = textRepository.getAllByTreeParentId(page.getTreeId());
        List<MediaWeb> medias = mediaService.getAllMediaWebByTreeParentId(page.getTreeId());
        return new PageWeb(page, titles, texts, medias);
    }

    @Override
    public List<PageWeb> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
        List<Page> pages = pageRepository.getAllByTreeParentId(treeParentId);
        List<PageWeb> pageWebs = new ArrayList<>();

        for(Page page : pages)
            pageWebs.add(getByTreeId(page.getTreeId()));

        return pageWebs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager")
    public PageWeb save(PageWeb pageWeb, Integer parentId) throws SQLException, IllegalAccessException {
        pageRepository.save(pageWeb, parentId);

        titleRepository.merge(pageWeb.getTitles(), pageWeb.getTreeId());
        textRepository.merge(pageWeb.getTexts(), pageWeb.getTreeId());
        mediaRepository.mergeMediaList(pageWeb.getMedias(), pageWeb.getTreeId());

        terminalRepository.incrementTerminalVersion(
                treeRepository.getParentIdByChildId(pageWeb.getTreeId())
        );

        return pageWeb;
    }
}
