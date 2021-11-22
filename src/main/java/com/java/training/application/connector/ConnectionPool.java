package com.java.training.application.connector;

import com.java.training.application.model.DbProperty;
import com.java.training.application.properties.DbPropertyReader;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.concurrent.locks.ReentrantLock;

public enum ConnectionPool {

    INSTANCE;
    
    private final DbProperty property;
    private int maxPoolSize = 8;
    private int connNum = 0;
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);
    private final DbPropertyReader reader = new DbPropertyReader();
    private static final ReentrantLock LOCKER = new ReentrantLock();
    
    private final ArrayDeque<Connection> freePool = new ArrayDeque<>();
    private final ArrayDeque<Connection> occupiedPool = new ArrayDeque<>();

    {
        property = reader.readProperty();
    }
    

    public Connection getConnection() throws SQLException {
        LOCKER.lock();
        logger.info("Connections before get: " + (maxPoolSize - occupiedPool.size()));
        Connection connection;
        if (isFull()) {
            throw new SQLException("The connection pool is full.");
        }
        connection = getConnectionFromPool();
        if (connection == null) {
            connection = createNewConnectionForPool();
        }
        connection = makeAvailable(connection);
        autoChangeMaxPoolSize();
        logger.info("Connections after get: " + (maxPoolSize - occupiedPool.size()));
        LOCKER.unlock();
        return connection;
    }

    public void returnConnection(Connection connection) throws SQLException {
        LOCKER.lock();
        logger.info("Connections before return: " + (maxPoolSize - occupiedPool.size()));
        if (connection == null) {
            throw new NullPointerException();
        }
        if (!occupiedPool.remove(connection)) {
            throw new SQLException("The connection is returned already or it isn't for this pool");
        }
        freePool.addLast(connection);
        autoChangeMaxPoolSize();
        logger.info("Connections after return: " + (maxPoolSize - occupiedPool.size()));
        LOCKER.unlock();
    }

    private boolean isFull() {
        return (freePool.isEmpty() && (connNum >= maxPoolSize));
    }

    private Connection createNewConnectionForPool() throws SQLException {
        Connection connection = createNewConnection();
        connNum++;
        occupiedPool.add(connection);
        return connection;
    }

    private Connection createNewConnection() throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection(property.getUrl(), property.getUser(), property.getPassword());
        return connection;
    }

    private Connection getConnectionFromPool() {
        Connection connection = null;
        if (!freePool.isEmpty()) {
            connection = freePool.removeLast();
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
        return (double) connNum / maxPoolSize >= 0.8 && maxPoolSize <= 28;
    }

    private boolean isCapacityNeeDecrease() {
        return (double) connNum / maxPoolSize <= 0.5 && maxPoolSize >= 12;
    }

    private void autoChangeMaxPoolSize() {
        if (isCapacityNeedIncrease()) {
            logger.info("Increase maxPoolSize...");
            maxPoolSize += 4;
        }

        if (isCapacityNeeDecrease()) {
            logger.info("Decrease maxPoolSize...");
            maxPoolSize -= 4;
        }

    }
}
