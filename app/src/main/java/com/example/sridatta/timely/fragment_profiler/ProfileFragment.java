package com.example.sridatta.timely.fragment_profiler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.dialog.NumberDialog;
import com.example.sridatta.timely.objects.Faculty;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import static android.R.attr.data;


public class ProfileFragment extends Fragment implements NumberDialog.OnInputSelected{

    private TextView tv;
    private String userID;
    private TextView tvNumber;
    private TextView tvMail;
    private TextView tvDesignation;
    private TextView tvResponsibility;
    private LinearLayout numberLayout;
    private static final String TAG="Profile Fragment";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void sendInput(String input) {
        Log.d(TAG,"sendInput: found incoming input: "+input);
        tvNumber.setText(input);

    }

    public ProfileFragment() {
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
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        tvNumber=(TextView) view.findViewById(R.id.tv_number);
        tvMail=(TextView) view.findViewById(R.id.tv_mail);
        tvDesignation=(TextView)view.findViewById(R.id.tv_desig);
        tvResponsibility=(TextView) view.findViewById(R.id.tv_responsibility);
        numberLayout=(LinearLayout) view.findViewById(R.id.linear_number);

        DocumentReference docRef=db.collection("Faculty").document("g85uXr4GF3Vbwk5jht6XVCvfGjo2");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Faculty faculty = documentSnapshot.toObject(Faculty.class);
                tvNumber.setText(faculty.getPhoneNumber());
                tvMail.setText(faculty.getEmailID());
                tvDesignation.setText(faculty.getDesignation());
                tvResponsibility.setText(faculty.getAdditionalResponsibility());
            }
        });

        numberLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG,"OnLongClick: opening dialog");
                NumberDialog numDialog=new NumberDialog();
                numDialog.setTargetFragment(ProfileFragment.this,1);
                numDialog.show(getFragmentManager(),"number dialog");

                return false;
            }
        });

        return view;
    }


}