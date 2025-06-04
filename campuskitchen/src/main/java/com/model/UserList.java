package com.model;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> users;
    private static UserList userList;

    public UserList(){
        this.users = new ArrayList<>();
    }

    public static UserList getInstance(){
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    public void addUser(String firstName, String lastName, String email, String universityID, String username, String password){
        User newUser = new User(firstName, lastName, email, universityID, username, password);
        users.add(newUser);
    }

    public User getUser(String username){
        for (User u : users) {
            if (u != null && u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public void editUser(String firstName, String lastName, String email, String universityID, String username, String password){
        User existing = getUser(username);
        if (existing == null) {
            throw new IllegalArgumentException(
                "editUser: no user found with username='" + username + "'."
            );
        }        
        existing.setFirstName(firstName);
        existing.setLastName(lastName);
        existing.setEmail(email);
        existing.setUniversityID(universityID);
        existing.setPassword(password);
    }

    public void saveUsers(User user){

    }

    public void removeUser(String username) {
        if (username == null) return;
        User delete = null;
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                delete = u;
                break;
            }
        }
        if (delete != null) {
            users.remove(delete);
        }
    }
}
