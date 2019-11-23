package com.tamas.szasz.zapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    private boolean mShowPasswordTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
//        LoginThread loginThread = new LoginThread();
//        loginThread.run();
        InfoThread infoThread = new InfoThread();
        infoThread.run();
//        UpdateThread updateThread = new UpdateThread();
//        updateThread.run();
    }


    public void onRegisterBTN(View view){
        RegisterThread registerThread = new RegisterThread();
        registerThread.run();
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
