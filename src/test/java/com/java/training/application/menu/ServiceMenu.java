package com.java.training.application.menu;

import com.java.training.application.model.User;
import com.java.training.application.reader.Reader;
import com.java.training.application.service.UserService;
import com.java.training.application.userInput.InputList;
import com.java.training.application.userInput.InputString;
import com.java.training.application.userInput.impl.InputEmail;
import com.java.training.application.userInput.impl.InputPhoneNumbers;
import com.java.training.application.userInput.impl.InputRole;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceMenu {

    private static final Reader READER = Reader.getInstance();
    private static final UserService USER_SERVICE = UserService.getInstance();
    private static ServiceMenu instance;

    private ServiceMenu() {
    }

    public static ServiceMenu getInstance() {
        if (instance == null) {
            instance = new ServiceMenu();
        }
        return instance;
    }

    public void addUser() throws SQLException, ClassNotFoundException {
        final InputString roleInput = new InputRole();
        final String role = roleInput.inputString();
        String firstName = READER.readLine("");
        firstName = READER.readLine("input first name: ");
        final String lastName = READER.readLine("input last name: ");
        final InputString emailInput = new InputEmail();
        final String email = emailInput.inputString();
        final InputList numbersInput = new InputPhoneNumbers();
        final List<String> numbers = numbersInput.inputList();
        final User user = new User(role, firstName, lastName, email, numbers);
        USER_SERVICE.addUser(user);
    }

    public void findUser() throws SQLException, ClassNotFoundException {
        final String firstName = READER.readLine("Enter user name: ");
        final Optional<User> foundUser = USER_SERVICE.findUserByName();
        foundUser.ifPresent(System.out::println);
    }

    public void deleteUser() throws SQLException, ClassNotFoundException {
        final long userId = READER.readLong("Enter user ID: ");
        USER_SERVICE.deleteUser(userId);
    }

    public void findAllUsers() throws SQLException, ClassNotFoundException {
        final List<User> foundUsers = USER_SERVICE.findAllUsers();
        foundUsers.stream().collect(Collectors.groupingBy(User::getRole));
        if (!foundUsers.isEmpty()) {
            foundUsers.forEach(System.out::println);
        } else {
            System.out.println("No users!");
        }
    }
}