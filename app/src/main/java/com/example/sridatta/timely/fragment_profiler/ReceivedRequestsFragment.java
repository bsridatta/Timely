package com.example.sridatta.timely.fragment_profiler;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.adapter.ReceivedRequestsAdapter;
import com.example.sridatta.timely.adapter.SentRequestsAdapter;
import com.example.sridatta.timely.objects.Faculty;
import com.example.sridatta.timely.objects.SwapRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class ReceivedRequestsFragment extends Fragment  {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;


    private ArrayList<SwapRequest> requestsDetails;
    private ArrayList<Faculty> requestsNames;
    private ReceivedRequestsAdapter adapter;
    private RecyclerView rv;
    private ArrayList<String> time;
    private ArrayList<String> date;
    private ArrayList<Integer> profilePics;



    public ReceivedRequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        //array lists of the contents
        requestsDetails=new ArrayList<>();
        requestsNames=new ArrayList<>();
        time=new ArrayList<>();
        date=new ArrayList<>();
        profilePics=new ArrayList<>();
        db.collection("Faculty").document(userID).collection("sentRequests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                SwapRequest swapRequesttemp = document.toObject(SwapRequest.class);
                                //adding the documentid which is used in deletion
                                swapRequesttemp.setRequestDocumentId(document.getId());
                                requestsDetails.add(swapRequesttemp);
                                accessNames(swapRequesttemp,task.getResult());




                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
    private void accessNames(SwapRequest swapRequesttemp,QuerySnapshot querySnapshot)
    {
        db.collection("Faculty").document(swapRequesttemp.getUserSenderId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Faculty facultytemp = documentSnapshot.toObject(Faculty.class);
                requestsNames.add(facultytemp);
                profilePics.add(R.drawable.album4);
                time.add("4:54pm");
                date.add("05/07/2016");
                Log.d(TAG, " ENTRY IN Received REQUESTS FRAGMENT ALERT BECOZ OF CHANGE IN DB ");
                if(querySnapshot.getDocuments().size()==requestsDetails.size()) {
                    fillReceivedRequests();
                }



            }
        });

    }
    private void fillReceivedRequests()
    {
        //parameters passing to the adapter
        adapter = new ReceivedRequestsAdapter(ReceivedRequestsFragment.this,requestsNames, requestsDetails, profilePics, time, date);

        rv.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_receivedrequests, container, false);

        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();



        rv = (RecyclerView) rootView.findViewById(R.id.rv_receivedRequests);
        rv.setHasFixedSize(true);
        adapter = new ReceivedRequestsAdapter(ReceivedRequestsFragment.this,requestsNames, requestsDetails, profilePics, time, date);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;


        }


}