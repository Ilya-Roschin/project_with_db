package com.java.training.application.maper;

import com.java.training.application.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

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
