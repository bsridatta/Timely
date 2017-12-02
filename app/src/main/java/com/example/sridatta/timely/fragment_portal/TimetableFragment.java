package com.example.sridatta.timely.fragment_portal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.activity.RecyclerViewFirestore;

public class TimetableFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    public TimetableFragment() {
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
        View view= inflater.inflate(R.layout.fragment_timetable, container, false);

        //recycler view code

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_timetable);





        return view;


    }

}
