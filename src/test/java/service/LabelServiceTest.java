package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.mkrf.label.config.SpringDBConfig;
import ru.mkrf.label.config.SpringServiceConfig;
import ru.mkrf.label.entity.jsconfig.Content;
import ru.mkrf.label.service.labelservice.LabelService;

import java.sql.SQLException;
import java.util.List;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class, SpringServiceConfig.class} )
public class LabelServiceTest {
    @Autowired
    private LabelService labelService;

    @Test
    public void testRecursive() throws SQLException, IllegalAccessException {
        List<Content> result = labelService.getConfigContent(341);

        System.out.println(result);
    }
}
