package com.tamas.szasz.zapp.login.retrofit_classes.user;

public class UserInfoResponse extends UserInfoRequest {

    private String firstName;
    private String lastName;
    private String email;

    public UserInfoResponse(String firstName,String lastName,String email){
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
