package ru.mkrf.label.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.mkrf.label.util.configuration.LabelConfig;
import ru.mkrf.label.util.parser.Parser;
import ru.mkrf.label.util.parser.ParserJAXBImpl;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@ComponentScan({"ru.mkrf.label.repository"})
@EnableTransactionManagement
public class SpringDBConfig {
    @Bean("config")
    public LabelConfig readConfigFile() {
        try {
            File file = new File("/var/kiosk/config.xml");
            Parser parser = new ParserJAXBImpl();
            LabelConfig conf = (LabelConfig) parser.getObject(file, LabelConfig.class);
            return conf;
        } catch (JAXBException e) {
            Logger.getLogger(SpringServiceConfig.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    @Bean
    public DataSource getDataSource() {
        LabelConfig config = readConfigFile();
        String url = "jdbc:sqlite:" + config.getPathToDB();
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setInitSQL( "PRAGMA FOREIGN_KEYS = ON;" );
        dataSource.setMaxActive(1);

        dataSource.setUrl(url);

        return dataSource;
    }

    @Bean("namedPreparedStatement")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(getDataSource());
    }

    @Bean("transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }
}
