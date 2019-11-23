package com.tamas.szasz.zapp.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.material.textfield.TextInputEditText;
import com.tamas.szasz.zapp.NavigationActivity;
import com.tamas.szasz.zapp.R;
import com.tamas.szasz.zapp.cars.retrofit_threads.cars.ListThread;
import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.login.retrofit_threads.user.InfoThread;
import com.tamas.szasz.zapp.login.retrofit_threads.user.LoginThread;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private boolean mShowPasswordTrue;
    private String inputEmail;
    private String inputPassword;
    private static boolean logedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logedIn = false;
        User.getInstance().setContext(this);
        if(isSavedToken()){
            logedIn = true;
            goToMapsActivity();
        }
    }

    private  void goToMapsActivity() {
        Intent mapsIntent = new Intent(this, NavigationActivity.class);
        startActivity(mapsIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRegisterActivity();
        showPassword();
    }

    private void startRegisterActivity() {
        final TextView _textRegister = findViewById(R.id.act_login_TV_Register);
        _textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(signUpIntent);
            }
        });
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

        LoginThread loginThread = new LoginThread(inputEmail,inputPassword, this);
        loginThread.run();

    }

    private  boolean isSavedToken(){
        Log.d("TOKEN","geting token");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String storedToken = null;
        if(sharedPref.contains("TOKEN")){
            storedToken = sharedPref.getString("TOKEN",null);
        }

        if(storedToken != null){
            User.getInstance().setToken(storedToken);
            getInfo();
            return true;
        }

        return false;
    }

    private void getInfo(){
        InfoThread infoThread = new InfoThread();
        infoThread.run();
        ListThread listThread = new ListThread(this);
        listThread.run();
        try {
            listThread.join();
            infoThread.join();
            goToMapsActivity();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loginSuccessful(){
        logedIn = true;
        getInfo();
    }

    public void loginDenied(){
        Log.d("LOGIN FAILED","failed login");
    }




    private void showPassword() {
        final ImageView imageViewShowPassword = findViewById(R.id.act_login_IV_ShowPassword);
        imageViewShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editTextPass = findViewById(R.id.act_login_TIET_password);
                if (mShowPasswordTrue) {
                    editTextPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_24px));
                    editTextPass.setSelection(editTextPass.getText().length());
                    mShowPasswordTrue = false;
                } else {
                    editTextPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24px));
                    editTextPass.setSelection(editTextPass.getText().length());
                    mShowPasswordTrue = true;
                }
            }
        });
    }
}
