package com.tamas.szasz.zapp.credentials;

import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import static android.app.PendingIntent.getActivity;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private static User instance;
    private Context context;

    private User(){

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

    public void setContext(Context context){
        this.context = context;
    }

    public void setToken(String token) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        SharedPreferences.Editor _userEditor = sharedPreferences.edit();
        _userEditor.putString("TOKEN", token);
        _userEditor.apply();
        this.token = token;
        Log.d("TOKEN","savingToken");
//        SharedPreferences sharedPreferences = context.getSharedPreferences("TOKEN",Context.MODE_PRIVATE);
//        sharedPreferences.edit().putString("Token",token);
//        this.token = token;
    }
}
