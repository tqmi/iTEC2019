package com.tamas.szasz.zapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tamas.szasz.zapp.NavigationActivity;
import com.tamas.szasz.zapp.R;
import com.tamas.szasz.zapp.cars.Car;
import com.tamas.szasz.zapp.cars.retrofit_threads.cars.AddThread;
import com.tamas.szasz.zapp.cars.retrofit_threads.cars.CarUpdateThread;
import com.tamas.szasz.zapp.cars.retrofit_threads.cars.DeleteThread;
import com.tamas.szasz.zapp.cars.retrofit_threads.cars.ListThread;
import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.login.retrofit_threads.user.RegisterThread;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private boolean mShowPasswordTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showPassword();
    }

    public void onRegisterBTN(View view){


        String firstName = null;
        String lastName = null;
        String email = null;
        String password = null;

        firstName = ((TextInputEditText)findViewById(R.id.act_register_TIET_firstName)).getText().toString();
        lastName = ((TextInputEditText)findViewById(R.id.act_register_TIET_lastName)).getText().toString();
        email = ((TextInputEditText)findViewById(R.id.act_register_TIET_email)).getText().toString();
        password = ((TextInputEditText)findViewById(R.id.act_register_TIET_password)).getText().toString();

        String regexEmail = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern patternEmail = Pattern.compile(regexEmail);

        Matcher matcherEmail = patternEmail.matcher(email);

        if(!matcherEmail.matches()){
            //TODO: handle incorrect email
            Toast.makeText(this,"Invalid email!",Toast.LENGTH_LONG).show();
            Log.d("REGISTER FAILED","incorrectEmail");
            return;
        }

        if(firstName.length() < 1 || lastName.length() < 1){
            //TODO: show something
            Toast.makeText(this,"Fill in name!",Toast.LENGTH_LONG).show();
            Log.d("REGISTRATION FAILED","name missing");
            return;
        }


        RegisterThread registerThread = new RegisterThread(email,firstName,lastName,password,this);
        registerThread.run();
/*
        CarUpdateThread carUpdateThread = new CarUpdateThread(new Car("new ","new",199,30,20,"2019-11-23T10:54:13.16"," ","d68f044b-38de-4749-9850-281a56b22269"),this);
        carUpdateThread.run();*/
    }

    public void onRegisterSuccess(){
        Log.d("REGITER SUCCESS","here");

        goToMapsActivity();

    }

    public void onRegisterFailed(){

        Toast.makeText(this,"Registration failed! Try again!",Toast.LENGTH_LONG).show();

    }

    private  void goToMapsActivity() {
        Intent mapsIntent = new Intent(this, NavigationActivity.class);
        startActivity(mapsIntent);
    }


    private void showPassword() {
        final ImageView imageViewShowPassword = findViewById(R.id.act_register_IV_ShowPassword);
        imageViewShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editTextPass = findViewById(R.id.act_register_TIET_password);
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
