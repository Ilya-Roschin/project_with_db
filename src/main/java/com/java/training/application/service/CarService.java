package com.java.training.application.service;

import com.java.training.application.connector.ConnectionPool;
import com.java.training.application.dao.UserRepository;
import com.java.training.application.model.Car;
import com.java.training.application.model.Entity;
import com.java.training.application.userInput.InputString;
import com.java.training.application.userInput.impl.car.InputCarColor;
import com.java.training.application.userInput.impl.car.InputCarName;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.java.training.application.util.Constant.MESSAGE_CLOSE_CONNECTION;

public enum CarService {

    INSTANCE;

    private static final UserRepository DB_SERVICE = new UserRepository();
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.INSTANCE;
    private final static Logger logger = Logger.getLogger(UserRepository.class);

    public List<Entity> findAll() throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        final List<Entity> cars = DB_SERVICE.findAll(connection, Car.class);
        logger.info(MESSAGE_CLOSE_CONNECTION);
        CONNECTION_POOL.returnConnection(connection);
        return cars;
    }

    public void add() throws SQLException {
        final Car car = initCar();
        Connection connection = CONNECTION_POOL.getConnection();
        String query = "INSERT INTO car (id_car, car_name, car_color) VALUES "
                + "(" + car.getId() + ", '" + car.getName() + "', '" + car.getColor() + "')";
        DB_SERVICE.insert(connection, query);
        logger.info(MESSAGE_CLOSE_CONNECTION);
        CONNECTION_POOL.returnConnection(connection);
    }

    public void delete(final long userId) throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        DB_SERVICE.DeleteById(connection, userId, "car", "id_car");
        logger.info(MESSAGE_CLOSE_CONNECTION);
        CONNECTION_POOL.returnConnection(connection);
    }

    public Optional<Entity> findByName(long id) throws SQLException {
        return findAll().stream().filter(Car.class::isInstance).filter(p -> p.getId() == id).findFirst();
    }

    private Car initCar() {
        final InputString inputCarName = new InputCarName();
        final InputString inputCarColor = new InputCarColor();

        return new Car(inputCarName.inputString(),
                inputCarColor.inputString());
    }
}
