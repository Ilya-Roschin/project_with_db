package com.java.training.application.userInput.impl.user;

import com.java.training.application.reader.Reader;
import com.java.training.application.userInput.InputString;

public class InputUserFirstName implements InputString {

    private static final Reader READER = Reader.getInstance();

    @Override
    public String inputString() {
        READER.readLine("");
        return READER.readLine("input first name: ");
    }
}
