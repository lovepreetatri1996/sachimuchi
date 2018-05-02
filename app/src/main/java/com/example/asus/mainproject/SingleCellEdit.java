package com.example.asus.mainproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.asus.mainproject.dataModels.EventData;
import com.example.asus.mainproject.dataModels.ProfileData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Calendar;

import static com.example.asus.mainproject.fragments.Add_Event_Detailes.place_s;

public class SingleCellEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_cell_edit);

        ImageView cal = findViewById(R.id.calandar);
        
        
      final EditText event_name=findViewById(R.id.event_name);
    final EditText event_date=findViewById(R.id.dateofbrith);
    final EditText event_time=findViewById(R.id.select_time);
    EditText event_description=findViewById(R.id.event_description);
    EditText event_activity_one=findViewById(R.id.activity_one);
    EditText event_activity_two=findViewById(R.id.activity_two);
    EditText event_activity_three=findViewById(R.id.activity_three);
    EditText event_activity_four=findViewById(R.id.activity_four);
    EditText event_activity_five=findViewById(R.id.activity_five);
    EditText event_male=findViewById(R.id.event_male);
    EditText event_female=findViewById(R.id.event_female);



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



        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Calendar c = Calendar.getInstance();



                DatePickerDialog datePickerDialog = new DatePickerDialog(SingleCellEdit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        event_date.setText(String.valueOf(i2) + " /" + String.valueOf(i1 + 1)+"/"+String.valueOf(i));


                    }
                }, c.get(Calendar.YEAR) , c.get(Calendar.MONTH) , c.get(Calendar.DAY_OF_WEEK) );

                datePickerDialog.show();
            }
        });

        final Calendar call = Calendar.getInstance();
       int   year_x = call.get(Calendar.YEAR);
       int  month_x = call.get(Calendar.MONTH);
       int day_x = call.get(Calendar.DAY_OF_MONTH);


        ImageView  clock = findViewById(R.id.clock);



        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);


                TimePickerDialog time_dialog = new TimePickerDialog(SingleCellEdit.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        event_time.setText(String.valueOf(i)+":"+String.valueOf(i1));


                    }
                }, hour, minute, true);

                time_dialog.show();
            }
        });

}

    public void update(View view) {
        final EditText event_name=findViewById(R.id.event_name);
        final EditText event_date=findViewById(R.id.dateofbrith);
        final EditText event_time=findViewById(R.id.select_time);
        final EditText event_description=findViewById(R.id.event_description);
        final EditText event_activity_one=findViewById(R.id.activity_one);
        final EditText event_activity_two=findViewById(R.id.activity_two);
        final EditText event_activity_three=findViewById(R.id.activity_three);
        final EditText event_activity_four=findViewById(R.id.activity_four);
        final EditText event_activity_five=findViewById(R.id.activity_five);
        final EditText event_male=findViewById(R.id.event_male);
        final EditText event_female=findViewById(R.id.event_female);


        final String e_name=event_name.getText().toString();
        final String e_date=event_date.getText().toString();
        final String e_time=event_time.getText().toString();
        final String e_description=event_description.getText().toString();
        final String e_activity_one=event_activity_one.getText().toString();
        final String e_activity_two=event_activity_two.getText().toString();
        final String e_activity_three=event_activity_three.getText().toString();
        final String e_activity_four=event_activity_four.getText().toString();
        final String e_activity_five=event_activity_five.getText().toString();
        final String e_male=event_male.getText().toString();
        final String e_female=event_female.getText().toString();


        if(e_name.length() <=4 )
        {
            event_name.setError("name must be of minimum 4 characters");
            return;
        }
        if(e_date.length() == 0 )
        {
            event_date.setError( "please enter date of the event" );
            return;
        }
        if(e_time.length() == 0 )
        {
            event_time.setError( "please enter time of the event" );
            return;
        }

        if(e_description.length() <=20 )
        {
            event_description.setError("name must be of minimum 40 characters");
            return;
        }

        if(e_activity_one.length() <=10 )
        {
            event_activity_one.setError("name must be of minimum 30 characters");
            return;
        }

        if(e_activity_two.length() <=10 )
        {
            event_activity_two.setError("name must be of minimum 30 characters");
            return;
        }
        if(e_activity_three.length() <=10 )
        {
            event_activity_three.setError("name must be of minimum 30 characters");
            return;
        }
        if(e_activity_four.length() <=10 )
        {
            event_activity_four.setError("name must be of minimum 30 characters");
            return;
        }
        if(e_activity_five.length() <=10 )
        {
            event_activity_five.setError("name must be of minimum 30 characters");
            return;
        }

        if(e_male.length() >=15)
        {
            event_male.setError("enter must 15 number of male");
            return;
        }

        if(e_female.length() >=10)
        {
            event_female.setError("enter must 10 number of female");
            return;
        }

        EventData data = new EventData(e_name, e_date, e_time, e_description, e_activity_one, e_activity_two, e_activity_three, e_activity_four, e_activity_five, e_male, e_female , place_s);

        OnCompleteListener listener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(SingleCellEdit.this , "event updated " , Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail();

        database.getReference().child("event").child(email.replace(".","")).child(getIntent().getStringExtra("key")).setValue(data).addOnCompleteListener(listener);





    }


}
