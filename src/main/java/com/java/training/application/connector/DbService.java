package com.java.training.application.connector;

import com.java.training.application.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbService {

    private final static Logger logger = Logger.getLogger(ConnectorJdbc.class);

    public List<User> sqlSelect(Connection connection) throws SQLException {
        final String sql = "SELECT * FROM user";

        logger.info("Executing statement...");
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        logger.info("Retrieving data from database...");
        System.out.println("\nuser:");

        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = mapTableToUser(resultSet);
            users.add(user);
        }

        logger.info("Closing connection and releasing resources...");
        resultSet.close();
        logger.info("Close statement...");
        statement.close();
        logger.info("Close connection...");
        connection.close();
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
        logger.info("Close connection...");
        connection.close();
    }

    public void sqlInsert(Connection connection, User user) throws SQLException {
        List<String> phones = user.getMobileNumber();
        String sql = "INSERT INTO user (id_user, user_role, first_name, last_name, email, phone_number) VALUES "
                + "(" + user.getId() + ", '" + user.getRole() + "', '" + user.getFirstName() + "', '"
                + user.getLastName() + "', '" + user.getEmail() + "', '" + phones.get(0) + "')";
        PreparedStatement statement = connection.prepareStatement(sql);
        logger.info("Executing statement...");
        statement.executeUpdate(sql);
        logger.info("Close statement...");
        statement.close();
    }

    private User mapTableToUser(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id_user");
        String userRole = resultSet.getString("user_role");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phone_number");
        List<String> phoneList = new ArrayList<>();
        phoneList.add(phoneNumber);
        return new User(id, userRole, firstName, lastName, email, phoneList);
    }
}
