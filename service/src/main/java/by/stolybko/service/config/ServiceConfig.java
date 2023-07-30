package by.stolybko.service.config;

import by.stolybko.database.config.DatabaseConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Import(DatabaseConfig.class)
@Configuration
@ComponentScan("by.stolybko.service")
@EnableTransactionManagement(proxyTargetClass = true)
public class ServiceConfig {

}
