package com.example.truerideshare;

public class User {
    private String lastName, firstName, location, password;

    public User(String lastName, String firstName, String location, String password){
        this.lastName = lastName;
        this.firstName = firstName;
        this.location = location;
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLocation(){
        return this.location;
    }
}
