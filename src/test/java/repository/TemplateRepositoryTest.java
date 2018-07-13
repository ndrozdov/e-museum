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
import ru.mkrf.label.entity.db.Template;
import ru.mkrf.label.repository.template.TemplateRepository;

import java.sql.SQLException;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class} )
public class TemplateRepositoryTest {
    @Autowired
    private TemplateRepository repository;

    @Test
    public void save() throws SQLException, IllegalAccessException {
        Template template = new Template();
        template.setDeleted(0);
        template.setSortord(0);

        repository.save(template, null);

        Template _template = repository.getById(template.getTreeId());

        Assert.assertNotNull(_template);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(template, _template));

        repository.delete(template.getTreeId());

        _template = repository.getById(template.getTreeId());

        Assert.assertNull(_template);
    }
}
