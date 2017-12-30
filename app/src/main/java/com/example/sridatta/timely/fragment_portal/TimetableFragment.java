package com.example.sridatta.timely.fragment_portal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.adapter.GridViewAdapter;
import com.example.sridatta.timely.objects.LectureSlot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class TimetableFragment extends Fragment {

    private FirebaseFirestore db;

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    public TimetableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db= FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_timetable, container, false);

        gridView = (GridView) view.findViewById(R.id.gvTimetable);
        gridAdapter = new GridViewAdapter(this, R.layout.card_lectureslot, getData());
        gridView.setAdapter(gridAdapter);



//        gridView.setAdapter(new MyAdapter(view.getContext())); // uses the view to get the context instead of getActivity().

       return view;
    }


//   get data from firestore
    private ArrayList<LectureSlot> getData() {
        final ArrayList<LectureSlot> lectures = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            for(int j=1;j<8;j++){

                String a=Integer.toString(i);

                switch(i){
                    case 1:
                        a= "Mon";
                        break;
                    case 2:
                        a= "Tue";
                        break;
                    case 3:
                        a= "Wed";
                        break;
                    case 4:
                        a= "Thr";
                        break;
                    case 5:
                        a= "Fri";
                        break;
                    default:
                        a=Integer.toString(i);
                        break;

                }

//                String a=Integer.toString(i);
                String b=Integer.toString(j);
                lectures.add(new LectureSlot(a,b));
            }
        }
        return lectures;
    }

}
