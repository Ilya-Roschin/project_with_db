package com.java.training.application.service;

import com.java.training.application.connector.ConnectionPool;
import com.java.training.application.dao.UserRepository;
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

import static com.java.training.application.util.Constant.MESSAGE_CLOSE_CONNECTION;

public enum UserService {

    INSTANCE;
    private static final UserRepository DB_SERVICE = new UserRepository();
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.INSTANCE;
    private static final Logger logger = Logger.getLogger(UserRepository.class);

    public List<? extends Entity> findAll() throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        final List<? extends Entity> users = DB_SERVICE.findAll(connection, User.class);
        logger.info(MESSAGE_CLOSE_CONNECTION);
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
        DB_SERVICE.insert(connection, query);
        logger.info(MESSAGE_CLOSE_CONNECTION);
        CONNECTION_POOL.returnConnection(connection);
    }

    public void delete(final long userId) throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        DB_SERVICE.DeleteById(connection, userId, "user", "id_user");
        logger.info(MESSAGE_CLOSE_CONNECTION);
        CONNECTION_POOL.returnConnection(connection);
    }

    public Optional<? extends Entity> findByName(long id) throws SQLException {
        return findAll().stream().filter(User.class::isInstance).filter(p -> p.getId() == id).findFirst();
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
