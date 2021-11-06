package com.java.training.application.validator.impl;

import com.java.training.application.validator.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberValidator implements Validator {

    private static final String REGEX_FOR_PHONE_NUMBERS = "\\d{5}\\u0020\\d{7}";

    @Override
    public boolean validate(final String number) {
        final Pattern pattern = Pattern.compile(REGEX_FOR_PHONE_NUMBERS);
        final Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}

