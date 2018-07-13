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
import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.repository.media.MediaRepository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class} )
public class MediaRepositoryTest {
    @Autowired
    private MediaRepository repository;

    @Test
    public void save() throws SQLException, IllegalAccessException {
        Media media = new Media();
        media.setDeleted(0);
        media.setFileName("fileName");
        media.setOriginalFileName("originalFileName");
        media.setFileSize(1024);

        repository.save(media, null);

        Media _media = repository.getById(media.getTreeId());

        Assert.assertNotNull(_media);

        Assert.assertTrue(EqualsBuilder.reflectionEquals(media, _media));

        repository.delete(media.getTreeId());

        Assert.assertNull(repository.getById(media.getTreeId()));

        Arrays.asList(new Media(), new Media(), new Media()).forEach(el -> {
            try {
                repository.save(el, null);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

//        List<Media> medias = repository.getAll();
//        List<Media> _medias = repository.getByIds("1,54,55");

        System.out.println("test");

    }
}
