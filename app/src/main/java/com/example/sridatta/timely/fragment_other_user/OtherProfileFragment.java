package com.example.sridatta.timely.fragment_other_user;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.activity.Profiler;
import com.example.sridatta.timely.objects.Faculty;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;

/**
 * Made By Sukrita
 */
public class OtherProfileFragment extends Fragment {
    private ImageView ivhome;

    private TextView tvName;
    private TextView tvNum;
    private TextView tvDept;
    private TextView tvMail;
    private TextView tvDesig;
    private TextView tvAddResponsibility;
    private  String otherUserID;
    private String str[];

    private FirebaseFirestore db;

    private static final String TAG = OtherProfileFragment.class.getSimpleName();

    public OtherProfileFragment() {
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
        View rootview= inflater.inflate(R.layout.fragment_other_profile, container, false);
        db = FirebaseFirestore.getInstance();

        otherUserID=getActivity().getIntent().getExtras().getString("userID");

        if(otherUserID!=null){
            Log.i(TAG,"from on create view "+otherUserID);
        }else
        {
            Log.i(TAG,"from oncreate view null");
        }

        str=otherUserID.split(" ");
        tvName=(TextView) rootview.findViewById(R.id.tv_other_name);
        tvDept=(TextView) rootview.findViewById(R.id.tv_other_department);
        tvDesig=(TextView) rootview.findViewById(R.id.tv_other_Designation);
        tvAddResponsibility=(TextView) rootview.findViewById(R.id.tv_other_AdditionalResponsibility);
        tvMail=(TextView) rootview.findViewById(R.id.tv_other_mail);
        tvNum=(TextView) rootview.findViewById(R.id.tv_other_number);

        db.collection("Faculty")
                .whereEqualTo("firstName", str[0]).whereEqualTo("lastName",str[1])
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Faculty faculty=document.toObject(Faculty.class);
                                tvName.setText(faculty.getFirstName()+" "+faculty.getLastName());
                                tvDept.setText(faculty.getDepartment());
                                tvNum.setText(faculty.getPhoneNumber());
                                tvMail.setText(faculty.getEmailID());
                                tvDesig.setText(faculty.getDesignation());
                                tvAddResponsibility.setText(faculty.getAdditionalResponsibility());
                                Log.i(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.i(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        ivhome=(ImageView)rootview.findViewById(R.id.iv_other_home);
        ivhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(), Profiler.class);
                startActivity(i);
            }
        });
        return rootview;
    }



}
