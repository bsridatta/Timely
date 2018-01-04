package com.example.sridatta.timely.fragment_portal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sridatta.timely.R;


public class RequestsFragment extends Fragment {


    public RequestsFragment() {
        /* required empty public constructor */

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_requests, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);
        RecyclerAdapter adapter = new RecyclerAdapter(new String[]{"Teacher One","Teacher Two","Teacher Three","Teacher Four","Teacher Five","Teacher Six",
                "Teacher Seven","Teacher Eight","Teacher Nine","Teacher Ten","Teacher Eleven","Teacher Twelve","Teacher Thirteen",
                "Teacher Fourteen"},new String[]{"3rd Feb 2018",
                "22nd March 2018", "22nd March 2018",
                "3rd Feb 2018", "22nd March 2018",
                "3rd Feb 2018", "22nd March 2018",
                "3rd Feb 2018","3rd Feb 2018","3rd Feb 2018","3rd Feb 2018","22nd March 2018","22nd March 2018","22nd March 2018"},new int[]{R.drawable.number2,
                R.drawable.number8,
                R.drawable.number2,
                R.drawable.number8,
                R.drawable.number2,
                R.drawable.number8,
                R.drawable.number2,
                R.drawable.number8,R.drawable.number2,
                R.drawable.number8,R.drawable.number2,
                R.drawable.number8,R.drawable.number2,
                R.drawable.number8});
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

}
