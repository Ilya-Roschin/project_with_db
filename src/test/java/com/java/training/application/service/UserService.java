package com.java.training.application.service;

import com.java.training.application.connector.ConnectorJdbc;
import com.java.training.application.connector.DBService;
import com.java.training.application.model.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class UserService {

    private static UserService instance;
    private static final ConnectorJdbc CONNECTOR_JDBC = new ConnectorJdbc();
    private static final DBService DB_SERVICE = new DBService();

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
        return DB_SERVICE.sqlSelect(statement);
    }

    public void addUser(final User user) throws  SQLException, ClassNotFoundException {
        Statement statement = CONNECTOR_JDBC.connectDataBase();
        DB_SERVICE.sqlInsert(statement,user);
    }

    public void deleteUser(final long userId) throws  SQLException, ClassNotFoundException {
        boolean deleted = false;
        Statement statement = CONNECTOR_JDBC.connectDataBase();
        DB_SERVICE.sqlDeleteById(statement,userId);
    }
    public Optional<User> findUserByName() throws SQLException, ClassNotFoundException {
        return findAllUsers().stream().findFirst();
    }

}
