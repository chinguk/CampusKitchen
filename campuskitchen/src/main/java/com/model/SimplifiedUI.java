package com.model;

public class SimplifiedUI {
    public SimplifiedUI() {}

    public void run(){
        scenario1();
    }

    public void scenario1() {
        User user = RecipeSystemFACADE.getInstance().login("kim", "10062003");

        if(user == null) {
            System.out.println("We didn't successfully login");
            return;
        }

        System.out.println("Successfully logged in");
        System.out.println(user);
    }

    public static void main(String[] args) {
        (new SimplifiedUI()).run();
    }
}
