package com.tamas.szasz.zapp.login.retrofit_classes.user;

public class UserUpdateResponse extends UserUpdateRequest {

    private String email;

    public UserUpdateResponse(String firstName,String lastName,String email){
        super(firstName,lastName);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
