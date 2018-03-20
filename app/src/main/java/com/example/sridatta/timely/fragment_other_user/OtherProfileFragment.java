package com.example.sridatta.timely.fragment_other_user;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.activity.Profiler;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherProfileFragment extends Fragment {
    private ImageView ivhome;


    public OtherProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_other_profile, container, false);
        String fullname;
        if(this.getArguments()!=null)
        {fullname = this.getArguments().getString("label");
        Log.d(TAG,"the label passed is "+fullname);
        }
        else
        {
            Log.d(TAG,"the label passed is null ");


        }

        ivhome=(ImageView)rootview.findViewById(R.id.iv_other_home);
        ivhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(), Profiler.class);
                startActivity(i);
            }
        });
        return rootview;
    }


}
