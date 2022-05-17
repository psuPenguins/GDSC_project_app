package com.example.gdsc_project_app;

import static android.text.TextUtils.isEmpty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvSignUp;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById((R.id.etEmail));
        etPassword = findViewById((R.id.etPassword));
        btnLogin = findViewById((R.id.btnLogin));
        tvSignUp = findViewById(R.id.tvSignUp);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(email, password);
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick sign up button");
                goRegisterActivity();
            }
        });
    }

    private void loginUser(String email, String password){
        Log.i(TAG, "Attempting to login user with email: " + email);

        // Check if username or password is lacking
        if (isEmpty(email)){
            etEmail.setError("An username is required.");
            etEmail.requestFocus();
            return;
        }
        if (isEmpty(password)){
            etPassword.setError("A password is required.");
            etPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please provide a valid email.");
            etEmail.requestFocus();
            return;
        }
        if (password.length() < 6){
            etPassword.setError("Minimum length of password is 6.");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            goMainActivity();
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, Html.fromHtml("<font color='#5e5e5e'><b>" + "Failed to login. Please check your credentials." + "</b></font>"), Toast.LENGTH_LONG).show();
                        }

                    }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        Log.i(TAG, "Going into MainActivity");
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void goRegisterActivity() {
        Intent i = new Intent(this, SignUpActivity.class);
        Log.i(TAG, "Going into RegisterActivity");
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }



    public void updatePassword() {
        // [START update_password]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = "SOME-SECURE-PASSWORD";

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });
        // [END update_password]
    }
}
