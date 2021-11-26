package com.java.training.application.properties;

import com.java.training.application.model.DbProperty;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbPropertyReader {

    private final Logger logger = Logger.getLogger(this.getClass());

    public DbProperty readProperty() {
        Properties properties = new Properties();
        String url = "";
        String user = "";
        String password = "";
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(input);
            url = properties.getProperty("sql.database.url");
            user = properties.getProperty("sql.user");
            password = properties.getProperty("sql.password");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return new DbProperty(url, user, password);

    }


}
