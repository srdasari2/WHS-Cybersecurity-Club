package com.whs.cybsec;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.whs.cybsec.Constants.AUTH_USER_EMAIL;

/**
 * @author Satvik Dasari
 * SignUp Members
 */
public class Registration extends AppCompatActivity {


    private EditText firstName, lastName, password, confirmPassword, email, grade;
    private TextView errorMsg;

    private static final String TAG = Registration.class.getSimpleName();
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Declare firebase auth
    private FirebaseAuth mAuth;
    private boolean authAcctSuccess;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collectRegistrationInfo();

    }

    /**
     * Collect user SingUp info
     */
    private void collectRegistrationInfo() {

        errorMsg = findViewById(R.id.tvErrorMsg);
        firstName = findViewById(R.id.etFname);
        lastName = findViewById(R.id.etLname);
        password = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);
        grade = findViewById(R.id.etGrade);

        Button register = findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = validateRegisterInfo();
                if (isValid) {
                    createUser();
                } else {
                    ValidationActivity.displayErrorMsg("Invalid values. please correct.", errorMsg);
                    Log.d(TAG, "Invalid values. please correct.");
                }
            }
        });

    }

    //Validation of each of the registration fields
    private boolean validateRegisterInfo() {

        Log.d(TAG, "Validating user SignUp info.");

        boolean valid = true;

        if (!ValidationActivity.isValidAndMsg(firstName, "", ValidationActivity.REQUIRED_MSG, true)) {
            valid = false;
        }
        if (!ValidationActivity.isValidAndMsg(lastName, "", ValidationActivity.REQUIRED_MSG, true)) {
            valid = false;
        }
        if (!ValidationActivity.isValidAndMsg(email, "", ValidationActivity.REQUIRED_MSG, true)) {
            valid = false;
        }
        if (!ValidationActivity.isValidAndMsg(password, "", ValidationActivity.REQUIRED_MSG, true)) {
            valid = false;
        }
        if (!ValidationActivity.isValidAndMsg(grade, ValidationActivity.REGEX_GRADE, ValidationActivity.REQUIRED_MSG, true)) {
            valid = false;
        }

        return valid;

    }

    private void createUser() {

        Log.d(TAG, "Creating user record in the database.");

        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setPassword(password.getText().toString());
        user.setEmail(email.getText().toString());
        user.setGrade(grade.getText().toString());

        //Create a new document with a random ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        createAuthentication(email.getText().toString(), password.getText().toString());
                        if (!authAcctSuccess) {
                            showSignIn();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private boolean createAuthentication(String email, String password) {

        Log.d(TAG, "Creating user email authentication record.");

        //Initializing the Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        Log.d(TAG, "Creating authentication account for user: " + email);
        mAuth.createUserWithEmailAndPassword(email, password);
        return authAcctSuccess;

    }

    public void onRegistrationCancel(View view){

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

    }

    // RegistrationTeacherActivity is unsuccessful. show login screen
    private void showSignIn() {

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

    }

    // RegistrationTeacherActivity is successful. show home screen
    private void showHome(String userEmail) {

        Log.d(TAG,"Taking user to Home");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(AUTH_USER_EMAIL,userEmail);
        startActivity(intent);

    }

}
