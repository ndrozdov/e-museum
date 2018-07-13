package ru.mkrf.label.service.terminal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.mkrf.label.entity.db.Grid;
import ru.mkrf.label.entity.db.Terminal;
import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.entity.web.TerminalWeb;
import ru.mkrf.label.repository.grid.GridRepository;
import ru.mkrf.label.repository.terminal.TerminalRepository;
import ru.mkrf.label.repository.title.TitleRepository;
import ru.mkrf.label.service.AbstractBaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TerminalServiceImpl extends AbstractBaseService<Terminal> implements TerminalService {
    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private GridRepository gridRepository;

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager")
    public TerminalWeb save(TerminalWeb terminalWeb) throws SQLException, IllegalAccessException {
        Terminal terminal = terminalRepository.save(terminalWeb, null);
        titleRepository.merge(terminalWeb.getTitles(), terminal.getTreeId());

        List<Grid> grids = Collections.singletonList(terminalWeb.getGrid());
        gridRepository.merge(grids, terminal.getTreeId());
        Grid grid = CollectionUtils.isEmpty(grids) ? null : grids.get(0);

        return new TerminalWeb(terminal, grid, terminalWeb.getTitles());
    }

    @Override
    public TerminalWeb getByTerminalId(Integer id) throws SQLException, IllegalAccessException {
        Terminal terminal = terminalRepository.getById(id);

        if(terminal != null) {
            List<Title> titles = titleRepository.getAllByTreeParentId(id);
            List<Grid> grids = gridRepository.getAllByTreeParentId(id);
            Grid grid = !CollectionUtils.isEmpty(grids) ? grids.get(0) : null;

            TerminalWeb terminalWeb = new TerminalWeb(terminal, grid, titles);

            return terminalWeb;
        }

        return null;
    }

    @Override
    public List<TerminalWeb> getAllTerminalWeb() throws SQLException, IllegalAccessException {
        List<Terminal> terminals = super.getAll();
        List<TerminalWeb> terminalWebs = new ArrayList<>();

        for(Terminal terminal : terminals)
            terminalWebs.add(getByTerminalId(terminal.getTreeId()));

        return terminalWebs;
    }

    @Override
    public void setTerminalVersion(Integer treeId, Integer terminalVersion) throws SQLException, IllegalAccessException {
        terminalRepository.setTerminalVersion(treeId, terminalVersion);
    }
}