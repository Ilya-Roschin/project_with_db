package com.java.training.application.dao;

import com.java.training.application.connector.ConnectionPool;
import com.java.training.application.maper.CarMapper;
import com.java.training.application.model.Car;
import com.java.training.application.model.Entity;
import org.apache.log4j.Logger;

import java.beans.Expression;
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

public class CarRepository implements Repository<Car> {

    private static final Logger LOGGER = Logger.getLogger(CarRepository.class);
    private static final CarMapper CAR_MAPPER = new CarMapper();
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.INSTANCE;

    @Override
    public List<Car> findAll() throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        LOGGER.info(MESSAGE_EXECUTING_STATEMENT);
        List<Car> cars;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM car");
            LOGGER.info("Retrieving data from database...");
            System.out.println("\nuser:");
            cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(CAR_MAPPER.mapTableToСar(resultSet));
            }
            resultSet.close();
            LOGGER.info(MESSAGE_CLOSE_STATEMENT);
        }
        LOGGER.info(MESSAGE_CLOSE_CONNECTION);
        CONNECTION_POOL.returnConnection(connection);
        return cars;
    }

    @Override
    public void deleteById(long id, String table, String column) throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        final String sql = "DELETE FROM " + table + " WHERE " + "id" + " = " + id; // TODO: 28.11.2021 ref id, delete parameter table
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            LOGGER.info(MESSAGE_EXECUTING_STATEMENT);
            LOGGER.info("Delete car from database...");
            statement.executeUpdate(sql);
            LOGGER.info("Car " + "'" + id + "' deleted");
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
            statement.close();
        }
        LOGGER.info(MESSAGE_CLOSE_CONNECTION);
        CONNECTION_POOL.returnConnection(connection);
    }

//    public <T extends Entity> T returnT(T t) {
////        PECS - producer extends consumer super
////               read             write            only
//        List<? extends Entity> producer = new ArrayList<>();
//        producer.add(null);
//        List<? super Entity> consumer = new ArrayList<>();
//        consumer.add(new Car("", ""));
////        -------------------------------------------------------
//        List<?> someList = new ArrayList<>();
//        someList.add(new Integer(5));
//        someList.add(null);
////        -------------------------------------------------------
//        final Integer integer = new Integer(1);
//        final Number number = integer;
//        // ковариантность типов
//
//        List<Integer> ints = new ArrayList<>();
//        List<? extends Number> nums = ints;
//        // инвариантны
//        return t;
//    }
}
