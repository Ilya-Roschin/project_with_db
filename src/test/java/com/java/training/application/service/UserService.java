package com.java.training.application.service;

import com.java.training.application.connector.ConnectorJdbc;
import com.java.training.application.model.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class UserService {

    private static UserService instance;
    private static final ConnectorJdbc CONNECTOR_JDBC = new ConnectorJdbc();

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public List<User> findAllUsers() throws SQLException, ClassNotFoundException {
        Statement statement = CONNECTOR_JDBC.connectDataBase();
        return CONNECTOR_JDBC.sqlSelect(statement);
    }

    public void addUser(final User user) throws  SQLException, ClassNotFoundException {
        Statement statement = CONNECTOR_JDBC.connectDataBase();
        CONNECTOR_JDBC.sqlInsert(statement,user);
    }

    public void deleteUser(final long userId) throws  SQLException, ClassNotFoundException {
        boolean deleted = false;
        Statement statement = CONNECTOR_JDBC.connectDataBase();
        CONNECTOR_JDBC.sqlDeleteById(statement,userId);
    }
    public Optional<User> findUserByName() throws SQLException, ClassNotFoundException {
        return findAllUsers().stream().findFirst();
    }

}
