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
import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.repository.title.TitleRepository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class} )
public class TitleRepositoryTest {
    @Autowired
    private TitleRepository repository;

    @Test
    public void save() throws SQLException, IllegalAccessException {
        Title title = new Title();
        title.setTitle("new tile");

        repository.save(title, null);

        Title _title = repository.getById(title.getTreeId());
        Assert.assertNotNull(_title);

        Assert.assertTrue(EqualsBuilder.reflectionEquals(title, _title));

        repository.delete(title.getTreeId());

        _title = repository.getById(title.getTreeId());
        Assert.assertNull(_title);
    }
}
