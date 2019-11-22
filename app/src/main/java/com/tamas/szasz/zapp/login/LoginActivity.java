package com.tamas.szasz.zapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tamas.szasz.zapp.R;
import com.tamas.szasz.zapp.login.retrofit_threads.InfoThread;
import com.tamas.szasz.zapp.login.retrofit_threads.LoginThread;
import com.tamas.szasz.zapp.login.retrofit_threads.RegisterThread;
import com.tamas.szasz.zapp.login.retrofit_threads.UpdateThread;

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
//        LoginThread loginThread = new LoginThread();
//        loginThread.run();
//        InfoThread infoThread = new InfoThread();
//        infoThread.run();
        UpdateThread updateThread = new UpdateThread();
        updateThread.run();
    }


    public void onRegisterBTN(View view){
        RegisterThread registerThread = new RegisterThread();
        registerThread.run();
    }
}
