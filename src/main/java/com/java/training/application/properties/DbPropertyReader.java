package com.java.training.application.properties;
// TODO: 13.11.2021 1) добавить класс DbProperty
// TODO: 13.11.2021 2) поля в lower case
// TODO: 13.11.2021 3) sql.password ... вывеси в интерфейс Constant
// TODO: 13.11.2021

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbPropertyReader {

    private String JDBC_DRIVER;
    private String DATABASE_URL;
    private String USER;
    private String PASSWORD;

    public DbPropertyReader() {
        readProperty();
    }

    private void readProperty() {
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            JDBC_DRIVER = properties.getProperty("sql.jdbc.driver");
            DATABASE_URL = properties.getProperty("sql.database.url");
            USER = properties.getProperty("sql.user");
            PASSWORD = properties.getProperty("sql.password");
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 13.11.2021 log.error
        }
    }

    public String getJdbcDriver() {
        return JDBC_DRIVER;
    }

    public String getDatabaseUrl() {
        return DATABASE_URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }
}
