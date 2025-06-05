package com.model;

import java.util.ArrayList;
import java.util.Scanner;

public class KitchenDriver {
    /**
     * This method prompts the user to enter their username and password and checks if the user is in the list
     * of users. If the user is found, it prints a "Login successful!" message.
     * Otherwise, it prints a "Login failed. Please check your username and
     * password." message.
     */
    public static void main(String[] args){
        ArrayList<User> users = DataLoader.getUsers();
        Scanner scanner = new Scanner(System.in);

        System.out.println("==== Welcome to Campus Kitchen ====");
        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        User loggedInUser = null;

        for (User user : users) {
            if (user.getUsername().equals(inputUsername) && user.getPassword().equals(inputPassword)) {
                loggedInUser = user;
                break;
            }
        }

        if (loggedInUser != null) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Please check your username and password.");
        }
        scanner.close();
    }
}
