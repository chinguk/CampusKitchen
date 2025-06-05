package com.model;

public class SimplifiedUI {
    public SimplifiedUI() {}

    public void run(){
        //scenario1();
        scenario2();
    }

    public void scenario1() {
        User user = RecipeSystemFACADE.getInstance().login("emill23", "321");

        if(user == null) {
            System.out.println("We didn't successfully login");
            return;
        }

        System.out.println("Successfully logged in");
        System.out.println(user);
    }

    public void scenario2() {
        System.out.println("Scenario 2");

        boolean adding = RecipeSystemFACADE.getInstance().createAccount("Amy", "Smith", "asmith@asmith.com", "1234", "asmith", "2309553344");

        if(!adding) {
            System.out.println("Couldn't successfully create account");
            return;
        }

        System.out.println("Successfully created account");
        RecipeSystemFACADE.getInstance().logout();

        User user = RecipeSystemFACADE.getInstance().login("asmith", "2309553344");

        if(user == null) {
            System.out.println("We didn't successfully login");
            return;
        }

    }

    public static void main(String[] args) {
        (new SimplifiedUI()).run();
    }
}
