package com.java.training.application.maper;

import com.java.training.application.model.Car;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper {

    public Car mapTableToСar(ResultSet resultSet) throws SQLException {
        return new Car(resultSet.getLong("id_car"),
                resultSet.getString("car_name"),
                resultSet.getString("car_color"));
    }
}
