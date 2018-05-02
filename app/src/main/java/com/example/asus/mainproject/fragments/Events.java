package com.example.asus.mainproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.mainproject.Events_Detailes;
import com.example.asus.mainproject.R;
import com.example.asus.mainproject.dataModels.EventData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Events extends Fragment {

    public Events() {
        // Required empty public constructor
    }



    private RecyclerView recyclerView ;

    List<EventData> list ;

     Events.Adpater adapter ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));

        list = new ArrayList<>();

        adapter = new Events.Adpater();

        recyclerView.setAdapter(adapter);

        get_data();
    }

    private void get_data()

    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("event").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for ( DataSnapshot snap : dataSnapshot.getChildren() )
                {
                    for ( DataSnapshot snap2 : snap.getChildren())
                    {
                        EventData eventData = snap2.getValue(EventData.class);


                        list.add(eventData);
                    }
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    public class view_holder extends RecyclerView.ViewHolder
    {

        public TextView event_name , event_date , event_location ;
        public LinearLayout event_cell;

        public view_holder(View itemView) {
            super(itemView);

            event_name = itemView.findViewById(R.id.event_name);

            event_date = itemView.findViewById(R.id.date_);

            event_location = itemView.findViewById(R.id.location_);
            event_cell=itemView.findViewById(R.id.event_cell);
        }
    }

    public class Adpater extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(getContext()).inflate(R.layout.single_event_cell , parent , false));
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {


            final EventData data = list.get(position);

            holder.event_name.setText(data.name);

            holder.event_date.setText(data.date);

            holder.event_location.setText(data.location);

            holder.event_cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(getContext() , Events_Detailes.class);

                    i.putExtra("name" , data.name);
                    i.putExtra("date" , data.date);
                    i.putExtra("location",data.location);
                    i.putExtra("time" , data.time);
                    i.putExtra("event_description" , data.description);
                    i.putExtra("activityone" , data.activity_one);
                    i.putExtra("activitytwo" , data.activity_two);
                    i.putExtra("activitythree" , data.activity_three);
                    i.putExtra("activityfour" , data.activity_four);
                    i.putExtra("activityfive" , data.activity_five);
                    i.putExtra("event_male" , data.e_male);
                    i.putExtra("event_female" , data.e_female);

                    startActivity(i);



                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
