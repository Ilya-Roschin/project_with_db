package com.java.training.application.service;

import com.java.training.application.connector.ConnectorJdbc;
import com.java.training.application.connector.DbService;
import com.java.training.application.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class UserService {

    private static UserService instance;
    private static final ConnectorJdbc CONNECTOR_JDBC = new ConnectorJdbc();
    private static final DbService DB_SERVICE = new DbService();

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public List<User> findAllUsers() throws SQLException, ClassNotFoundException {

        Connection connection = CONNECTOR_JDBC.connectDataBase();

        return DB_SERVICE.sqlSelect(connection);
    }

    public void addUser(final User user) throws SQLException, ClassNotFoundException {
        Connection connection = CONNECTOR_JDBC.connectDataBase();
        DB_SERVICE.sqlInsert(connection, user);
    }

    public void deleteUser(final long userId) throws SQLException, ClassNotFoundException {
        boolean deleted = false;
        Connection connection = CONNECTOR_JDBC.connectDataBase();
        DB_SERVICE.sqlDeleteById(connection, userId);
    }

    public Optional<User> findUserByName() throws SQLException, ClassNotFoundException {
        return findAllUsers().stream().findFirst();
    }

}
