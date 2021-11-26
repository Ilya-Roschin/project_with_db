package com.java.training.application.dao;

import com.java.training.application.maper.UserMapper;
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

public class UserRepository implements Repository {

    private static final Logger LOGGER = Logger.getLogger(UserRepository.class);
    private static final UserMapper USER_MAPPER = new UserMapper();

    @Override
    public List<Entity> findAll(Connection connection, Class<? extends Entity> clazz) throws SQLException {
        LOGGER.info(MESSAGE_EXECUTING_STATEMENT);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

        LOGGER.info("Retrieving data from database...");
        System.out.println("\nuser:");

        List<Entity> entities = new ArrayList<>();
        entities.add(USER_MAPPER.mapTableToUser(resultSet));

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
        LOGGER.info("Delete user from database...");
        statement.executeUpdate(sql);
        LOGGER.info("User " + "'" + id + "' deleted");
        LOGGER.info(MESSAGE_CLOSE_STATEMENT);
        statement.close();
    }

    @Override
    public void insert(Connection connection, String data) throws SQLException {
        String sql = "INSERT INTO user (id_user, user_role, first_name, last_name, email, phone_number) VALUES " + data;
        PreparedStatement statement = connection.prepareStatement(sql);
        LOGGER.info(MESSAGE_EXECUTING_STATEMENT);
        statement.executeUpdate(sql);
        statement.close();
    }
}
