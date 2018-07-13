package repository;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.mkrf.label.config.SpringDBConfig;
import ru.mkrf.label.entity.db.Terminal;

import java.sql.SQLException;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class} )
public class TerminalRepository {
    @Autowired
    private ru.mkrf.label.repository.terminal.TerminalRepository repository;

    @Test
    public void save() throws SQLException, IllegalAccessException {
        Terminal terminal = new Terminal();
        terminal.setTemplateId(null);
        terminal.setTerminalVersion(0);
        terminal.setLastActiveTime(0);
        terminal.setDeleted(0);
        terminal.setVersion(0);
        terminal.setSortord(0);

        repository.save(terminal, null);

        Terminal _terminal = repository.getById(terminal.getTreeId());
        Assert.assertNotNull(_terminal);

        Assert.assertTrue(EqualsBuilder.reflectionEquals(terminal, _terminal));

        repository.delete(terminal.getTreeId());

        _terminal = repository.getById(terminal.getTreeId());
        Assert.assertNull(_terminal);
    }
}
