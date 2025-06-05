package com.model;

import java.util.Scanner;

public class UI {
    private RecipeSystemFACADE systemFACADE;

    public UI() {
        systemFACADE = new RecipeSystemFACADE();
    }

    public void runLogin() {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Would you like to login or sign up?");
        System.out.println("1. Login");
        System.out.println("2. Sign up");
        int choice = keyboard.nextInt();
        keyboard.nextLine();
 
        switch (choice) {
            case 1:
                System.out.println("Enter your username");
                String username = keyboard.nextLine();
                System.out.println("Enter your password");
                String password = keyboard.nextLine();
                User loggedIn = systemFACADE.login(username, password);

                if (loggedIn != null) {
                    System.out.println("You have logged in");
                } else {
                    System.out.println("Login failed, try again");
                }
                break;
            case 2:
                System.out.println("Enter your first name");
                String firstName = keyboard.nextLine();
                System.out.println("Enter your last name");
                String lastName = keyboard.nextLine();
                System.out.println("Enter your email");
                String email = keyboard.nextLine();
                System.out.println("Enter your university ID");
                String universityID = keyboard.nextLine();
                System.out.println("Enter your username");
                String newUsername = keyboard.nextLine();
                System.out.println("Enter your password");
                String newPassword = keyboard.nextLine();

                User newUser = systemFACADE.createAccount(firstName, lastName, email, universityID, newUsername, newPassword);
                if (newUser != null) {
                    System.out.println("Account created! You are now logged in as " + newUser.getUsername());
                } else {
                    System.out.println("Username already exists. Try a different username");
                }
                break;
        }
    }
        public static void main(String[] args){
            UI ui = new UI();
            ui.runLogin();
        }
}
