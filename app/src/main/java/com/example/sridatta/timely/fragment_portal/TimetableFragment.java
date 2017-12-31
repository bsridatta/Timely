package com.example.sridatta.timely.fragment_portal;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.adapter.GridViewAdapter;
import com.example.sridatta.timely.objects.LectureSlot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class TimetableFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    private ArrayList<LectureSlot> lectures;

    public TimetableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        String userID = mAuth.getCurrentUser().getUid();

        Query query= FirebaseFirestore.getInstance()
                .collection("Faculty").document(userID).collection("TimeTable");


        lectures = new ArrayList<>();


        db.collection("Faculty").document(userID).collection("TimeTable")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                                LectureSlot l = document.toObject(LectureSlot.class);
                                lectures.add(l);
                                Log.d(TAG, document.getId() + " => " +l + "  size "+lectures.size());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_timetable, container, false);

        gridView = (GridView) view.findViewById(R.id.gvTimetable);
        gridAdapter = new GridViewAdapter(this, R.layout.card_lectureslot, lectures);
        gridView.setAdapter(gridAdapter);


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                openDialog();
                return false;
            }
        });
//        gridView.setAdapter(new MyAdapter(view.getContext())); // uses the view to get the context instead of getActivity().
       return view;
    }

    public void openDialog() {
        final Dialog dialog = new Dialog(gridView.getContext()); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_slot_swap);

        dialog.show();
    }


    private ArrayList<LectureSlot> getData() {

        ArrayList<LectureSlot> lectures = new ArrayList<>();
        String userID = mAuth.getCurrentUser().getUid();

        return lectures;
    }





//    private ArrayList<LectureSlot> getData(){
//
//        ArrayList<LectureSlot> lectures = new ArrayList<>();
//
//
//        String userID = mAuth.getCurrentUser().getUid();
//
//        db.collection("Faculty").document(userID).collection("TimeTable")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//
//                                LectureSlot l = document.toObject(LectureSlot.class);
//                                lectures.add(l);
//                                Log.d(TAG, document.getId() + " => " +l + "  size "+lectures.size());
//                                if(lectures.size()==35){
//                                    addToGrid();
//                                }
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//
//
//
//
//        return lectures;
//    }
//
//    private void addToGrid() {
//    }


//
//                String a=Integer.toString(i);
//
//
//                switch(i){
//                    case 1:
//                        a= "Mon ";
//                        break;
//                    case 2:
//                        a= "Tue ";
//                        break;
//                    case 3:
//                        a= "Wed ";
//                        break;
//                    case 4:
//                        a= "Thr ";
//                        break;
//                    case 5:
//                        a= "Fri ";
//                        break;
//                    default:
//                        a=Integer.toString(i);
//                        break;
//
//                }
//
////                String a=Integer.toString(i);
//                String b=Integer.toString(j);
}

