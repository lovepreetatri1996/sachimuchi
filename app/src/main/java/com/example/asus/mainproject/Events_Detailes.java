package com.example.asus.mainproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Events_Detailes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events__detailes);



        final TextView event_name=findViewById(R.id.event_name);
        TextView event_date=findViewById(R.id.dateofbrith);
        TextView event_time=findViewById(R.id.select_time);
        TextView event_description=findViewById(R.id.event_description);
        TextView event_activity_one=findViewById(R.id.activity_one);
        TextView event_activity_two=findViewById(R.id.activity_two);
        TextView event_activity_three=findViewById(R.id.activity_three);
        TextView event_activity_four=findViewById(R.id.activity_four);
        TextView event_activity_five=findViewById(R.id.activity_five);
        TextView event_male=findViewById(R.id.event_male);
        TextView event_female=findViewById(R.id.event_female);



        String name = getIntent().getStringExtra("name");
        String date = getIntent().getStringExtra("date");
        String location= getIntent().getStringExtra("location");
        String time = getIntent().getStringExtra("time");
        String eventdescription = getIntent().getStringExtra("event_description");
        String activityone= getIntent().getStringExtra("activityone");
        String activitytwo= getIntent().getStringExtra("activitytwo");
        String activitythree= getIntent().getStringExtra("activitythree");
        String activityfour= getIntent().getStringExtra("activityfour");
        String activityfive= getIntent().getStringExtra("activityfive");
        String male= getIntent().getStringExtra("event_male");
        String female= getIntent().getStringExtra("event_female");

        event_name.setText(name);
        event_date.setText(date);
        event_time.setText(time);
        event_description.setText(eventdescription);
        event_activity_one.setText(activityone);
        event_activity_two.setText(activitytwo);
        event_activity_three.setText(activitythree);
        event_activity_four.setText(activityfour);
        event_activity_five.setText(activityfive);
        event_male.setText(male);
        event_female.setText(female);

    }

    public void back_back(View view) {
        finish();
    }
}
