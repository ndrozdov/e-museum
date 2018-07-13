package ru.mkrf.label.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.mkrf.label.entity.db.User;

import java.util.Hashtable;
import java.util.Map;

@Configuration
@ComponentScan({"ru.mkrf.label.service"})
public class SpringServiceConfig {

    @Bean("users")
    public Map<String, User> getLoginUsersMap() {
        return new Hashtable<>();
    }
}
