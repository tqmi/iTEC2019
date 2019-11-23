package com.tamas.szasz.zapp.credentials;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private static User instance;

    private User(){
        token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImp0aSI6ImRhMzkwMGQ3LWUyNmUtNDdmMi1hOTk1LTkyYjViNzZmMGYxNyIsImVtYWlsIjoidGVzdEBnbWFpbC5jb20iLCJpZCI6ImJjODNjNWVmLWU0NDAtNDk0MC1hY2RlLTRhYjcyYWJjOTQ5YyIsIm5iZiI6MTU3NDQzNjU0MSwiZXhwIjoxNTc0Njk1NzQxLCJpYXQiOjE1NzQ0MzY1NDF9.5SVjBedzR4hiPaXMqLsdmxfh2mhCUvZPV7fDi6nwtMg";
    }

    public static User getInstance() {
        if(instance == null)
            instance = new User();
        return instance;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
