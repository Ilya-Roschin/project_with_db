package com.java.training.application.connector;

import com.java.training.application.properties.DbPropertyReader;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

// TODO: 13.11.2021 auto increase 
// TODO: 13.11.2021 не использовать synchronized
public class ConnectionPool {

    private final static Logger logger = Logger.getLogger(ConnectionPool.class);
    private static final DbPropertyReader DB_PROPERTY_READER = new DbPropertyReader();
    private static final String JDBC_DRIVER = DB_PROPERTY_READER.getJdbcDriver();
    private static final String databaseUrl = DB_PROPERTY_READER.getDatabaseUrl();
    private static final String userName = DB_PROPERTY_READER.getUSER();
    private static final String password = DB_PROPERTY_READER.getPASSWORD();
    private static int maxPoolSize;
    private static int connNum = 0;

    private final Stack<Connection> freePool = new Stack<>(); // TODO: 13.11.2021 use arraydeque
    private final Set<Connection> occupiedPool = new HashSet<>();

    public ConnectionPool(int maxSize) {
        this.maxPoolSize = maxSize;
    }

    public synchronized Connection getConnection() throws SQLException {
        logger.info("Connections before get: " + (maxPoolSize - connNum));
        Connection connection = null;
        if (isFull()) {
            throw new SQLException("The connection pool is full.");
        }
        connection = getConnectionFromPool();
        if (connection == null) {
            connection = createNewConnectionForPool();
        }
        connection = makeAvailable(connection);
        autoChangeMaxPoolSize();
        logger.info("Connections after get: " + (maxPoolSize - connNum));
        return connection;
    }

    public synchronized void returnConnection(Connection connection) throws SQLException {
        logger.info("Connections before return: " + (maxPoolSize - connNum));
        if (connection == null) {
            throw new NullPointerException();
        }
        if (!occupiedPool.remove(connection)) {
            throw new SQLException("The connection is returned already or it isn't for this pool");
        }
        freePool.push(connection);
        autoChangeMaxPoolSize();
        logger.info("Connections after return: " + (maxPoolSize - connNum));
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

    private boolean isCapacityNeedIncrease() {
        return (double) connNum / maxPoolSize >= 0.8 && maxPoolSize < 29;
    }

    private boolean isCapacityNeeDecrease() {
        return (double) connNum / maxPoolSize <= 0.5 && maxPoolSize >= 12;
    }

    private void autoChangeMaxPoolSize() {
        if (isCapacityNeedIncrease()) {
            logger.info("Increase maxPoolSize...");
            maxPoolSize += 3;
        }

        if (isCapacityNeeDecrease()) {
            logger.info("Decrease maxPoolSize...");
            maxPoolSize += 3;
        }

    }
}
