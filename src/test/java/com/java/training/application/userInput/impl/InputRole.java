package com.java.training.application.userInput.impl;

import com.java.training.application.reader.Reader;
import com.java.training.application.service.UserRoleService;
import com.java.training.application.userInput.InputString;

public class InputRole implements InputString {

    private static final Reader READER = Reader.getInstance();
    private static final UserRoleService USER_ROLE_SERVICE = UserRoleService.getInstance();

    @Override
    public String inputString() {
        return inputRole();
    }

    private String inputRole() {
        USER_ROLE_SERVICE.printRoleMenu();
        return USER_ROLE_SERVICE.choseRole();
    }
}
