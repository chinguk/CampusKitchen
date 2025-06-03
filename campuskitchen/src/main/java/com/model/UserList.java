package com.model;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> users;
    private UserList userList;

    public UserList(){
        this.users = new ArrayList<>();
    }

    public UserList getInstance(){
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    public void addUser(String firstName, String lastName, String email, String universityID, String username, String password){
        // check for duplicate usernames, if taken do nothing
        if (getUser(username) != null) {
            return;
        }

        // create new user
        User newUser = new User(firstName, lastName, email, universityID, username, password);
        users.add(newUser);

        saveUsers(newUser);
    }

    public User getUser(String username, String password){
    for (User u : users) {
            if (u != null && u.login(username, password) && u.username.equals(username)) {
                return u;
            }
        }
        return null;
    }

    public void editUser(String firstName, String lastName, String email, String universityID, String username, String password){

    }

    public void saveUsers(User user){

    }
}
