package com.java.training.application.service;

import com.java.training.application.connector.ConnectionPool;
import com.java.training.application.connector.DbService;
import com.java.training.application.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {

    private static UserService instance;

    private static final DbService DB_SERVICE = new DbService();
    private static final ConnectionPool CONNECTION_POOL = new ConnectionPool(5);
    private final static Logger logger = Logger.getLogger(DbService.class);

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public List<User> findAllUsers() throws SQLException {

        Connection connection = CONNECTION_POOL.getConnection();
        List<User> users = DB_SERVICE.sqlSelect(connection);
        logger.info("Close connection...");
        CONNECTION_POOL.returnConnection(connection);
        return users;
    }

    public void addUser(final User user) throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        DB_SERVICE.sqlInsert(connection, user);
        logger.info("Close connection...");
        CONNECTION_POOL.returnConnection(connection);
    }

    public void deleteUser(final long userId) throws SQLException {
        boolean deleted = false;
        Connection connection = CONNECTION_POOL.getConnection();
        DB_SERVICE.sqlDeleteById(connection, userId);
        logger.info("Close connection...");
        CONNECTION_POOL.returnConnection(connection);
    }

    public Optional<User> findUserByName() throws SQLException {
        return findAllUsers().stream().findFirst();
    }

}
