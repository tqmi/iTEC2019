package com.tamas.szasz.zapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tamas.szasz.zapp.R;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private String userEmail;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void onLoginBTN(View view){

        userEmail = (String) ((TextView)findViewById(R.id.login_email_tw)).getText();
        userPassword = (String) ((TextView)findViewById(R.id.login_password_tw)).getText();
        URL authURL;
        try {
            authURL = new URL(R.string.auth_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    public void onRegisterBTN(View view){

    }
}
