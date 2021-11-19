package com.java.training.application.maper.impl;

import com.java.training.application.maper.Mapper;
import com.java.training.application.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements Mapper {

    // TODO: 19.11.2021 refactor
    public User mapTableToUser(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id_user");
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
