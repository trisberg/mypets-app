package com.springdeveloper.mypets;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("refresh")
@PropertySource({"file:config/application-refresh.yaml"})
public class RefreshConfiguration {

    @Bean
    @RefreshScope
    @ConfigurationProperties(prefix="spring.datasource.hikari")
    public DataSource refreshableDataSource() {
        return refreshableDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    @RefreshScope
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSourceProperties refreshableDataSourceProperties() {
        return new DataSourceProperties();
    }

}
