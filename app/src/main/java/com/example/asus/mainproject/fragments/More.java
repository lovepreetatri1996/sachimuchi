package com.example.asus.mainproject.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.mainproject.MainActivity;
import com.example.asus.mainproject.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class More extends Fragment {


    TextView signout_txt ;

    public More() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_more, container, false);

        signout_txt = v.findViewById(R.id.sign_out);

        signout_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.signOut();

                getActivity().finish();

                Intent i = new Intent(getActivity() , MainActivity.class);

                startActivity(i);
            }
        });

        return  v;
    }

}
