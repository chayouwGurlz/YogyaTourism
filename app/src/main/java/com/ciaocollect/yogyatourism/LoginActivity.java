package com.ciaocollect.yogyatourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;

    TextInputLayout tiUsername;
    TextInputLayout tiPassword;
    Button btnLogin;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DbHelper(this);
        initCreateAccountTextView();
        initViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    Login currUser = dbHelper.Authenticate(new Login(null, username, password));

                    if (currUser != null){
                        Snackbar.make(btnLogin, "Successfully logged in!", Snackbar.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else{
                        Snackbar.make(btnLogin, "Failed to logged in!, please try again", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initCreateAccountTextView(){
        TextView tvCreateAccount = (TextView)findViewById(R.id.txStatusLogin);
        tvCreateAccount.setText(fromHtml(("<font color='#ff5733'>I don't have account yet.</font><font color='#0c0099'> create new one </font>")));
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews(){
        etUsername = (EditText) findViewById(R.id.etUserLogin);
        etPassword = (EditText) findViewById(R.id.etPassLogin);
        tiUsername = (TextInputLayout) findViewById(R.id.tiUserLogin);
        tiPassword = (TextInputLayout) findViewById(R.id.tiPassLogin);
        btnLogin = (Button)findViewById(R.id.btnLogin);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else{
            result = Html.fromHtml(html);
        }
        return result;
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
