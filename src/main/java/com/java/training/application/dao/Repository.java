package com.java.training.application.dao;

import com.java.training.application.maper.CarMapper;
import com.java.training.application.maper.UserMapper;
import com.java.training.application.model.Car;
import com.java.training.application.model.Entity;
import com.java.training.application.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static final Logger logger = Logger.getLogger(Repository.class);
    private static final UserMapper USER_MAPPER = new UserMapper();
    private static final CarMapper CAR_MAPPER = new CarMapper();

    public List<Entity> findAll(Connection connection, Class<? extends Entity> clazz) throws SQLException {
        final StringBuilder sql = new StringBuilder("SELECT * FROM ");
        
        if (clazz == User.class) {
            sql.append("user");
        } else if (clazz == Car.class) {
            sql.append("cars");
        }

        logger.info("Executing statement...");
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql.toString());

        logger.info("Retrieving data from database...");
        System.out.println("\nuser:");

        List<Entity> entities = new ArrayList<>();

        while (resultSet.next()) {
            if (clazz == User.class) {
                entities.add(USER_MAPPER.mapTableToUser(resultSet));
            } else if (clazz == Car.class) {
                entities.add(CAR_MAPPER.mapTableToСar(resultSet));
            }
        }
        resultSet.close();
        logger.info("Close statement...");
        statement.close();
        return entities;
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
