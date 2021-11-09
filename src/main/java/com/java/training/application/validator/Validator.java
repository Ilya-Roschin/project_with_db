package com.java.training.application.validator;

@FunctionalInterface
public interface Validator {

    boolean validate(final String input);
}
