package com.java.training.application.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbPropertyReader {

    private static String JDBC_DRIVER;
    private static String DATABASE_URL;
    private static String USER;
    private static String PASSWORD;

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
