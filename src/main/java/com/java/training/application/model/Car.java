package com.java.training.application.model;

import java.util.UUID;

public class Car implements Entity {

    private final long id;
    private final String name;
    private final String color;

    public Car(long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Car(String name, String color) {
        this.id = UUID.randomUUID().getMostSignificantBits();
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return "\n================\n" + "id: "
                + id + "\n" + "Name: " + name + "\n" + "Color: " + color + "\n";
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

}