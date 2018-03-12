package com.example.sridatta.timely.fragment_portal;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.activity.Portal;
import com.example.sridatta.timely.adapter.RecyclerAdapter;
import com.example.sridatta.timely.objects.Faculty;
import com.example.sridatta.timely.objects.LectureSlot;
import com.example.sridatta.timely.objects.SwapRequest;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.ContentValues.TAG;


public class RequestsFragment extends Fragment  {
    private ArrayList<SwapRequest> requestsDetails;
    private ArrayList<Faculty> requestsNames;
    private RecyclerAdapter adapter;
    private RecyclerView rv;
    private ArrayList<String> time;
    private ArrayList<String> date;
    private ArrayList<Integer> profilePics;

    //dialog elements
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
    private Button acceptButton;
    private Button confirmButton;
    //database retrieval
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;




    public RequestsFragment() {
        /* required empty public constructor */
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db= FirebaseFirestore.getInstance();
        requestsDetails=new ArrayList<>();
        requestsNames=new ArrayList<>();
        time=new ArrayList<>();
        date=new ArrayList<>();
        profilePics=new ArrayList<>();

        database();

    }

    private void database()
    {
        //Listener
        db.collection("Requests")
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        accessDetails(value);





                    }
                });



    }
    private void accessDetails(QuerySnapshot value)
    {
        requestsDetails=new ArrayList<>();
        requestsNames=new ArrayList<>();
        time=new ArrayList<>();
        date=new ArrayList<>();
        profilePics=new ArrayList<>();

        int flag=0;
        for (DocumentSnapshot document : value)
        {

            SwapRequest swapRequesttemp = document.toObject(SwapRequest.class);
            //adding the documentid which is used in deletion
            swapRequesttemp.setRequestDocumentId(document.getId());
            Log.d(TAG, document.getId() + " THIS IS SETTING OF REQUEST ID=> DELETE FLAW" + document.getData()+" "+swapRequesttemp.getRequestDocumentId());

            requestsDetails.add(swapRequesttemp);

            flag=1;
            accessNames(swapRequesttemp,value);
        }
        if(flag==0)
        {
            adapter=new RecyclerAdapter(RequestsFragment.this,requestsNames,requestsDetails,profilePics,time,date);
            rv.setAdapter(adapter);
        }

    }
    private void accessNames(SwapRequest swapRequesttemp,QuerySnapshot value)
    {

        db.collection("Faculty").document(swapRequesttemp.getUserSenderId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Faculty facultytemp = documentSnapshot.toObject(Faculty.class);
                accessParameters(facultytemp,value);





            }
        });

    }
    private void accessParameters(Faculty facultytemp,QuerySnapshot value)
    {
        requestsNames.add(facultytemp);
        profilePics.add(R.drawable.album4);
        time.add("4:54pm");
        date.add("05/07/2016");


        Log.d(TAG, "2ND ENTRY IN REQUESTS FRAGMENT ALERT BECOZ OF CHANGE IN DB ");
        if(requestsDetails.size()==value.getDocuments().size()){
            fillRequests();


        }


    }
    private void fillRequests()
    {
        //parameters passing to the adapter

//        Collections.sort(requestsNames, new Comparator<Faculty>() {
//            @Override
//            public int compare(Faculty lhs, Faculty rhs) {
//                return lhs.getFirstName().compareTo(rhs.getFirstName());
//            }
//        });
//        Collections.sort(requestsNames, new Comparator<Faculty>() {
//            @Override
//            public int compare(Faculty lhs, Faculty rhs) {
//                return lhs.getFirstName().compareTo(rhs.getFirstName());
//            }
//        });
        int i, j;
        Faculty temp;
        SwapRequest tempSwap;
        String tempTime;
        String tempDate;
        Integer tempImage;
        for (i = 0; i < requestsNames.size()-1; i++) {

            // Last i elements are already in place
            for (j = 0; j < requestsNames.size() - i - 1; j++) {
                if ((requestsNames.get(j).getFirstName()+" "+requestsNames.get(j).getLastName()).compareToIgnoreCase((requestsNames.get(j+1).getFirstName()+" "+requestsNames.get(j+1).getLastName()))>0) {
                    temp=requestsNames.get(j);
                    requestsNames.set(j,requestsNames.get(j+1));
                    requestsNames.set(j+1,temp);

                    tempSwap=requestsDetails.get(j);
                    requestsDetails.set(j,requestsDetails.get(j+1));
                    requestsDetails.set(j+1,tempSwap);

                    tempTime=time.get(j);
                    time.set(j,time.get(j+1));
                    time.set(j+1,tempTime);

                    tempDate=date.get(j);
                    date.set(j,date.get(j+1));
                    date.set(j+1,tempDate);

                    tempImage=profilePics.get(j);
                    profilePics.set(j,profilePics.get(j+1));
                    profilePics.set(j+1,tempImage);
                }
            }
        }



        adapter = new RecyclerAdapter(RequestsFragment.this,requestsNames, requestsDetails, profilePics, time, date);

        rv.setAdapter(adapter);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {



        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_requests, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);
        adapter = new RecyclerAdapter(RequestsFragment.this,requestsNames, requestsDetails, profilePics, time, date);
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        ItemClickSupport.addTo(rv).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView,  int position, View v) {
                onClickSwapRequest(position);
                return false;
            }
        });

        return rootView;

    }
    private void onClickSwapRequest( int position)
    {

        final Dialog dialogswap = new Dialog(getContext());
        dialogswap.setContentView(R.layout.dialog_swapslot);
        dialogswap.show();// Context, this, etc.


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
        acceptButton=(Button)dialogswap.findViewById(R.id.bv_swap);
        acceptButton.setText("ACCEPT ");

        //setting the contents of the dialog


        tvcourseCode.setText(requestsDetails.get(position).getCourseCode());
        tvCourseName.setText(requestsDetails.get(position).getCourseName());
        tvdegree.setText(requestsDetails.get(position).getDegree());
        tvdepartment.setText(requestsDetails.get(position).getDepartment());
        tvsemester.setText(requestsDetails.get(position).getSemester());
        tvsection.setText(requestsDetails.get(position).getSection());
        tvblock.setText(requestsDetails.get(position).getBlock());
        tvfloor.setText(requestsDetails.get(position).getFloor());
        tvroomNo.setText(requestsDetails.get(position).getRoomNo());
        tvassistingFaculty.setText(requestsDetails.get(position).getAssistingFaculty());
        tvday.setText(requestsDetails.get(position).getDay());
        tvhour.setText(requestsDetails.get(position).getHour());

        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();



        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogswap.dismiss();
                onClickAcceptButton(position);



            }
        });


    }
    private void onClickAcceptButton(  int position)
    {
        final Dialog confirmdialog = new Dialog(getContext()); // Context, this, etc.
        confirmdialog.setContentView(R.layout.dialog_confirmswap);
        confirmdialog.show();
        confirmButton=(Button)confirmdialog.findViewById(R.id.bv_confirmSwap);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "3RD ENTRY IN WHEN ACCEPT BUTTON IS CLICKED");
                onClickConfirmButton(position);
                confirmdialog.dismiss();
                Toast.makeText(getContext(),"Swap Request Accepted Successfully",Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void onClickConfirmButton(  int position)
    {
        db.collection("Faculty").document(userID).collection("currentRequests").document(requestsNames.get(0).getFirstName()+""+requestsNames.get(0).getLastName()+" "+requestsDetails.get(0).getDay()+" "+requestsDetails.get(0).getHour()).set(requestsDetails.get(position));

        Log.d(TAG, "PASSING OF REQUEST ID- DELETE STATEMENT DELETE FLAW 2"+requestsDetails.get(position).getRequestDocumentId());
        //function call
        deleteDocument(position);

    }
    private void deleteDocument(int position)
    {

        //deleting the document from the database
        db.collection("Requests").document(requestsDetails.get(position).getRequestDocumentId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "5TH ENTRY IN DELETION FROM REQUESTS DB");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

    }




}
