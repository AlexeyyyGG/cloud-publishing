package config;

import java.sql.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import repository.DatabaseConnection;
import repository.EmployeeRepository;
import service.EmployeeService;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"config", "controller", "service", "repository"})
public class Config implements WebMvcConfigurer {
    @Bean
    public Connection getConnection() {
        return DatabaseConnection.getConnection();
    }

    @Bean
    public EmployeeRepository repository(Connection connection) {
        return new EmployeeRepository(connection);
    }

    @Bean
    public EmployeeService service(EmployeeRepository repository) {
        return new EmployeeService(repository);
    }
}