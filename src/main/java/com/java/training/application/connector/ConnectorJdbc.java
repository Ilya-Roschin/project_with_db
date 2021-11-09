package com.java.training.application.connector;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectorJdbc {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/javashema";
    private static final String USER = "root";
    private static final String PASSWORD = "Password_123386_";
    private static final Logger logger = Logger.getLogger(ConnectorJdbc.class);

    public Statement connectDataBase() throws ClassNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;

        logger.info("Registering JDBC driver...");

        Class.forName("com.mysql.cj.jdbc.Driver");

        logger.info("Creating database connection...");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

        logger.info("Executing statement...");
        statement = connection.createStatement();
        return statement;
    }
}
