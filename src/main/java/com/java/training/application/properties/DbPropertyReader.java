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
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            return new DbProperty(properties.getProperty("sql.database.url"), properties.getProperty("sql.user"),
                    properties.getProperty("sql.password"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
        // TODO: 22.11.2021 исправить
    }




}
