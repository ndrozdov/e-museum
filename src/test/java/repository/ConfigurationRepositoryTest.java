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
import ru.mkrf.label.entity.db.Configuration;
import ru.mkrf.label.repository.configuration.ConfigurationRepository;

import java.sql.SQLException;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class} )
public class ConfigurationRepositoryTest {
    @Autowired
    private ConfigurationRepository repository;

    @Test
    public void save() throws SQLException, IllegalAccessException {
        Configuration configuration = new Configuration("name", "value");

        configuration = repository.save(configuration);
        Configuration _configuration = repository.getById(configuration.getId());
        Assert.assertNotNull(_configuration);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(configuration, _configuration));

        _configuration.setValue("new Value");
        _configuration.setName("new Name");

        repository.save(_configuration);
        configuration = repository.getById(_configuration.getId());
        Assert.assertNotNull(configuration);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(configuration, _configuration));

        repository.delete(_configuration.getId());
        _configuration = repository.getById(_configuration.getId());

        Assert.assertNull(_configuration);
    }
}
