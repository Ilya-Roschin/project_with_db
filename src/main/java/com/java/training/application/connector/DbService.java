package com.java.training.application.connector;

import com.java.training.application.maper.impl.UserMapper;
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
    private static final UserMapper MAPPER = new UserMapper();

    public List<User> sqlSelect(Connection connection) throws SQLException {
        final String sql = "SELECT * FROM user";

        logger.info("Executing statement...");
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        logger.info("Retrieving data from database...");
        System.out.println("\nuser:");

        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = MAPPER.mapTableToUser(resultSet);
            users.add(user);
        }

        logger.info("Closing connection and releasing resources...");
        resultSet.close();
        logger.info("Close statement...");
        statement.close();
        return users;
    }

    public void sqlDeleteById(Connection connection, long id) throws SQLException {
        final String sql = "DELETE FROM user WHERE id_user = " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        logger.info("Executing statement...");
        logger.info("Delete user from database...");
        statement.executeUpdate(sql);
        logger.info("User " + "'" + id + "' deleted");
        logger.info("Close statement...");
        statement.close();
    }

    public void sqlInsert(Connection connection, User user) throws SQLException {
        List<String> phones = user.getMobileNumber();
        String sql = "INSERT INTO user (id_user, user_role, first_name, last_name, email, phone_number) VALUES "
                + "(" + user.getId() + ", '" + user.getRole() + "', '" + user.getFirstName() + "', '"
                + user.getLastName() + "', '" + user.getEmail() + "', '" + phones.get(0) + "')";
        PreparedStatement statement = connection.prepareStatement(sql);
        logger.info("Executing statement...");
        statement.executeUpdate(sql);
        statement.close();
    }

}
