package com.java.training.application.userInput.impl.user;

import com.java.training.application.reader.Reader;
import com.java.training.application.userInput.InputString;

public class InputUserLastName implements InputString {

    private static final Reader READER = Reader.getInstance();

    @Override
    public String inputString() {
        return READER.readLine("input last name: ");
    }
}
