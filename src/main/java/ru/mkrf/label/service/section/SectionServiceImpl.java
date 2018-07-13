package ru.mkrf.label.service.section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.mkrf.label.entity.db.*;
import ru.mkrf.label.entity.web.MediaWeb;
import ru.mkrf.label.entity.web.SectionWeb;
import ru.mkrf.label.repository.grid.GridRepository;
import ru.mkrf.label.repository.media.MediaRepository;
import ru.mkrf.label.repository.section.SectionRepository;
import ru.mkrf.label.repository.terminal.TerminalRepository;
import ru.mkrf.label.repository.title.TitleRepository;
import ru.mkrf.label.repository.tree.TreeRepository;
import ru.mkrf.label.repository.treemedia.TreeMediaRepository;
import ru.mkrf.label.service.AbstractBaseService;
import ru.mkrf.label.service.media.MediaService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SectionServiceImpl extends AbstractBaseService<Section> implements SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private GridRepository gridRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @Override
    public List<SectionWeb> getAllByTreeParentId(Integer treeParentId) throws SQLException, IllegalAccessException {
        List<Section> sections = sectionRepository.getAllByTreeParentId(treeParentId);
        List<SectionWeb> sectionWebs = new ArrayList<>();

        for(Section section : sections)
            sectionWebs.add(getBySectionId(section.getTreeId()));

        return sectionWebs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager")
    public SectionWeb save(SectionWeb sectionWeb, Integer treeParentId) throws SQLException, IllegalAccessException {
        sectionRepository.save(sectionWeb, treeParentId);
        titleRepository.merge(sectionWeb.getTitles(), sectionWeb.getTreeId());
        List<Grid> grids = Collections.singletonList(sectionWeb.getGrid());
        gridRepository.merge(grids, sectionWeb.getTreeId());
        sectionWeb.setGrid(CollectionUtils.isEmpty(grids) ? null : grids.get(0));
        mediaRepository.mergeMediaList(sectionWeb.getMedias(), sectionWeb.getTreeId());

        terminalRepository.incrementTerminalVersion(
                treeRepository.getParentIdByChildId(sectionWeb.getTreeId())
        );

        return sectionWeb;
    }

    @Override
    public SectionWeb getBySectionId(Integer sectionId) throws SQLException, IllegalAccessException {
        Section section = sectionRepository.getById(sectionId);

        if(section == null)
            return null;

        List<Title> titles = titleRepository.getAllByTreeParentId(sectionId);
        List<Grid> grids = gridRepository.getAllByTreeParentId(sectionId);
        Grid grid = !CollectionUtils.isEmpty(grids) ? grids.get(0) : null;
        List<MediaWeb> medias = mediaService.getAllMediaWebByTreeParentId(section.getTreeId());

        return new SectionWeb(section, grid, titles, medias);
    }
}
