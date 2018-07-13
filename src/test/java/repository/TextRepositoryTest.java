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
import ru.mkrf.label.entity.db.Text;
import ru.mkrf.label.repository.text.TextRepository;

import java.sql.SQLException;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class} )
public class TextRepositoryTest {
    @Autowired
    private TextRepository textRepository;

    @Test
    public void save() throws SQLException, IllegalAccessException {
        Text text = new Text();
        text.setText("new Text");

        textRepository.save(text, null);

        Text _text = textRepository.getById(text.getTreeId());

        Assert.assertNotNull(_text);

        Assert.assertTrue(EqualsBuilder.reflectionEquals(text, _text));

        textRepository.delete(text.getTreeId());

        _text = textRepository.getById(text.getTreeId());

        Assert.assertNull(_text);
    }
}
