package com.example.sridatta.timely.fragment_portal;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.adapter.GridViewAdapter;
import com.example.sridatta.timely.adapter.SentRequestsAdapter;
import com.example.sridatta.timely.objects.Faculty;
import com.example.sridatta.timely.objects.LectureSlot;
import com.example.sridatta.timely.objects.SwapRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class TimetableFragment extends Fragment {

    //dialog ui components
    private TextView tvSlotName;
    private TextView tvSlotSwap;
    private TextView tvEditSlot;
    private Switch swFree;

    private String userid;
    ArrayList<Faculty> facultytemp;
    // elements of the slot


    private TextView tvcourseCode;
    private TextView tvCourseName;
    private TextView tvdegree;
    private TextView tvdepartment;
    private TextView tvsemester;
    private TextView tvsection;
    private TextView tvblock;
    private TextView tvfloor;
    private TextView tvroomNo;
    private TextView tvassistingFaculty;
    private TextView tvday ;
    private TextView tvhour;
    private Button swapButton;
    private Button confirmButton;

    //database retrieval
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;

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
        userID = mAuth.getCurrentUser().getUid();

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
        facultytemp=new ArrayList<>();
        db.collection("Faculty").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Faculty faculty = documentSnapshot.toObject(Faculty.class);
                getUserName(faculty);

            }
        });




    }
    private void getUserName(Faculty faculty){
        facultytemp.add(faculty);

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
                dialog.dismiss();
                swapSlotDialog(position);
            }
        });
        tvEditSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        dialog.show();
    }

    //editing dialog
    private void swapSlotDialog(int position) {

        final Dialog dialogswap = new Dialog(gridView.getContext()); // Context, this, etc.
        dialogswap.setContentView(R.layout.dialog_swapslot);

        dialogswap.show();
        userid=mAuth.getInstance().getUid();

        LectureSlot slotInView = (LectureSlot) gridView.getAdapter().getItem(position);
        SwapRequest swapRequest=new SwapRequest(slotInView.getDay(), slotInView.getHour(), slotInView.getCourseCode(), slotInView.getCourseName(),
                slotInView.getDegree(), slotInView.getDepartment(), slotInView.getSemester(),
                slotInView.getSection(), slotInView.getBlock(), slotInView.getFloor(), slotInView.getRoomNo(),
                slotInView.getAssistingFaculty(),userid,"temporary");


//
//        String courseCode= slotInView.getFloor();
//        etNewCourseCode=(EditText) dialog.findViewById(ReceivedRequestsAdapter.id.et_new_courseCode);
//        etNewCourseCode.setText(courseCode);

        //initialising
        tvcourseCode=(TextView) dialogswap.findViewById(R.id.tv_courseCodeDialog);
        tvCourseName=(TextView)dialogswap.findViewById(R.id.tv_courseNameDialog);
        tvdegree=(TextView)dialogswap.findViewById(R.id.tv_degreeDialog);
        tvdepartment=(TextView)dialogswap.findViewById(R.id.tv_departmentDialog);
        tvsemester=(TextView)dialogswap.findViewById(R.id.tv_semesterDialog);
        tvsection=(TextView)dialogswap.findViewById(R.id.tv_sectionDialog);
        tvblock=(TextView)dialogswap.findViewById(R.id.tv_blockDialog);
        tvfloor=(TextView)dialogswap.findViewById(R.id.tv_floorDialog);
        tvroomNo=(TextView)dialogswap.findViewById(R.id.tv_roomNoDialog);
        tvassistingFaculty=(TextView)dialogswap.findViewById(R.id.tv_assistingFacultyDialog);
        tvday=(TextView)dialogswap.findViewById(R.id.tv_dayDialog) ;
        tvhour=(TextView)dialogswap.findViewById(R.id.tv_HourDialog);
        swapButton=(Button)dialogswap.findViewById(R.id.bv_swap);
        //setting the text views
        tvcourseCode.setText(slotInView.getCourseCode());
        tvCourseName.setText(slotInView.getCourseName());
        tvdegree.setText(slotInView.getDegree());
        tvdepartment.setText(slotInView.getDepartment());
        tvsemester.setText(slotInView.getSemester());
        tvsection.setText(slotInView.getSection());
        tvblock.setText(slotInView.getBlock());
        tvfloor.setText(slotInView.getFloor());
        tvroomNo.setText(slotInView.getRoomNo());
        tvassistingFaculty.setText(slotInView.getAssistingFaculty());
        tvday.setText(slotInView.getDay());
        tvhour.setText(slotInView.getHour());
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSwapButton(swapRequest);
                dialogswap.dismiss();


            }
        });




    }
    private void onClickSwapButton(SwapRequest swapRequest)
    {
        final Dialog confirmdialog = new Dialog(gridView.getContext()); // Context, this, etc.
        confirmdialog.setContentView(R.layout.dialog_confirmswap);
        confirmdialog.show();
        confirmButton=(Button)confirmdialog.findViewById(R.id.bv_confirmSwap);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pushing into the firebase
                onClickConfirmSwapButton(swapRequest);

                confirmdialog.dismiss();

                Toast.makeText(gridView.getContext(),"Swap Request Sent Successfully",Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void onClickConfirmSwapButton(SwapRequest swapRequest)
    {



        db.collection("Faculty").document(userid).collection("sentRequests").add(swapRequest);
        db.collection("Requests").document(facultytemp.get(0).getFirstName()+""+facultytemp.get(0).getLastName()+" "+swapRequest.getDay()+" "+swapRequest.getHour()).set(swapRequest);
        Log.d(TAG, " =>  1st ENTRY IN ON CLICK OF SWAP BUTTON"  );
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

