package com.java.training.application.menu;

import com.java.training.application.model.User;
import com.java.training.application.reader.Reader;
import com.java.training.application.service.UserService;
import com.java.training.application.userInput.InputList;
import com.java.training.application.userInput.InputString;
import com.java.training.application.userInput.impl.user.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ServiceMenu {

    private static final Reader READER = Reader.getInstance();
    private static final UserService USER_SERVICE = UserService.INSTANCE;


    private static ServiceMenu instance;

    private ServiceMenu() {
    }

    public static ServiceMenu getInstance() {
        if (instance == null) {
            instance = new ServiceMenu();
        }
        return instance;
    }

    public void addUser() throws SQLException {// TODO: 13.11.2021 refactor
        final InputString roleInput = new InputUserRole();
        final InputString firstNameInput = new InputUserFirstName();
        final InputString lastNameInput = new InputUserLastName();
        final InputString emailInput = new InputUserEmail();
        final InputList numbersInput = new InputUserPhoneNumbers();

        USER_SERVICE.addUser(new User(roleInput.inputString(),
                firstNameInput.inputString(),
                lastNameInput.inputString(),
                emailInput.inputString(),
                numbersInput.inputList()));
    }

    public void findUser() throws SQLException {
        // TODO: 13.11.2021 refactor
        final String firstName = READER.readLine("Enter user name: ");
        final Optional<User> foundUser = USER_SERVICE.findUserByName();
        foundUser.ifPresent(System.out::println);
    }

    public void deleteUser() throws SQLException {
        final long userId = READER.readLong("Enter user ID: ");
        USER_SERVICE.deleteUser(userId);
    }

    public void findAllUsers() throws SQLException {
        final List<User> foundUsers = USER_SERVICE.findAllUsers();
        if (!foundUsers.isEmpty()) {
            foundUsers.forEach(System.out::println);
        } else {
            System.out.println("No users!");
        }
    }
}
