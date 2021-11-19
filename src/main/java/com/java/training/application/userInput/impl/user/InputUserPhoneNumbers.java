package com.java.training.application.userInput.impl.user;

import com.java.training.application.reader.Reader;
import com.java.training.application.userInput.InputList;
import com.java.training.application.validator.Validator;
import com.java.training.application.validator.impl.NumberValidator;

import java.util.ArrayList;
import java.util.List;

public class InputUserPhoneNumbers implements InputList {

    private static final Validator NUMBER_VALIDATOR = new NumberValidator();
    private static final Reader READER = Reader.getInstance();

    @Override
    public List<String> inputList() {
        return inputPhoneNumbers();
    }

    public List<String> inputPhoneNumbers() {
        final List<String> numbers = new ArrayList<>();
        int amountOfNumbers = enterAmountOfPhoneNumbers();
        while (amountOfNumbers > 0) {
            String number = READER.readLine("");
            number = READER.readLine("Enter phone number: ");
            while (!NUMBER_VALIDATOR .validate(number)) {
                number = READER.readLine("invalid phone number. Try again:");
            }
            numbers.add(number);
            amountOfNumbers--;
        }
        return numbers;
    }

    private int enterAmountOfPhoneNumbers() {
        while (true) {
           final int amountOfNumbers = READER.readInt("how many phone numbers do you want to enter: ");
            if (amountOfNumbers <= 0 || amountOfNumbers > 3) {
                System.out.println("invalid number. Try again. ");
            } else {
                return amountOfNumbers;
            }
        }
    }


}
