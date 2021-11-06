package com.java.training.application.service;

import com.java.training.application.reader.Reader;
import com.java.training.application.validator.Validator;
import com.java.training.application.validator.impl.NumberValidator;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberService {

    private static final Validator NUMBER_VALIDATOR = new NumberValidator();
    private static final Reader READER = Reader.getInstance();
    private static PhoneNumberService instance;

    private PhoneNumberService() {
    }

    public static PhoneNumberService getInstance() {
        if (instance == null) {
            instance = new PhoneNumberService();
        }
        return instance;
    }

    public List<String> inputPhoneNumbers() {
        final List<String> numbers = new ArrayList<>();
        int amountOfNumbers = enterAmountOfPhoneNumbers();
        while (amountOfNumbers > 0) {
            String number = READER.readLine("Enter phone number: ");
            while (!NUMBER_VALIDATOR .validate(number)) {
                number = READER.readLine("invalid number. Try again:");
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
