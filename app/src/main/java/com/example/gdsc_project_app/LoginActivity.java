package com.example.gdsc_project_app;

import static android.text.TextUtils.isEmpty;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView((R.layout.activity_login));

        etUsername = findViewById((R.id.etUsername));
        etPassword = findViewById((R.id.etPassword));
        btnLogin = findViewById((R.id.btnLogin));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });
    }

    private void loginUser(String username, String password){
        Log.i(TAG, "Attempting to login user: " + username);

        // Check if username or password is lacking
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder("Please ");
        if (isEmpty(username)){
            validationError = true;
            validationErrorMessage.append("enter a username");
        }
        if (isEmpty(password)){
            if (validationError){
                validationErrorMessage.append(", \n  and ");
            }
            validationError = true;
            validationErrorMessage.append("enter a password");
        }
        validationErrorMessage.append(".");
        if (validationError){
            Toast.makeText(LoginActivity.this, Html.fromHtml("<font color='#5e5e5e'><b>" + validationErrorMessage + "</b></font>"), Toast.LENGTH_LONG).show();
            return;
        }

        // TODO: navigate to the main activity if the user has signed in properly
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, Html.fromHtml("<font color='#5e5e5e'><b>" + "Issue with login!" + "</b></font>"), Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
                Log.i(TAG, "Login Success");
                Toast.makeText(LoginActivity.this, Html.fromHtml("<font color='#5e5e5e'><b>" + "Success!" + "</b></font>"), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        // Intent temp = new Intent(this, CommentActivity.class);
        Log.i(TAG, "Going into MainActivity");
        startActivity(i);
        // startActivity(temp);
    }
}
