package com.tamas.szasz.zapp.login.retrofit_classes;

public class UserUpdateRequest {
    private String firstName, lastName;
    public UserUpdateRequest( String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName(){return firstName;}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName(){return lastName;}
}
