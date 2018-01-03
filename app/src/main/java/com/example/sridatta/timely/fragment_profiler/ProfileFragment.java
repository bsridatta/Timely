package com.example.sridatta.timely.fragment_profiler;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sridatta.timely.dialog.DesignationDialog;
import com.example.sridatta.timely.dialog.MailDialog;
import com.example.sridatta.timely.dialog.NumberDialog;
import com.example.sridatta.timely.dialog.ResponsibilityDialog;
import com.example.sridatta.timely.R;
import com.example.sridatta.timely.objects.Faculty;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import static android.R.attr.data;


public class ProfileFragment extends Fragment implements NumberDialog.EditNumberDialogListener,MailDialog.EditMailDialogListener,DesignationDialog.EditDesignationDialogListener,ResponsibilityDialog.EditResponsibilityDialogListener {

    //widgets declaration
    private TextView tv;
    private String userID;
    private TextView tvNumber;
    private TextView tvMail;
    private TextView tvDesignation;
    private TextView tvResponsibility;
    private LinearLayout numberLayout;
    private LinearLayout mailLayout;
    private LinearLayout designationLayout;
    private LinearLayout responsibilityLayout;

    //firebase instance
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("Faculty").document("g85uXr4GF3Vbwk5jht6XVCvfGjo2");
    private static final String TAG = ProfileFragment.class.getSimpleName();

    // Call this method to launch the edit number dialog
    private void showEditNumberDialog() {
        FragmentManager fm = getFragmentManager();
        NumberDialog numberdialog = NumberDialog.newInstance("Number dialog");
        // SETS the target fragment for use later when sending results
        numberdialog.setTargetFragment(ProfileFragment.this, 300);
        numberdialog.show(fm, "fragment_edit_number");
    }

    // Call this method to launch the edit mail dialog
    private void showEditMailDialog() {
        FragmentManager fm = getFragmentManager();
        MailDialog maildialog = MailDialog.newInstance("Mail dialog");
        // SETS the target fragment for use later when sending results
        maildialog.setTargetFragment(ProfileFragment.this, 300);
        maildialog.show(fm, "fragment_edit_mail");
    }

    private void showEditDesignationDialog() {
        FragmentManager fm = getFragmentManager();
        DesignationDialog designationdialog = DesignationDialog.newInstance("Designation dialog");
        // SETS the target fragment for use later when sending results
        designationdialog.setTargetFragment(ProfileFragment.this, 300);
        designationdialog.show(fm, "fragment_edit_designation");
    }

    private void showEditResponsibiltyDialog() {
        FragmentManager fm = getFragmentManager();
        ResponsibilityDialog responsibiltydialog = ResponsibilityDialog.newInstance("Responsibility dialog");
        // SETS the target fragment for use later when sending results
        responsibiltydialog.setTargetFragment(ProfileFragment.this, 300);
        responsibiltydialog.show(fm, "fragment_edit_responsibility");
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
        mailLayout=(LinearLayout) view.findViewById(R.id.linear_mail);
        designationLayout=(LinearLayout) view.findViewById(R.id.linear_designation);
        responsibilityLayout=(LinearLayout) view.findViewById(R.id.linear_responsibility);

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

        //setting OnLongClick for the fields to update
        numberLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showEditNumberDialog();
                return false;
            }
        });

        mailLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showEditMailDialog();
                return false;
            }
        });

        designationLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showEditDesignationDialog();
                return false;
            }
        });

        responsibilityLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showEditResponsibiltyDialog();
                return false;
            }
        });


        return view;
    }

    // This is called when the dialog is completed and the results have been passed
    @Override
    public void onFinishEditNumberDialog(String inputText) {
        tvNumber.setText(inputText);
        Toast.makeText(getContext(), "You have just changed your number to " + inputText, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFinishEditMailDialog(String inputText) {
        tvMail.setText(inputText);
        Toast.makeText(getContext(), "You have just changed your mail to " + inputText, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFinishEditDesignationDialog(String inputText) {
        tvDesignation.setText(inputText);
        Toast.makeText(getContext(), "You have just changed your Designation to " + inputText, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFinishEditResponsibilityDialog(String inputText) {
        tvResponsibility.setText(inputText);
        Toast.makeText(getContext(), "You have just changed your Responsibility to " + inputText, Toast.LENGTH_SHORT).show();

    }

}