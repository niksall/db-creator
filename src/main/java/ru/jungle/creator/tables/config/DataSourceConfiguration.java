package ru.jungle.creator.tables.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceProperties.driver());
        dataSource.setUrl(dataSourceProperties.url());
        dataSource.setUsername(dataSourceProperties.username());
        dataSource.setPassword(dataSourceProperties.password());
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
