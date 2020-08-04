package com.whs.cybsec;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.whs.cybsec.Constants.AUTH_USER_EMAIL;
import static com.whs.cybsec.Constants.SHARED_PREFERENCES;

/**
 * @author Satvik Dasari
 * Login
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private EditText email, password;
    private TextView errorMsg;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userSignIn();

    }

    private void userSignIn() {
        errorMsg = findViewById(R.id.tvErrorMsg);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.progressBar);

        final Button signIn = findViewById(R.id.btnLogin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = validateSignInInfo();
                if (valid) {
                    signIn(email.getText().toString(), password.getText().toString());
                } else {
                    ValidationActivity.displayErrorMsg("Invalid values.Please try again!", errorMsg);
                }
            }
        });

    }

    private boolean validateSignInInfo() {
        Log.d(TAG, "Validating user credential fields.");
        boolean valid = true;

        if (!ValidationActivity.isValidAndMsg(email, "", ValidationActivity.REQUIRED_MSG, true)) {
            valid = false;
        }
        if (!ValidationActivity.isValidAndMsg(password, "", ValidationActivity.REQUIRED_MSG, true)) {
            valid = false;
        }

        return valid;
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "SignIn - authentication with :" + email);
        progressBar.setVisibility(View.VISIBLE);
        //Initialization of the Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        //START sign_in_with_email
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            persistUserInfo(user.getEmail());
                            showHome();
                        } else {
                            //If the login fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (!task.isSuccessful()) {
                            ValidationActivity.displayErrorMsg("Login failed. Invalid Email/Password.", errorMsg);
                        }
                    progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void persistUserInfo(String userEmail){
        Log.d(TAG,"persisting user email:" + userEmail);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(AUTH_USER_EMAIL,userEmail);
        editor.commit();

    }

    //Login is successful so show home screen
    private void showHome() {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    // SignUp user
    public void startRole(View view) {
        Intent intent = new Intent(getApplicationContext(), Registration.class);
        startActivity(intent);

    }
//    public void startResetPassword(View view) {
//        Intent intent = new Intent(getApplicationContext(), RoleActivity.class);
//        startActivity(intent);
//
//    }

}


