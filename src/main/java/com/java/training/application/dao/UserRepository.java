package com.java.training.application.dao;

import com.java.training.application.maper.CarMapper;
import com.java.training.application.maper.UserMapper;
import com.java.training.application.model.Car;
import com.java.training.application.model.Entity;
import com.java.training.application.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.java.training.application.util.Constant.MESSAGE_CLOSE_STATEMENT;
import static com.java.training.application.util.Constant.MESSAGE_EXECUTING_STATEMENT;

public class UserRepository implements Repository {

    private static final Logger logger = Logger.getLogger(UserRepository.class);
    private static final UserMapper USER_MAPPER = new UserMapper();
    private static final CarMapper CAR_MAPPER = new CarMapper();

    public List<Entity> findAll(Connection connection, Class<? extends Entity> clazz) throws SQLException {
        final StringBuilder sql = new StringBuilder("SELECT * FROM ");
        
        if (clazz == User.class) {
            sql.append("user");
        } else if (clazz == Car.class) {
            sql.append("cars");
        }

        logger.info(MESSAGE_EXECUTING_STATEMENT);
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
        logger.info(MESSAGE_CLOSE_STATEMENT);
        statement.close();
        return entities;
    }

    public void DeleteById(Connection connection, long id, String table, String column) throws SQLException {
        final String sql = "DELETE FROM " + table + " WHERE " + column + " = " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        logger.info(MESSAGE_EXECUTING_STATEMENT);
        logger.info("Delete user from database...");
        statement.executeUpdate(sql);
        logger.info("User " + "'" + id + "' deleted");
        logger.info(MESSAGE_CLOSE_STATEMENT);
        statement.close();
    }

    public void insert(Connection connection, String query) throws SQLException {
        String sql = query;
        PreparedStatement statement = connection.prepareStatement(sql);
        logger.info(MESSAGE_EXECUTING_STATEMENT);
        statement.executeUpdate(sql);
        statement.close();
    }


}
