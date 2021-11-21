package com.java.training.application.connector;

import com.java.training.application.maper.CarMapper;
import com.java.training.application.maper.UserMapper;
import com.java.training.application.model.Car;
import com.java.training.application.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbService {

    private final static Logger logger = Logger.getLogger(DbService.class);
    private static final UserMapper USER_MAPPER = new UserMapper();
    private static final CarMapper CAR_MAPPER = new CarMapper();

    public List<User> sqlSelectUser(Connection connection) throws SQLException {
        final String sql = "SELECT * FROM user";

        logger.info("Executing statement...");
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        logger.info("Retrieving data from database...");
        System.out.println("\nuser:");

        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(USER_MAPPER.mapTableToUser(resultSet));
        }
        resultSet.close();
        logger.info("Close statement...");
        statement.close();
        return users;
    }

    public List<Car> sqlSelectCar(Connection connection) throws SQLException {
        final String sql = "SELECT * FROM car";

        logger.info("Executing statement...");
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        logger.info("Retrieving data from database...");
        System.out.println("\ncar:");

        List<Car> cars = new ArrayList<>();
        while (resultSet.next()) {
            cars.add(CAR_MAPPER.mapTableToСar(resultSet));
        }
        resultSet.close();
        logger.info("Close statement...");
        statement.close();
        return cars;
    }

    public void sqlDeleteById(Connection connection, long id, String table, String column) throws SQLException {
        final String sql = "DELETE FROM " + table + " WHERE " + column + " = " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        logger.info("Executing statement...");
        logger.info("Delete user from database...");
        statement.executeUpdate(sql);
        logger.info("User " + "'" + id + "' deleted");
        logger.info("Close statement...");
        statement.close();
    }

    public void sqlInsert(Connection connection, String query) throws SQLException {
        String sql = query;
        PreparedStatement statement = connection.prepareStatement(sql);
        logger.info("Executing statement...");
        statement.executeUpdate(sql);
        statement.close();
    }

}
