package com.java.training.application.menu;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Scanner;

import static com.java.training.application.util.Constant.MESSAGE_NO_SUCH_OPERATIONS;

public class Menu {

    private static final ServiceMenu SERVICE_MENU = ServiceMenu.getInstance();
    private static final Logger LOGGER = Logger.getLogger(Menu.class);

    public static void run() {
        final Menu menu = new Menu();
        try {
            while (true) {
                menu.printMainMenu();
                final int choice = menu.readMenu();
                menu.makeChoiceOfOperation(choice);
                if (choice == 0) {
                    break;
                }
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void printMainMenu() {
        System.out.println("Menu:");
        System.out.println("1. add new string to table");
        System.out.println("2. find by name");
        System.out.println("3. delete by name");
        System.out.println("4. find all");
        System.out.println("0. exit");
    }

    public void printTableMenu() {
        System.out.println("Choose table:");
        System.out.println("1. user");
        System.out.println("2. car");
        System.out.println("0. exit");
    }

    public int readMenu() {
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void makeChoiceOfOperation(final int choice) throws SQLException {
        printTableMenu();
        final int choiceTable = readMenu();
        switch (choice) {
            case 1:
                chooseAddMethod(choiceTable);
                break;
            case 2:
                chooseFindMethod(choiceTable);
                break;
            case 3:
                chooseDeleteMethod(choiceTable);
                break;
            case 4:
                chooseFindAllMethod(choiceTable);
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println(MESSAGE_NO_SUCH_OPERATIONS);
        }
    }

    public void chooseAddMethod(int choice) throws SQLException {
        switch (choice) {
            case 1:
                SERVICE_MENU.addUser();
                break;
            case 2:
                SERVICE_MENU.addCar();
                break;
            default:
                System.out.println(MESSAGE_NO_SUCH_OPERATIONS);
        }
    }

    public void chooseFindMethod(int choice) throws SQLException {
        switch (choice) {
            case 1:
                SERVICE_MENU.findUser();
                break;
            case 2:
                SERVICE_MENU.findCar();
                break;
            default:
                System.out.println(MESSAGE_NO_SUCH_OPERATIONS);
        }

    }

    public void chooseDeleteMethod(int choice) throws SQLException {
        switch (choice) {
            case 1:
                SERVICE_MENU.deleteUser();
                break;
            case 2:
                SERVICE_MENU.deleteCar();
                break;
            default:
                System.out.println(MESSAGE_NO_SUCH_OPERATIONS);
        }

    }

    public void chooseFindAllMethod(int choice) throws SQLException {
        switch (choice) {
            case 1:
                SERVICE_MENU.findAllUsers();
                break;
            case 2:
                SERVICE_MENU.findAllCars();
                break;
            default:
                System.out.println("no such entity's");
        }

    }


}

