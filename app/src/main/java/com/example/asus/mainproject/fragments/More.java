package com.example.asus.mainproject.fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.mainproject.EditEventInfo;
import com.example.asus.mainproject.Feedback;
import com.example.asus.mainproject.FirstPage;
import com.example.asus.mainproject.MainActivity;
import com.example.asus.mainproject.R;
import com.example.asus.mainproject.editprofile;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class More extends Fragment {


    TextView signout_txt;
    TextView share;
    TextView feedback,edit_event_info;
    TextView rate;

    LinearLayout edit_profile_layout ;

    public More() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_more, container, false);

        edit_profile_layout = v.findViewById(R.id.edit_profile_layout);

        edit_profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext() , editprofile.class);

                startActivity(i);
            }
        });
        edit_event_info=v.findViewById(R.id.edit_event_info);
        edit_event_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),EditEventInfo.class);
                startActivity(i);
            }
        });

        signout_txt = v.findViewById(R.id.sign_out);

        signout_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.signOut();

                getActivity().finish();

                Intent i = new Intent(getActivity(), MainActivity.class);

                startActivity(i);
            }
        });



        share=v.findViewById(R.id.share_app);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent u=new Intent(Intent.ACTION_SEND);
                u.setType("text/plain");
                String sharebody="your body here";
                String sharesub="your subject here";
                u.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                u.putExtra(Intent.EXTRA_TEXT,sharebody);
                startActivity(Intent.createChooser(u,"share app using"));

            }
        });
        feedback=v.findViewById(R.id.feedback_app);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),Feedback.class);
                startActivity(i);
            }
        });
        rate=v.findViewById(R.id.rate_us);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail/u/0/#inbox")));
            }
            catch (ActivityNotFoundException e)
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail/u/0/#inbox")));
            }
            }
        });


        return v;



    }


}
