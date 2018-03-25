package com.example.sridatta.timely.fragment_other_user;


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
import com.example.sridatta.timely.adapter.OtherGridViewAdapter;
import com.example.sridatta.timely.objects.Faculty;
import com.example.sridatta.timely.objects.LectureSlot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherTimeTableFragment extends Fragment {

    private String otherUserID;
    private String str[];

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;

    private ArrayList<LectureSlot> lectures;

    ArrayList<Faculty> facultytemp;
    private OtherGridViewAdapter gridAdapter;
    private GridView gridView;


    public OtherTimeTableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        lectures= new ArrayList<>();
        userID = mAuth.getCurrentUser().getUid();

        otherUserID=getActivity().getIntent().getExtras().getString("userID");

        if(otherUserID!=null){
            Log.i(TAG,"from on create view "+otherUserID);
        }else
        {
            Log.i(TAG,"from oncreate view null");
        }

        str=otherUserID.split(" ");

        db.collection("Faculty")
                .whereEqualTo("firstName", str[0]).whereEqualTo("lastName",str[1])
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Faculty faculty=document.toObject(Faculty.class);
                                Log.i(TAG, document.getId() + " => " + document.getData());
                                database(document.getId());
                            }
                        } else {
                            Log.i(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
//        getActivity().runOnUiThread(new Runnable(){
//            public void run() {
//                //If there are stories, add them to the table
//                database();
//
//                try {
//
//                } catch (final Exception ex) {
//                    Log.i("---","Exception in thread");
//                }
//            }
//        });

    }


    private void database(String otherDocumentID){
        db.collection("Faculty").document(otherDocumentID).collection("TimeTable")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                                LectureSlot l = document.toObject(LectureSlot.class);
                                lectures.add(l);
                                Log.d(TAG, document.getId() + " => " +l + "  size "+lectures.size());
                                if(lectures.size()==35){
                                    fillGrid();

                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return;
    }

    private void fillGrid() {

        gridAdapter = new OtherGridViewAdapter(this, R.layout.card_lectureslot, lectures);
        gridView.setAdapter(gridAdapter);
        return;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_other_time_table, container, false);

        gridView = (GridView) view.findViewById(R.id.gvOtherTimetable);
        gridAdapter = new OtherGridViewAdapter(this, R.layout.card_lectureslot, lectures);
        gridView.setAdapter(gridAdapter);

        return view;
    }

}
