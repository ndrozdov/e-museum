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
import ru.mkrf.label.entity.db.Grid;
import ru.mkrf.label.repository.grid.GridRepository;

import java.sql.SQLException;

@Rollback( false )
@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration( classes = {SpringDBConfig.class} )
public class GridRepositoryTest {
    @Autowired
    private GridRepository repository;

    @Test
    public void save() throws SQLException, IllegalAccessException {
        Grid grid = new Grid();
        grid.setCols(1);
        grid.setRows(2);

        repository.save(grid, null);

        Grid _grid = repository.getById(grid.getTreeId());
        Assert.assertNotNull(_grid);

        Assert.assertTrue(EqualsBuilder.reflectionEquals(grid, _grid));

        repository.delete(grid.getTreeId());

        _grid = repository.getById(grid.getTreeId());
        Assert.assertNull(_grid);
    }
}
