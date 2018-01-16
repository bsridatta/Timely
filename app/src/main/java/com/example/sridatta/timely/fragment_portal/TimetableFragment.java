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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.adapter.GridViewAdapter;
import com.example.sridatta.timely.objects.LectureSlot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class TimetableFragment extends Fragment {

    //dialog ui components
    private TextView tvSlotName;
    private TextView tvSlotSwap;
    private TextView tvEditSlot;
    private Switch swFree;

    //tvEditSlot dialog ui
    private EditText etNewCourseCode;
    private EditText etNewCourseName;
    private  EditText etNewBatchDetails;
    private  EditText etNewClassLocation;
    private  EditText etNewAssistingFaculty;

    // elements of the slot
    private TextView tvCourseCode;
    private TextView tvCourseName;
    private  TextView tvBatchDetails;
    private  TextView tvClassLocation;
    private  TextView tvAssistingFaculty;

    //database retrieval
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

        lectures= new ArrayList<>();
        String userID = mAuth.getCurrentUser().getUid();


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
                                if(lectures.size()==35){
                                    fillGrid();

                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void fillGrid() {

        gridAdapter = new GridViewAdapter(this, R.layout.card_lectureslot, lectures);
        gridView.setAdapter(gridAdapter);
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

                optionsDialog(i);
                return false;
            }
        });
        return view;
    }


    //   options dialog swap or tvEditSlot
    public void optionsDialog(int i) {

        final Dialog dialog = new Dialog(gridView.getContext()); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_slot_swap);

        int position =i;
        tvSlotName = (TextView) dialog.findViewById(R.id.tv_slotName);
        tvSlotSwap = (TextView) dialog.findViewById(R.id.tv_swap);
        tvEditSlot = (TextView) dialog.findViewById(R.id.tv_edit);
        swFree = (Switch) dialog.findViewById(R.id.switch_free);

        tvSlotSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
        tvEditSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editSlotDialog(position);
            }
        });

        dialog.show();
    }

    //editing dialog
    private void editSlotDialog(int position) {

        final Dialog dialog = new Dialog(gridView.getContext()); // Context, this, etc.

        LectureSlot slotInView= (LectureSlot) gridView.getAdapter().getItem(position);

//
//        String courseCode= slotInView.getFloor();
//        etNewCourseCode=(EditText) dialog.findViewById(R.id.et_new_courseCode);
//        etNewCourseCode.setText(courseCode);


        String courseName;
        String degree;
        String department;
        String semester;
        String section;
        String block;
        String floor;
        String roomNo;
        String assistingFaculty;


        dialog.show();
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
//                String a=Integer.toString(i);
//                String b=Integer.toString(j);
}

