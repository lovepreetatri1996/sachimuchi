package com.example.asus.mainproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
    public void feedback(View v){
        Intent i =new Intent(FirstPage.this,Feedback.class);
        startActivity(i);
    }
}
