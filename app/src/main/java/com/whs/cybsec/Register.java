package com.whs.cybsec;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Satvik Dasari
 * Registration
 */

public class Register extends AppCompatActivity {
    private EditText firstName, lastName, password, email, grade;
    Button register = findViewById(R.id.btnRegister);
    //cancel button goes here
    //ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_member_registration);

        firstName = findViewById(R.id.etFname);
        lastName = findViewById(R.id.etLname);
        password = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);
        grade = findViewById(R.id.etGrade);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fFirstName = firstName.getText().toString();
                String fLastName = lastName.getText().toString();
                String fPassword = password.getText().toString();
                String fEmail = email.getText().toString();
                String fGrade = grade.getText().toString();
                if(TextUtils.isEmpty(fEmail)){
                    email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(fPassword)){
                    password.setError("Password is required");
                    return;
                }

                if(fPassword.length() < 6){
                    password.setError("Password must be at least 6 character long");
                    return;
                }



                fAuth.createUserWithEmailAndPassword(fEmail, fPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(Register.this, "Error while creating account:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }
    public void onRegistrationCancel(View view){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
