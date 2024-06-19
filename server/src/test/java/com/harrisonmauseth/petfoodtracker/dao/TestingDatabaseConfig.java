package com.harrisonmauseth.petfoodtracker.dao;

import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Objects;

/**
 * To use an existing PostgreSQL database, set up the following environment variables, otherwise
 * the default values will be used. Default values operate under the assumption that the user
 * has not updated postgres default settings.
 */
@Configuration
public class TestingDatabaseConfig {

    private static final String DB_HOST =System.getenv("POSTGRES_HOST");
    private static final String DB_PORT =System.getenv("POSTGRES_PORT");
    private static final String DB_NAME = "PetFoodTracker-test";
    private static final String DB_USERNAME =System.getenv("POSTGRES_USER");
    private static final String DB_PASSWORD =System.getenv("POSTGRES_TOKEN");
    private static final String ADMIN_URL = System.getenv("POSTGRES_JDBC_ADMIN_URL");

    private SingleConnectionDataSource adminDataSource;
    private JdbcTemplate adminJdbcTemplate;

    private DataSource ds = null;

    private void setup() {
        adminDataSource = new SingleConnectionDataSource();
        adminDataSource.setUrl(ADMIN_URL);
        adminDataSource.setUsername(DB_USERNAME);
        adminDataSource.setPassword(DB_PASSWORD);
        adminJdbcTemplate = new JdbcTemplate(adminDataSource);
        adminJdbcTemplate.update("DROP DATABASE IF EXISTS \"" + DB_NAME + "\";");
        adminJdbcTemplate.update("CREATE DATABASE \"" + DB_NAME + "\";");
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        if (ds != null) return ds;
        setup();

        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setUrl(String.format("jdbc:postgresql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME));
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setAutoCommit(false);

        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("test-schema.sql"));
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("test-data.sql"));

        ds = dataSource;
        return ds;
    }

    @PreDestroy
    public void cleanup() throws SQLException {
        if (adminDataSource != null) {
            adminJdbcTemplate.update("DROP DATABASE \"" + DB_NAME + "\";");
            adminDataSource.getConnection().close();
            adminDataSource.destroy();
        }
    }
}
