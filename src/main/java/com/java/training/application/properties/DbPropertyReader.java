package com.java.training.application.properties;
// TODO: 13.11.2021 1) добавить класс DbProperty
// TODO: 13.11.2021 3) sql.password ... вывеси в интерфейс Constant
// TODO: 13.11.2021

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbPropertyReader {

    private String jdbcDriver;
    private String databaseUrl;
    private String user;
    private String password;

    public DbPropertyReader() {
        readProperty();
    }

    private void readProperty() {
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            jdbcDriver = properties.getProperty("sql.jdbc.driver");
            databaseUrl = properties.getProperty("sql.database.url");
            user = properties.getProperty("sql.user");
            password = properties.getProperty("sql.password");
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 13.11.2021 log.error
        }
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getUSER() {
        return user;
    }

    public String getPASSWORD() {
        return password;
    }
}
