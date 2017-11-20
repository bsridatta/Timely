package com.example.sridatta.timely;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    //  FIREBASE declaration
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //  UI
    private EditText mEmailField;
    private EditText mPasswordField;
    private TextView tvErrorMessage;
    private Button btnLogin;
    private ProgressBar progressBar;

    private ConstraintLayout loadLayout;
    private ConstraintLayout childConstraint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //UI declarations
        loadLayout =(ConstraintLayout)findViewById(R.id.load_layout);
        childConstraint=(ConstraintLayout)findViewById(R.id.child_constraint);
        loadLayout.setVisibility(View.INVISIBLE);

        mEmailField=(EditText)findViewById(R.id.et_email);
        mPasswordField=(EditText)findViewById(R.id.et_password);
        btnLogin=(Button)findViewById(R.id.btn_login);
        tvErrorMessage =(TextView) findViewById(R.id.tv_error_message);

        progressBar= (ProgressBar) findViewById(R.id.pbPogressDialog);

        // FIREBASE
        //check if user is authenticated
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                  if yes send him inside
                if(firebaseAuth.getCurrentUser()!=null){

                    String userID = firebaseAuth.getCurrentUser().getUid();
                    startActivity(new Intent(Login.this,Profiler.class).putExtra("userID",userID));

                }
            }
        };

        //setting onclick for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoad();
                //directing it to the login procedure
                login();
            }
        });

    }


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
