package config;

import java.sql.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import repository.DatabaseConnection;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"config", "controller", "service", "repository"})
@PropertySource("classpath:application.properties")
public class Config implements WebMvcConfigurer {
    private final DbProperties dbProperties;

    @Autowired
    public Config(DbProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Connection getConnection() {
        return DatabaseConnection.getConnection(
                dbProperties.getUrl(),
                dbProperties.getUser(),
                dbProperties.getPassword()
        );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("/swagger-ui/");
    }
}