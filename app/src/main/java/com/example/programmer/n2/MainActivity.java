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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edittextmail;
    private EditText edittextpassword;
    private EditText editname;
    private Button registerbutton;
    private ProgressDialog progressDialogregister;
    private FirebaseAuth firebaseAuth;
    private TextView registered;
    public String s;
    public  DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialogregister=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users");





        edittextmail=(EditText)findViewById(R.id.edittextmail);
        edittextpassword=(EditText)findViewById(R.id.edittextpassword);
        editname=(EditText)findViewById(R.id.editname);
        registerbutton = (Button)findViewById(R.id.registerbutton);
        registered=(TextView)findViewById(R.id.registered);
        if(firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(this,user.class));
        }
        registerbutton.setOnClickListener(this);
        registered.setOnClickListener(this);



        }



    private void registeruser()
    {
            final String email=edittextmail.getText().toString();
            final String password=edittextpassword.getText().toString();
            final String username=editname.getText().toString().trim();




        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return ;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialogregister.setMessage("Registering user .....");
        progressDialogregister.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            String uid = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference current =databaseReference.child(uid);
                            current.child("Name").setValue(username);
                            current.child("Username").setValue(email);
                            current.child("Password").setValue(password);
                            databaseReference.keepSynced(true);

                            progressDialogregister.dismiss();
                            finish();
                            Toast.makeText(MainActivity.this, "Registered Successfully.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this,user.class));
                        } else {
                            progressDialogregister.dismiss();
                            Toast.makeText(MainActivity.this, " Not Registered", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v==registerbutton)
        {
                registeruser();
        }
        else if(v==registered)
        {
            finish();
            startActivity(new Intent(this,login.class));
        }

    }

}
