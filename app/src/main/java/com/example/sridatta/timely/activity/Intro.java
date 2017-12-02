package com.example.sridatta.timely.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.example.sridatta.timely.R;

import java.util.ArrayList;

public class Intro extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    private TextInputEditText tiFirstName;
    private TextInputEditText tiLastName;
    private EditText etEmailId;
    private EditText etPhoneNumber;
    private Spinner spDepartment;
    private Spinner spDesignation;
    private Button btnSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //spinner for department
        spDepartment = (Spinner) findViewById(R.id.sp_department);
        spDepartment= (Spinner) findViewById(R.id.sp_designation);

        //spinner click listener
        spDepartment.setOnItemSelectedListener(this);
        spDesignation.setOnItemSelectedListener(this);

        //spinner elements
//        //department
//        Resource res=getResources()
//        ArrayList deptList= res.get;
//        ArrayAdapter<String> adapterDepartment = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item,);

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

