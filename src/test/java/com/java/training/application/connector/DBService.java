package com.java.training.application.connector;

import com.java.training.application.model.User;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBService {

    final static Logger logger = Logger.getLogger(ConnectorJdbc.class);

    public List<User> sqlSelect(Statement statement) throws SQLException {
        final String sql = "SELECT * FROM user";

        ResultSet resultSet = statement.executeQuery(sql);

        logger.info("Retrieving data from database...");
        System.out.println("\nuser:");

        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = initUser(resultSet);
            users.add(user);
        }

        logger.info("Closing connection and releasing resources...");
        resultSet.close();
        logger.info("Close statement...");
        statement.close();
        return users;
    }

    public void sqlDeleteById(Statement statement, long id) throws SQLException {
        final String sql = "DELETE FROM user WHERE id_user = " + id;
        logger.info("Delete user from database...");
        statement.executeUpdate(sql);
        logger.info("User " + "'" + id + "' deleted");
        logger.info("Close statement...");
        statement.close();
    }

    public void sqlInsert(Statement statement, User user) throws SQLException {
        List<String> phones = user.getMobileNumber();
        String sql = "INSERT INTO user (id_user, user_role, first_name, last_name, email, phone_number) VALUES "
                + "(" + user.getId() + ", '" + user.getRole() + "', '" + user.getFirstName() + "', '"
                + user.getLastName() + "', '" + user.getEmail() + "', '" + phones.get(0) + "')";
        statement.executeUpdate(sql);
        logger.info("Close statement...");
        statement.close();
    }

    private User initUser(ResultSet resultSet) throws SQLException {
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
