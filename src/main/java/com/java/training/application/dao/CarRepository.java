package com.java.training.application.dao;

import com.java.training.application.connector.ConnectionPool;
import com.java.training.application.maper.CarMapper;
import com.java.training.application.model.Entity;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.java.training.application.util.Constant.MESSAGE_CLOSE_CONNECTION;
import static com.java.training.application.util.Constant.MESSAGE_CLOSE_STATEMENT;
import static com.java.training.application.util.Constant.MESSAGE_EXECUTING_STATEMENT;

public class CarRepository implements Repository {

    private static final Logger LOGGER = Logger.getLogger(CarRepository.class);
    private static final CarMapper CAR_MAPPER = new CarMapper();
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.INSTANCE;

    @Override
    public List<Entity> findAll(Class<? extends Entity> clazz) throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        LOGGER.info(MESSAGE_EXECUTING_STATEMENT);
        List<Entity> entities;
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM car");

            LOGGER.info("Retrieving data from database...");
            System.out.println("\nuser:");

            entities = new ArrayList<>();
            entities.add(CAR_MAPPER.mapTableToСar(resultSet));
            resultSet.close();

            LOGGER.info(MESSAGE_CLOSE_STATEMENT);
        }
        LOGGER.info(MESSAGE_CLOSE_CONNECTION);
        CONNECTION_POOL.returnConnection(connection);
        return entities;
    }

    @Override
    public void deleteById(long id, String table, String column) throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        final String sql = "DELETE FROM " + table + " WHERE " + column + " = " + id;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            LOGGER.info(MESSAGE_EXECUTING_STATEMENT);
            LOGGER.info("Delete car from database...");
            statement.executeUpdate(sql);
            LOGGER.info("User " + "'" + id + "' deleted");
            LOGGER.info(MESSAGE_CLOSE_STATEMENT);
        }
        LOGGER.info(MESSAGE_CLOSE_CONNECTION);
        CONNECTION_POOL.returnConnection(connection);
    }

    @Override
    public void insert(String data) throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        String sql = "INSERT INTO car (id_car, car_name, car_color) VALUES " + data;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            LOGGER.info(MESSAGE_EXECUTING_STATEMENT);
            statement.executeUpdate(sql);
            LOGGER.info(MESSAGE_CLOSE_STATEMENT);
        }
        LOGGER.info(MESSAGE_CLOSE_CONNECTION);
        CONNECTION_POOL.returnConnection(connection);
    }
}
