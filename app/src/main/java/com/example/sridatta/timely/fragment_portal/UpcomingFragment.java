package com.example.sridatta.timely.fragment_portal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sridatta.timely.R;

public class UpcomingFragment extends Fragment {
    public UpcomingFragment() {
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

        View view=inflater.inflate(R.layout.fragment_upcoming, container, false);

        Bundle userID= getActivity().getIntent().getExtras();



        return view;
    }
}
