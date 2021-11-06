package com.java.training.application.connector;

import com.java.training.application.model.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectorJdbc {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/javashema";
    static final String USER = "root";
    static final String PASSWORD = "Password_123386_";

    public Statement connectDataBase() throws ClassNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;

        System.out.println("Registering JDBC driver...");

        Class.forName("com.mysql.cj.jdbc.Driver");

        System.out.println("Creating database connection...");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

        System.out.println("Executing statement...");

        return statement = connection.createStatement();
    }

    public List<User> sqlSelect(Statement statement) throws SQLException {
        final String sql = "SELECT * FROM user";

        ResultSet resultSet = statement.executeQuery(sql);

        System.out.println("Retrieving data from database...");
        System.out.println("\nuser:");

        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = initUser(resultSet);
            users.add(user);
        }

        System.out.println("Closing connection and releasing resources...");
        resultSet.close();
        System.out.println("Close statement...");
        statement.close();
        return users;
    }

    public void sqlDeleteById(Statement statement, long id) throws SQLException {
        final String sql = "DELETE FROM user WHERE iduser = " + id;
        System.out.println("Delete user from database...");
        statement.executeUpdate(sql);
        System.out.println("User " + "'" + id + "' deleted");
        System.out.println("Close statement...");
        statement.close();
    }

    public void sqlInsert(Statement statement, User user) throws SQLException {
        List<String> phones = user.getMobileNumber();
        String sql = "INSERT user (iduser, user_role, first_name, last_name, email, phone_number) VALUES "
                + "(" + user.getId() + ", '" + user.getRole() + "', '" + user.getFirstName() + "', '"
                + user.getLastName() + "', '" + user.getEmail() + "', '" + user.getEmail() + "', '"
                + phones.get(0) + "'";
        statement.executeUpdate(sql);
        System.out.println("Close statement...");
        statement.close();
    }

    private User initUser(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("iduser");
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
