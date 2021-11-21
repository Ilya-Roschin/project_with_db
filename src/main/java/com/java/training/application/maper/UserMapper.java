package com.java.training.application.maper;

import com.java.training.application.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public User mapTableToUser(ResultSet resultSet) throws SQLException {
        String phoneNumber = resultSet.getString("phone_number");
        List<String> phoneList = new ArrayList<>();
        phoneList.add(phoneNumber);
        return new User(resultSet.getLong("id_user"),
                resultSet.getString("user_role"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                phoneList);
    }
}
