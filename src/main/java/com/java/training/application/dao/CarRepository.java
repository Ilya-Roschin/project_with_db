package com.java.training.application.dao;

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

import static com.java.training.application.util.Constant.MESSAGE_CLOSE_STATEMENT;
import static com.java.training.application.util.Constant.MESSAGE_EXECUTING_STATEMENT;

public class CarRepository implements Repository {

    private static final Logger LOGGER = Logger.getLogger(CarRepository.class);
    private static final CarMapper CAR_MAPPER = new CarMapper();

    @Override
    public List<Entity> findAll(Connection connection, Class<? extends Entity> clazz) throws SQLException {
        LOGGER.info(MESSAGE_EXECUTING_STATEMENT);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM car");

        LOGGER.info("Retrieving data from database...");
        System.out.println("\nuser:");

        List<Entity> entities = new ArrayList<>();
        entities.add(CAR_MAPPER.mapTableToСar(resultSet));
        resultSet.close();

        LOGGER.info(MESSAGE_CLOSE_STATEMENT);
        statement.close();
        return entities;
    }

    @Override
    public void DeleteById(Connection connection, long id, String table, String column) throws SQLException {
        final String sql = "DELETE FROM " + table + " WHERE " + column + " = " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        LOGGER.info(MESSAGE_EXECUTING_STATEMENT);
        LOGGER.info("Delete car from database...");
        statement.executeUpdate(sql);
        LOGGER.info("User " + "'" + id + "' deleted");
        LOGGER.info(MESSAGE_CLOSE_STATEMENT);
        statement.close();
    }

    @Override
    public void insert(Connection connection, String data) throws SQLException {
        String sql = "INSERT INTO car (id_car, car_name, car_color) VALUES " + data;
        PreparedStatement statement = connection.prepareStatement(sql);
        LOGGER.info(MESSAGE_EXECUTING_STATEMENT);
        statement.executeUpdate(sql);
        LOGGER.info(MESSAGE_CLOSE_STATEMENT);
        statement.close();
    }
}
