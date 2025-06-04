package com.model;

import java.util.Scanner;

public class UI {
    private RecipeSystemFACADE systemFACADE;

    public UI() {
        systemFACADE = new RecipeSystemFACADE();
    }

    public void runLogin() {
        Scanner keyboard = new Scanner(System.in);

        //System.out.println("Would you like to login?");
        //System.out.println("1. Yes");
        //System.out.println("2. No");
        //int choice = keyboard.nextInt();
        //keyboard.nextLine();
 
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
    }
        public static void main(String[] args){
            UI ui = new UI();
            ui.runLogin();
        }
}
