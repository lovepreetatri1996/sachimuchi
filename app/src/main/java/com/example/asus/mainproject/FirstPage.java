package com.example.asus.mainproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirstPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

    }

    public void signup(View view) {
        Intent i=new Intent(FirstPage.this,SignUP.class);
        startActivity(i);
    }




    public void jaja(View view) {
        Intent i=new Intent(FirstPage.this,Homes.class);
        startActivity(i);

    }

    public void login(View view) {

        EditText email_et = findViewById(R.id.email_et);

        EditText password_et = findViewById(R.id.password_et);

        String email = email_et.getText().toString();

        String password = password_et.getText().toString();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Intent i = new Intent(FirstPage.this , Homes.class);

                    startActivity(i);
                }

                else {

                    Toast.makeText(FirstPage.this , "invalid login" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void frogot_password(View view) {


        EditText email_et = findViewById(R.id.email_et);

        String email = email_et.getText().toString();

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {


                            Log.d("", "Email sent.");
                        }
                    }
                });




    }
}
