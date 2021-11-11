package com.java.training.application.connector;


import com.java.training.application.properties.DbPropertyReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ConnectionPool {


    private static final DbPropertyReader DB_PROPERTY_READER = new DbPropertyReader();
    private static final String JDBC_DRIVER = DB_PROPERTY_READER.getJdbcDriver();
    private static final String databaseUrl = DB_PROPERTY_READER.getDatabaseUrl();
    private static final String userName = DB_PROPERTY_READER.getUSER();
    private static final String password = DB_PROPERTY_READER.getPASSWORD();
    private static int maxPoolSize;
    private static int connNum = 0;

    Stack<Connection> freePool = new Stack<>();
    Set<Connection> occupiedPool = new HashSet<>();

    public ConnectionPool(int maxSize) {
        this.maxPoolSize = maxSize;
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection connection = null;
        if (isFull()) {
            throw new SQLException("The connection pool is full.");
        }
        connection = getConnectionFromPool();
        if (connection == null) {
            connection = createNewConnectionForPool();
        }
        connection = makeAvailable(connection);
        return connection;
    }

    public synchronized void returnConnection(Connection connection) throws SQLException {
        if (connection == null) {
            throw new NullPointerException();
        }
        if (!occupiedPool.remove(connection)) {
            throw new SQLException("The connection is returned already or it isn't for this pool");
        }
        freePool.push(connection);
    }

    private synchronized boolean isFull() {
        return ((freePool.size() == 0) && (connNum >= maxPoolSize));
    }

    private Connection createNewConnectionForPool() throws SQLException {
        Connection connection = createNewConnection();
        connNum++;
        occupiedPool.add(connection);
        return connection;
    }

    private Connection createNewConnection() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection(databaseUrl, userName, password);
        return connection;
    }

    private Connection getConnectionFromPool() {
        Connection connection = null;
        if (freePool.size() > 0) {
            connection = freePool.pop();
            occupiedPool.add(connection);
        }
        return connection;
    }

    private Connection makeAvailable(Connection connection) throws SQLException {
        if (isConnectionAvailable(connection)) {
            return connection;
        }
        occupiedPool.remove(connection);
        connNum--;
        connection.close();

        connection = createNewConnection();
        occupiedPool.add(connection);
        connNum++;
        return connection;
    }

    private boolean isConnectionAvailable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

}
