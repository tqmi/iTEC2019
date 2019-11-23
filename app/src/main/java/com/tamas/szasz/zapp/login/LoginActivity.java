package com.tamas.szasz.zapp.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tamas.szasz.zapp.R;
import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.login.retrofit_threads.InfoThread;
import com.tamas.szasz.zapp.login.retrofit_threads.LoginThread;
import com.tamas.szasz.zapp.login.retrofit_threads.RegisterThread;
import com.tamas.szasz.zapp.login.retrofit_threads.UpdateThread;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.PendingIntent.getActivity;

public class LoginActivity extends AppCompatActivity {

    private String inputEmail;
    private String inputPassword;
    private static boolean logedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logedIn = false;
        User.getInstance().setContext(this);
        if(getSavedToken()){
            Log.d("TOKEN","got token");
            logedIn = true;
            //TODO: token saved, get user info and start map activity
        }
    }


    public void onLoginBTN(View view){
        inputEmail = ((TextInputEditText)findViewById(R.id.act_login_TIET_email)).getText().toString();
        inputPassword = ((TextInputEditText)findViewById(R.id.act_login_TIET_password)).getText().toString();

        String regexEmail = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern patternEmail = Pattern.compile(regexEmail);

        Matcher matcherEmail = patternEmail.matcher(inputEmail);

        if(!matcherEmail.matches()){
            //TODO: handle incorrect email
            Log.d("LOGIN FAILED","incorrectEmail");
            return;
        }

//        String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
//        Pattern patternPassword = Pattern.compile(regexPassword);
//        Matcher matcherPassword = patternPassword.matcher(inputPassword);
//
//        if(!matcherPassword.matches()){
//            Log.d("LOGIN FAILED","incorrectpassword");
//            //TODO: handle incorrect password
//            return;
//        }

        LoginThread loginThread = new LoginThread(inputEmail,inputPassword);
        loginThread.run();

    }

    private  boolean getSavedToken(){
        Log.d("TOKEN","geting token");
        SharedPreferences sharedPref = this.getSharedPreferences("TOKEN",MODE_PRIVATE);

        String storedToken = null;
        if(sharedPref.contains("Token")){
            storedToken = sharedPref.getString("Token",null);
        }

        if(storedToken != null){
            User.getInstance().setToken(storedToken);
            getInfo();
            return true;
        }

        return false;
    }

    private static void getInfo(){
        InfoThread infoThread = new InfoThread();
        infoThread.run();
    }

    public static void loginSuccessful(){
        logedIn = true;
        getInfo();
    }

    public static void loginDenied(){
        Log.d("LOGIN FAILED","failed login");
    }

    public void onRegisterBTN(View view){
//        RegisterThread registerThread = new RegisterThread();
//        registerThread.run();


    }
}
