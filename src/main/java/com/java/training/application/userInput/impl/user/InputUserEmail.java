package com.java.training.application.userInput.impl.user;

import com.java.training.application.reader.Reader;
import com.java.training.application.userInput.InputString;
import com.java.training.application.validator.Validator;
import com.java.training.application.validator.impl.EmailValidator;

public class InputUserEmail implements InputString {

    private static final Validator EMAIL_VALIDATOR = new EmailValidator();
    private static final Reader READER = Reader.getInstance();

    @Override
    public String inputString() {
        return inputEmail();
    }

    public String inputEmail() {
        String email = READER.readLine("input new Email: ");
        while (!EMAIL_VALIDATOR.validate(email)) {
            email = READER.readLine("invalid email. Enter email again:");
    }
    return email;
    }
}
