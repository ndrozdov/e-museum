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
import ru.mkrf.label.entity.db.Grid;
import ru.mkrf.label.entity.db.Terminal;
import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.entity.web.TerminalWeb;
import ru.mkrf.label.service.terminal.TerminalService;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class, SpringServiceConfig.class, SpringMVCConfig.class} )
public class TerminalControllerTest {
    @Autowired
    private TerminalService terminalService;

    @Test
    public void testController() throws SQLException, IllegalAccessException {
        Terminal terminal = new Terminal(null, null, 0, 0, 0, 0, 0);
        Grid grid = new Grid(2, 3);
        List<Title> titles = Arrays.asList(new Title("titleOne"), new Title("titleTwo"));

        TerminalWeb terminalWeb = new TerminalWeb(terminal, grid, titles);
        terminalService.save(terminalWeb);
        TerminalWeb _terminalWeb = terminalService.getByTerminalId(terminalWeb.getTreeId());

        Assert.assertNotNull(_terminalWeb);
    }
}
