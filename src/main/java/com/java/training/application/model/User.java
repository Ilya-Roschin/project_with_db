package com.java.training.application.model;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.unmodifiableList;

public class User implements Entity {

    private final long id;
    private final String role;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final List<String> numbers;

    public User(final String role, final String firstName, final String lastName, final String email, final List<String> numbers) {
        this.id = UUID.randomUUID().getMostSignificantBits();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.numbers = numbers;
        this.role = role;
    }

    public User(final long id, final String role, final String firstName, final String lastName,
                final String email, final List<String> numbers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.numbers = numbers;
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuilder text = new StringBuilder("\n================\n" + "id: "
                + id + "\n" + "Role:" + role + "\n" + "First name: " + firstName + "\n"
                + "Last name: " + lastName +"\n" + "Email: " + email+ "\n" + "Phones: "
                + numbers +"\n");
        return text.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getMobileNumber() {
        return unmodifiableList(numbers);
    }

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
