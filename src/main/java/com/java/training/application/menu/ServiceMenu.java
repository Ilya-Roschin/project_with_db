package com.java.training.application.menu;

import com.java.training.application.model.Car;
import com.java.training.application.model.User;
import com.java.training.application.reader.Reader;
import com.java.training.application.service.modelService.impl.CarService;
import com.java.training.application.service.modelService.impl.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
        final long userId = READER.readLong("Enter user id: ");
        final Optional<User> foundUser = USER_SERVICE.findByName(userId);
        foundUser.ifPresent(System.out::println);
    }

    public void deleteUser() throws SQLException {
        final long userId = READER.readLong("Enter user ID: ");
        USER_SERVICE.delete(userId);
    }

    public void findAllUsers() throws SQLException {
        final List<User> foundUsers = USER_SERVICE.findAll();
        if (!foundUsers.isEmpty()) {
            foundUsers.forEach(System.out::println);
        } else {
            System.out.println("No users!");
        }
    }

    public void addCar() throws SQLException {
        CAR_SERVICE.add();
    }

    public void findCar() throws SQLException {
        final long id = READER.readLong("Enter car id: ");
        final Optional<Car> foundCar = CAR_SERVICE.findByName(id);
        foundCar.ifPresent(System.out::println);
    }

    public void deleteCar() throws SQLException {
        final long carId = READER.readLong("Enter car ID: ");
        CAR_SERVICE.delete(carId);
    }

    public void findAllCars() throws SQLException {
        final List<Car> foundCars = CAR_SERVICE.findAll();
        if (!foundCars.isEmpty()) {
            foundCars.forEach(System.out::println);
        } else {
            System.out.println("No cars!");
        }
    }


}
