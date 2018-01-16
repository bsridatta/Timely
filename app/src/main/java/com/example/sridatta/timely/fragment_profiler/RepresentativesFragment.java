package com.example.sridatta.timely.fragment_profiler;


import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sridatta.timely.R;
import com.google.firebase.firestore.FirebaseFirestore;


public class RepresentativesFragment extends Fragment  {
    FirebaseFirestore db  ;



    public RepresentativesFragment() {
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

        View rootView = inflater.inflate(R.layout.fragment_representative, container, false);

        return rootView;


        }


}