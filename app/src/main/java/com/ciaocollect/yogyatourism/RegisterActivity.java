package com.ciaocollect.yogyatourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;

    TextInputLayout tiUsername;
    TextInputLayout tiPassword;
    Button btnRegister;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DbHelper(this);
        initViews();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();

                    if (!dbHelper.isUsernameExists(username)){
                        dbHelper.addLogin(new Login(null, username, password));
                        Snackbar.make(btnRegister, "Successfully created user!, please login", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    } else{
                        Snackbar.make(btnRegister, "Username already exist, please use another one", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initViews(){
        etUsername = (EditText) findViewById(R.id.etUserRegister);
        etPassword = (EditText) findViewById(R.id.etPassRegister);
        tiUsername = (TextInputLayout) findViewById(R.id.tiUserRegister);
        tiPassword = (TextInputLayout) findViewById(R.id.tiPassRegister);
        btnRegister = (Button)findViewById(R.id.btnRegister);
    }

    public boolean validate(){
        boolean valid = false;
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(username.isEmpty()){
            valid = false;
            tiUsername.setError("Please enter valid username");
        } else{
            if(username.length()>5){
                valid = true;
                tiUsername.setError(null);
            } else{
                valid = false;
                tiUsername.setError("Username is too short!");
            }
        }

        if(password.isEmpty()){
            valid = false;
            tiPassword.setError("Please enter valid password");
        } else{
            if(password.length()>5){
                valid = true;
                tiPassword.setError(null);
            } else{
                valid = false;
                tiPassword.setError("Password is too short!");
            }
        }
        return valid;
    }
}
