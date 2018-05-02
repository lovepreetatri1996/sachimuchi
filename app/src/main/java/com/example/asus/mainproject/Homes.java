package com.example.asus.mainproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.asus.mainproject.fragments.Add_Event_Detailes;
import com.example.asus.mainproject.fragments.Events;
import com.example.asus.mainproject.fragments.More;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import hotchemi.android.rate.AppRate;

public class Homes extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);
        AppRate.with(this)
                .setInstallDays(0)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);
        AppRate.with(this).clearAgreeShowDialog();


        final FragmentManager fm = getSupportFragmentManager();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.more)
                {
                    FragmentTransaction ft = fm.beginTransaction();

                    ft.replace(R.id.main_frame , new More());

                    ft.commit();
                }

                if(item.getItemId() == R.id.event)
                {
                    FragmentTransaction ft = fm.beginTransaction();

                    ft.replace(R.id.main_frame , new Events());

                    ft.commit();
                }












                if(item.getItemId() == R.id.add)
                {

                    FragmentTransaction ft = fm.beginTransaction();

                    ft.replace(R.id.main_frame , new Add_Event_Detailes());

                    ft.commit();

                }


                return  true;
            }
        });

    }



}
