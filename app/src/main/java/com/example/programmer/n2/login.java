package com.example.programmer.n2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class login extends AppCompatActivity implements View.OnClickListener {

    private EditText edittextmail;
    private EditText edittextpassword;
    private Button loginbutton;
    private TextView notregistered;
    private ProgressDialog progressDialoglogin;
    private FirebaseAuth firebaseAuth;
    public DatabaseReference  bdatabaseReference;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);

        firebaseAuth = FirebaseAuth.getInstance();
        edittextmail = (EditText) findViewById(R.id.edittextmail);
        edittextpassword = (EditText) findViewById(R.id.edittextpassword);
        loginbutton = (Button) findViewById(R.id.loginbutton);
        notregistered = (TextView) findViewById(R.id.notregistered);
        loginbutton.setOnClickListener(this);
        notregistered.setOnClickListener(this);
        progressDialoglogin = new ProgressDialog(this);
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, user.class));
        }


    }

    private void loginuser() {
        String email = edittextmail.getText().toString().trim();
        String password = edittextpassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialoglogin.setMessage("Signing in.....");
        progressDialoglogin.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialoglogin.dismiss();
                        if (task.isSuccessful()) {
                            progressDialoglogin.dismiss();
                            finish();
                            Toast.makeText(login.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(login.this, user.class));
                        } else {
                            progressDialoglogin.dismiss();
                            Toast.makeText(login.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
        if (v == loginbutton) {
            loginuser();
        } else if (v == notregistered) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }


}


