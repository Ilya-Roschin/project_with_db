package com.java.training.application.menu;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    private static final ServiceMenu SERVICE_MENU = ServiceMenu.getInstance();

    public static void run() {
        final Menu menu = new Menu();
        try {
            while (true) {

                menu.printMenu();
                final int choice = menu.readMenu();
                menu.makeChoice(choice);

            }
        } catch (final Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. add new string to table");
        System.out.println("2. find by name");
        System.out.println("3. delete by name");
        System.out.println("4. find all");
        System.out.println("0. exit");
    }

    public int readMenu() {
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void makeChoice(final int choice) throws SQLException {
        switch (choice) {
            case 1:
                SERVICE_MENU.addUser();
                break;
            case 2:
                SERVICE_MENU.findUser();
                break;
            case 3:
                SERVICE_MENU.deleteUser();
                break;
            case 4:
                SERVICE_MENU.findAllUsers();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("no such operations");
        }
    }
}

