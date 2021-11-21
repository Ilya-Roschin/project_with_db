package com.java.training.application.userInput.impl.car;

import com.java.training.application.userInput.InputString;
import com.java.training.application.reader.Reader;

public class InputCarColor implements InputString {

    private static final Reader READER = Reader.getInstance();

    @Override
    public String inputString() {
        return READER.readLine("input car color: ");
    }
}
