package com.java.training.application.service;

import com.java.training.application.connector.ConnectionPool;
import com.java.training.application.dao.Repository;
import com.java.training.application.model.Entity;
import com.java.training.application.model.User;
import com.java.training.application.userInput.InputList;
import com.java.training.application.userInput.InputString;
import com.java.training.application.userInput.impl.user.InputUserEmail;
import com.java.training.application.userInput.impl.user.InputUserFirstName;
import com.java.training.application.userInput.impl.user.InputUserLastName;
import com.java.training.application.userInput.impl.user.InputUserPhoneNumbers;
import com.java.training.application.userInput.impl.user.InputUserRole;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public enum UserService {

    INSTANCE;
    private static final Repository DB_SERVICE = new Repository();
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.INSTANCE;
    private static final Logger logger = Logger.getLogger(Repository.class);

    public List<User> findAll() throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        List<User> users = DB_SERVICE.sqlSelectUser(connection);
        logger.info("Close connection...");
        CONNECTION_POOL.returnConnection(connection);
        return users;
    }

    public void add() throws SQLException {
        final User user = initUser();
        Connection connection = CONNECTION_POOL.getConnection();
        List<String> phones = user.getMobileNumber();
        String query = "INSERT INTO user (id_user, user_role, first_name, last_name, email, phone_number) VALUES "
                + "(" + user.getId() + ", '" + user.getRole() + "', '" + user.getFirstName() + "', '"
                + user.getLastName() + "', '" + user.getEmail() + "', '" + phones.get(0) + "')";
        DB_SERVICE.sqlInsert(connection, query);
        logger.info("Close connection...");
        CONNECTION_POOL.returnConnection(connection);
    }

    public void delete(final long userId) throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        DB_SERVICE.sqlDeleteById(connection, userId, "user", "id_user");
        logger.info("Close connection...");
        CONNECTION_POOL.returnConnection(connection);
    }

    public Optional<User> findByName(long id) throws SQLException {
        return findAll().stream().filter(p -> p.getId() == id).findFirst();
    }

    private User initUser() {
        final InputString roleInput = new InputUserRole();
        final InputString firstNameInput = new InputUserFirstName();
        final InputString lastNameInput = new InputUserLastName();
        final InputString emailInput = new InputUserEmail();
        final InputList numbersInput = new InputUserPhoneNumbers();

        return new User(roleInput.inputString(),
                firstNameInput.inputString(),
                lastNameInput.inputString(),
                emailInput.inputString(),
                numbersInput.inputList());
    }

}
