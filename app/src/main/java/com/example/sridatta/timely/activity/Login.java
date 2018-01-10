package com.example.sridatta.timely.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sridatta.timely.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    //  FIREBASE declaration
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //  1 choose login or register
    private Button btnChooseRegister;
    private Button btnChooseLogin;

    //login
    private EditText mEmailField;
    private EditText mPasswordField;
    private TextView tvErrorMessage;
    private Button btnLogin;

    //loading while logging in
    private ProgressBar progressBar;

    // three ui modules choosing login and loading animation
    private ConstraintLayout loadLayout;
    private ConstraintLayout childConstraint;
    private ConstraintLayout parentConstraint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //UI declarations
        loadLayout =(ConstraintLayout)findViewById(R.id.load_layout);
        childConstraint=(ConstraintLayout)findViewById(R.id.child_constraint);
        parentConstraint=(ConstraintLayout)findViewById(R.id.parent_constraint);

        // displaying only choosing
        loadLayout.setVisibility(View.INVISIBLE);
        childConstraint.setVisibility(View.INVISIBLE);


        //logging in
        mEmailField=(EditText)findViewById(R.id.et_email);
        mPasswordField=(EditText)findViewById(R.id.et_password);
        tvErrorMessage =(TextView) findViewById(R.id.tv_error_message);

        //loading
        progressBar= (ProgressBar) findViewById(R.id.pbPogressDialog);


        //choose login or register
        btnChooseLogin=(Button) findViewById(R.id.btn_chooseLogIn);
        btnChooseRegister=(Button) findViewById(R.id.btn_chooseRegister);

        btnChooseRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login.this,Register.class));
            }
        });
        btnChooseLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentConstraint.setVisibility(view.INVISIBLE);
                childConstraint.setVisibility(View.VISIBLE);

            }
        });

        //login
        btnLogin=(Button)findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoad();
                //directing it to the login procedure
                login();
            }
        });


        // FIREBASE
        //check if user is authenticated
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                  if yes send him inside
                if(firebaseAuth.getCurrentUser()!=null){

                    String userID = firebaseAuth.getCurrentUser().getUid();
                    startActivity(new Intent(Login.this,Portal.class).putExtra("userID",userID));

                }
            }
        };
    }

    //loading
    public void startLoad(){

        childConstraint.setVisibility(View.INVISIBLE);
        loadLayout.setVisibility(View.VISIBLE);
    }

    public void stopLoad(){

        childConstraint.setVisibility(View.VISIBLE);
        loadLayout.setVisibility(View.INVISIBLE);
    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    // user def function to  initialise the login process once button is pressed
    private void login(){
        // auth strings to pass into the Firebase auth functions
        String email=mEmailField.getText().toString();
        String password=mPasswordField.getText().toString();

        // check if the fields are left blank
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty((password))){

            tvErrorMessage.setText("Enter valid input to login");
            stopLoad();
        }
        //if not blank
        else{

            //passing into auth function, and checking if the login is completed
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                //checking if the login is completed successfully or not
                    if(!task.isSuccessful()){
//                        Toast.makeText(Login.this,"unable to login",Toast.LENGTH_SHORT).show();
                        tvErrorMessage.setText("unable to login");
                        stopLoad();
                    }
                    else{
                        Toast.makeText(Login.this,"logging in",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}
