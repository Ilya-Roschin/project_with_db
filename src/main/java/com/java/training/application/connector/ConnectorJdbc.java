package com.java.training.application.connector;

import com.java.training.application.properties.DbPropertyReader;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorJdbc {
    private static final DbPropertyReader DB_PROPERTY_READER = new DbPropertyReader();
    private static final String JDBC_DRIVER = DB_PROPERTY_READER.getJdbcDriver();
    private static final String DATABASE_URL = DB_PROPERTY_READER.getDatabaseUrl();
    private static final String USER = DB_PROPERTY_READER.getUSER();
    private static final String PASSWORD = DB_PROPERTY_READER.getPASSWORD();
    private static final Logger logger = Logger.getLogger(ConnectorJdbc.class);

    public Connection connectDataBase() throws ClassNotFoundException, SQLException {

        Connection connection = null;

        logger.info("Registering JDBC driver...");

        Class.forName("com.mysql.cj.jdbc.Driver");

        logger.info("Creating database connection...");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

        return connection;
    }
}
