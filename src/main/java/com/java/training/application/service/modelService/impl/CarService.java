package com.java.training.application.service.modelService.impl;

import com.java.training.application.connector.ConnectionPool;
import com.java.training.application.connector.DbService;
import com.java.training.application.model.Car;
import com.java.training.application.userInput.InputString;
import com.java.training.application.userInput.impl.car.InputCarColor;
import com.java.training.application.userInput.impl.car.InputCarName;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public enum CarService {

    INSTANCE;

    private static final DbService DB_SERVICE = new DbService();
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.INSTANCE;
    private final static Logger logger = Logger.getLogger(DbService.class);

    public List<Car> findAll() throws SQLException {
        Connection connection = CONNECTION_POOL.getConnection();
        List<Car> cars = DB_SERVICE.sqlSelectCar(connection);
        logger.info("Close connection...");
        CONNECTION_POOL.returnConnection(connection);
        return cars;
    }

    public void add() throws SQLException {
        final Car car = initCar();
        Connection connection = CONNECTION_POOL.getConnection();
        String query = "INSERT INTO car (id_car, car_name, car_color) VALUES "
                + "(" + car.getId() + ", '" + car.getName() + "', '" + car.getColor() + "')";
        DB_SERVICE.sqlInsert(connection, query);
        logger.info("Close connection...");
        CONNECTION_POOL.returnConnection(connection);
    }

    public void delete(final long userId) throws SQLException {
        boolean deleted = false;
        Connection connection = CONNECTION_POOL.getConnection();
        DB_SERVICE.sqlDeleteById(connection, userId, "car", "id_car");
        logger.info("Close connection...");
        CONNECTION_POOL.returnConnection(connection);
    }

    public Optional<Car> findByName(long id) throws SQLException {
        return findAll().stream().filter(p -> p.getId() == id).findFirst();
    }

    private Car initCar() {
        final InputString inputCarName = new InputCarName();
        final InputString inputCarColor = new InputCarColor();

        return new Car(inputCarName.inputString(),
                inputCarColor.inputString());
    }
}
