package com.example.gdsc_project_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivity";
    private EditText etSignUpUsername;
    private EditText etSignUpPassword;
    private Button btnSignUp;
    private Button btnReturnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        etSignUpUsername = findViewById((R.id.etSignUpUsername));
        etSignUpPassword = findViewById((R.id.etSignUpPassword));
        btnSignUp = findViewById((R.id.btnSignUp));
        btnReturnLogin = findViewById(R.id.btnReturnLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                String username = etSignUpUsername.getText().toString();
                String password = etSignUpPassword.getText().toString();
            }
        });

        btnReturnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick sign up button");
                goLoginActivity();
            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        Log.i(TAG, "Going into LoginActivity");
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
