package com.java.training.application.menu;

import com.java.training.application.model.Entity;
import com.java.training.application.reader.Reader;
import com.java.training.application.service.CarService;
import com.java.training.application.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.java.training.application.util.Constant.MESSAGE_ENTER_CAR_ID;
import static com.java.training.application.util.Constant.MESSAGE_ENTER_USER_ID;
import static com.java.training.application.util.Constant.MESSAGE_NO_CARS;
import static com.java.training.application.util.Constant.MESSAGE_NO_USERS;

public class ServiceMenu {

    private static final Reader READER = Reader.getInstance();
    private static final UserService USER_SERVICE = UserService.INSTANCE;
    private static final CarService CAR_SERVICE = CarService.INSTANCE;


    private static ServiceMenu instance;

    private ServiceMenu() {
    }

    public static ServiceMenu getInstance() {
        if (instance == null) {
            instance = new ServiceMenu();
        }
        return instance;
    }

    public void addUser() throws SQLException {
        USER_SERVICE.add();
    }

    public void findUser() throws SQLException {
        final long userId = READER.readLong(MESSAGE_ENTER_USER_ID);
        final Optional<? extends Entity> foundUser = USER_SERVICE.findByName(userId);
        foundUser.ifPresent(System.out::println);
    }

    public void deleteUser() throws SQLException {
        final long userId = READER.readLong(MESSAGE_ENTER_USER_ID);
        USER_SERVICE.delete(userId);
    }

    public void findAllUsers() throws SQLException {
        final List<? extends Entity> foundUsers = USER_SERVICE.findAll();
        if (!foundUsers.isEmpty()) {
            foundUsers.forEach(System.out::println);
        } else {
            System.out.println(MESSAGE_NO_USERS);
        }
    }

    public void addCar() throws SQLException {
        CAR_SERVICE.add();
    }

    public void findCar() throws SQLException {
        final long id = READER.readLong(MESSAGE_ENTER_CAR_ID);
        final Optional<? extends Entity> foundCar = CAR_SERVICE.findByName(id);
        foundCar.ifPresent(System.out::println);
    }

    public void deleteCar() throws SQLException {
        final long carId = READER.readLong(MESSAGE_ENTER_CAR_ID);
        CAR_SERVICE.delete(carId);
    }

    public void findAllCars() throws SQLException {
        final List<? extends Entity> foundCars = CAR_SERVICE.findAll();
        if (!foundCars.isEmpty()) {
            foundCars.forEach(System.out::println);
        } else {
            System.out.println(MESSAGE_NO_CARS);
        }
    }


}
