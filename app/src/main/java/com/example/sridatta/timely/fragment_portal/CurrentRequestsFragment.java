package com.example.sridatta.timely.fragment_portal;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.adapter.CurrentRequestsAdapter;
import com.example.sridatta.timely.adapter.RecyclerAdapter;
import com.example.sridatta.timely.objects.Faculty;
import com.example.sridatta.timely.objects.SwapRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

public class CurrentRequestsFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;
    //dialog elements
    private Button doneSwapButton;
    private TextView dialogQues;



    private ArrayList<SwapRequest> requestsDetails;
    private ArrayList<Faculty> requestsNames;
    private CurrentRequestsAdapter adapter;
    private RecyclerView rv;
    private ArrayList<String> time;
    private ArrayList<String> date;
    private ArrayList<Integer> profilePics;
    public CurrentRequestsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();


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
        db.collection("Faculty").document(userID).collection("currentRequests")
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
            adapter=new CurrentRequestsAdapter(CurrentRequestsFragment.this,requestsNames,requestsDetails,profilePics,time,date);
            rv.setAdapter(adapter);
        }


    }


    private void accessNames(SwapRequest swapRequesttemp,QuerySnapshot value )
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
        Log.d(TAG, "2ND ENTRY IN CurrentREQUESTS FRAGMENT ALERT BECOZ OF CHANGE IN DB ");
        if(requestsDetails.size()==value.getDocuments().size())
        {
            fillCurrentRequests();
        }
    }
    private void fillCurrentRequests()
    {

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
        //parameters passing to the adapter
        adapter = new CurrentRequestsAdapter(CurrentRequestsFragment.this,requestsNames, requestsDetails, profilePics, time, date);

        rv.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_currentrequests, container, false);


        rv = (RecyclerView) view.findViewById(R.id.rv_currentRequests);
        ItemClickSupport.addTo(rv).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                onClickCurrentRequest(position);
                return false;
            }
        });
        rv.setHasFixedSize(true);



        adapter = new CurrentRequestsAdapter(CurrentRequestsFragment.this,requestsNames, requestsDetails, profilePics, time, date);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return view;
    }
    private void onClickCurrentRequest(int position)
    {
        final Dialog dialogdoneswap = new Dialog(getContext());
        dialogdoneswap.setContentView(R.layout.dialog_confirmswap);
        dialogdoneswap.show();// Context, this, etc.

        doneSwapButton=(Button)dialogdoneswap.findViewById(R.id.bv_confirmSwap);
        dialogQues=(TextView)dialogdoneswap.findViewById(R.id.textView);
        dialogQues.setText("Do you want to remove?");


        doneSwapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onClickDoneSwapButton(position);

                dialogdoneswap.dismiss();
                Toast.makeText(getContext(),"Request completed",Toast.LENGTH_SHORT).show();

            }

        });
    }
    private void onClickDoneSwapButton(int position)
    {
        db.collection("Faculty").document(userID).collection("receivedRequests").add(requestsDetails.get(position));
        deleteCurrentRequest(position);

    }
    private void deleteCurrentRequest(int position)
    {
        db.collection("Faculty").document(userID).collection("currentRequests").document(requestsDetails.get(position).getRequestDocumentId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
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
