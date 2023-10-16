package ru.jungle.creator.tables.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "spring.datasource")
public record DataSourceProperties ( String driver,
                                     String url,
                                     String username,
                                     String password) {
}