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
import ru.mkrf.label.entity.db.Page;
import ru.mkrf.label.repository.page.PageRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class} )
public class PageRepositoryTest {
    @Autowired
    private PageRepository repository;

    @Test
    public void save() throws SQLException, IllegalAccessException {
        Page page = new Page();
        page.setContent("Content");
        page.setFullscreen(0);
        page.setDeleted(0);
        page.setSortord(0);

        repository.save(page, null);

        Page _page = repository.getById(page.getTreeId());
        Assert.assertNotNull(_page);

        Assert.assertTrue(EqualsBuilder.reflectionEquals(page, _page));

        repository.delete(page.getTreeId());
        _page = repository.getById(page.getTreeId());

        Assert.assertNull(_page);
    }
}
