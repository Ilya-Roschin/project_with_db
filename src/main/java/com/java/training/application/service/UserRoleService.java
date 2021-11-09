package com.java.training.application.service;

import com.java.training.application.model.UserRole;
import com.java.training.application.reader.Reader;

public class UserRoleService {

    private static UserRoleService instance;
    private static final Reader READER = Reader.getInstance();

    private UserRoleService() {
    }

    public static UserRoleService getInstance() {
        if (instance == null) {
            instance = new UserRoleService();
        }
        return instance;
    }

    public void printRoleMenu() {
        System.out.println("1. USER" + "\n" + "2. ADMINISTRATOR");
    }

    public String choseRole() {
        while (true) {
            int userNumber = READER.readInt("Choose user role:");
            switch (userNumber) {
                case 1:
                    return UserRole.USER.name();
                case 2:
                    return UserRole.ADMINISTRATOR.name();
                default:
                    System.out.println("Incorrect input.");
            }
        }
    }
}
