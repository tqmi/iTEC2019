package com.tamas.szasz.zapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tamas.szasz.zapp.R;
import com.tamas.szasz.zapp.login.retrofit_threads.LoginThread;
import com.tamas.szasz.zapp.login.retrofit_threads.RegisterThread;

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
<<<<<<< HEAD
        LoginThread loginThread = new LoginThread();
        loginThread.run();
=======

        userEmail = (String) ((TextView)findViewById(R.id.login_email_tw)).getText();
        userPassword = (String) ((TextView)findViewById(R.id.login_password_tw)).getText();
        URL authURL = null;
        try {
            authURL = new URL(getString(R.string.auth_url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
>>>>>>> 6618bc5ec87581e62d4d339726af25b021470b3a

    }


    public void onRegisterBTN(View view){
        RegisterThread registerThread = new RegisterThread();
        registerThread.run();
    }
}
