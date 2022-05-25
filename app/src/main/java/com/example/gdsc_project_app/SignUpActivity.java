package com.example.gdsc_project_app;

import static android.text.TextUtils.isEmpty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivity";
    private EditText etSignUpEmail;
    private EditText etSignUpUsername;
    private EditText etSignUpPassword;
    private Button btnSignUp;
    private Button btnReturnLogin;

    private ProgressDialog mLoadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(SignUpActivity.this);

        etSignUpEmail = findViewById((R.id.etSignUpEmail));
        etSignUpPassword = findViewById((R.id.etSignUpPassword));
        btnSignUp = findViewById((R.id.btnSignUp));
        btnReturnLogin = findViewById(R.id.btnReturnLogin);
        etSignUpUsername = findViewById(R.id.etSignUpUsername);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                String email = etSignUpEmail.getText().toString().trim();
                String password = etSignUpPassword.getText().toString().trim();
                String username = etSignUpUsername.getText().toString().trim();

                // Check if username, email, or password is lacking
                if (isEmpty(email)){
                    etSignUpEmail.setError("An email is required.");
                    etSignUpEmail.requestFocus();
                    return;
                }
                if (isEmpty(username)){
                    etSignUpUsername.setError("An username is required.");
                    etSignUpUsername.requestFocus();
                    return;
                }
                if (isEmpty(password)){
                    etSignUpPassword.setError("A password is required.");
                    etSignUpPassword.requestFocus();
                    return;
                }
                if (password.length() < 6){
                    etSignUpPassword.setError("Minimum length of password is 6.");
                    etSignUpPassword.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etSignUpEmail.setError("Please provide a valid email.");
                    etSignUpEmail.requestFocus();
                    return;
                }

                mLoadingBar.setTitle("Registration");
                mLoadingBar.setMessage("Please wait while we check your credentials.");
                mLoadingBar.setCanceledOnTouchOutside(false);
                mLoadingBar.show();

                createAccount(email, username, password);
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

    private void createAccount(String email, String username, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "User has been registered successfully.");
                            String profileImage = "gs://chatterbox-58447.appspot.com/icons8-starfish-100.png";
                            FBUser user = new FBUser(email, username, password, "", profileImage);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                // Check if user created
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, Html.fromHtml("<font color='#5e5e5e'><b>" + "User has been registered successfully." + "</b></font>"), Toast.LENGTH_LONG).show();
                                        mLoadingBar.dismiss();
                                        goLoginActivity();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, Html.fromHtml("<font color='#5e5e5e'><b>" + "Failed to register. Please try again." + "</b></font>"), Toast.LENGTH_LONG).show();
                                        mLoadingBar.dismiss();
                                    }
                                }
                            });
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Failed to register. Please try again.", Toast.LENGTH_LONG).show();
                            mLoadingBar.dismiss();
                        }
                    }
                });
        // [END create_user_with_email]
    }


    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        Log.i(TAG, "Going into LoginActivity");
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
