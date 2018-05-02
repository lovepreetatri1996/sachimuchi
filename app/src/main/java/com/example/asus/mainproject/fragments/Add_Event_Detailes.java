package com.example.asus.mainproject.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.asus.mainproject.PlacePickActivity;
import com.example.asus.mainproject.R;
import com.example.asus.mainproject.SignUP;
import com.example.asus.mainproject.dataModels.EventData;
import com.example.asus.mainproject.dataModels.ProfileData;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class Add_Event_Detailes extends Fragment {
    Button button;
    private TextView tvDisplayTime, select_location;
    private TimePicker timePicker1;
    private Button btnChangeTime;

    private ImageView clock;

    private EditText time_et , date_et ;
    private Button add_et;

    private int hour;
    private int minute;

    static final int TIME_DIALOG_ID = 999;

    private static View view;
    int year_x, day_x, month_x;

   public  static   String place_s ;

   public static TextView place_txt ;

    public Add_Event_Detailes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_add__event__detailes, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        place_txt = view.findViewById(R.id.place_txt);

        time_et = view.findViewById(R.id.select_time);
        date_et=view.findViewById(R.id.dateofbrith);

        place_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity( new Intent(getContext() , PlacePickActivity.class));
            }
        });


        ImageView cal = view.findViewById(R.id.calandar);


        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Calendar c = Calendar.getInstance();



                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                 date_et.setText(String.valueOf(i2) + " /" + String.valueOf(i1 + 1)+"/"+String.valueOf(i));


                    }
                }, c.get(Calendar.YEAR) , c.get(Calendar.MONTH) , c.get(Calendar.DAY_OF_WEEK) );

                datePickerDialog.show();
            }
        });

        final Calendar call = Calendar.getInstance();
        year_x = call.get(Calendar.YEAR);
        month_x = call.get(Calendar.MONTH);
        day_x = call.get(Calendar.DAY_OF_MONTH);


        clock = view.findViewById(R.id.clock);

        select_location = view.findViewById(R.id.select_location);

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);


                TimePickerDialog time_dialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        time_et.setText(String.valueOf(i)+":"+String.valueOf(i1));


                    }
                }, hour, minute, true);

                time_dialog.show();
            }
        });

        add_et=view.findViewById(R.id.add_event);
        add_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final EditText event_name=view.findViewById(R.id.event_name);
                final EditText event_date=view.findViewById(R.id.dateofbrith);
                final EditText event_time=view.findViewById(R.id.select_time);
                final EditText event_description=view.findViewById(R.id.event_description);
                final EditText event_activity_one=view.findViewById(R.id.activity_one);
                final EditText event_activity_two=view.findViewById(R.id.activity_two);
                final EditText event_activity_three=view.findViewById(R.id.activity_three);
                final EditText event_activity_four=view.findViewById(R.id.activity_four);
                final EditText event_activity_five=view.findViewById(R.id.activity_five);
                final EditText event_male=view.findViewById(R.id.event_male);
                final EditText event_female=view.findViewById(R.id.event_female);


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



              OnCompleteListener listener = new OnCompleteListener() {
                  @Override
                  public void onComplete(@NonNull Task task) {

                      if(task.isSuccessful())
                      {
                          event_name.setText("");
                          event_date.setText("");
                          event_time.setText("");
                          event_description.setText("");
                          event_activity_one.setText("");
                          event_activity_two.setText("");
                          event_activity_three.setText("");
                          event_activity_four.setText("");
                          event_activity_five.setText("");
                          event_male.setText("");
                          event_female.setText("");
                      }
                  }
              };


               FirebaseAuth auth =  FirebaseAuth.getInstance();

               String email = auth.getCurrentUser().getEmail().replace("." , "");

                EventData data = new EventData(e_name, e_date, e_time, e_description, e_activity_one, e_activity_two, e_activity_three, e_activity_four, e_activity_five, e_male, e_female , place_s);

                FirebaseDatabase database = FirebaseDatabase.getInstance();

                long current_time_stamp = System.currentTimeMillis();

                database.getReference().child("event").child(email).child(String.valueOf(current_time_stamp)).setValue(data).addOnCompleteListener(listener);




            }
        });

        return view;
    }


    public static void update_place()
    {
        place_txt.setText(place_s);
    }


}
