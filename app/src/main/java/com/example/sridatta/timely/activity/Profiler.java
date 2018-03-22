package com.example.sridatta.timely.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sridatta.timely.fragment_other_user.OtherProfileFragment;
import com.example.sridatta.timely.fragment_profiler.SentRequestsFragment;
import com.example.sridatta.timely.fragment_profiler.ProfileFragment;
import com.example.sridatta.timely.R;
import com.example.sridatta.timely.fragment_profiler.ReceivedRequestsFragment;
import com.example.sridatta.timely.objects.Faculty;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Profiler extends AppCompatActivity {
    private TextView facultyName;
    private TextView facultyDepartment;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AutoCompleteTextView actvFacultySearch;
    private ImageButton ibSearch;
    private ImageButton ibCancel;
    ArrayList<String> facultyNames;
    private FirebaseFirestore db;

    private static final String TAG = Profiler.class.getSimpleName();

    private String userID;
    private FirebaseAuth mAuth;
    private ArrayList<Faculty> userFaculty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getCurrentUser().getUid();
        userFaculty=new ArrayList<>();
        facultyDepartment=(TextView) findViewById(R.id.tv_department);
        facultyName=(TextView) findViewById(R.id.tv_name);


        db.collection("Faculty").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Faculty faculty = documentSnapshot.toObject(Faculty.class);
                displayNameAndDept(faculty);


            }
        });



        //faculty list retrieval
        facultyNames=new ArrayList<>();
        actvFacultySearch=(AutoCompleteTextView)findViewById(R.id.auto_complete_profiler);
        ibSearch=(ImageButton)findViewById(R.id.ibv_search_profiler);
        ibCancel=(ImageButton)findViewById(R.id.ibv_cancel_profiler);
        db= FirebaseFirestore.getInstance();
        //toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        //this set back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //this is set custom image to back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_home);

        //pager setup
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        getSupportActionBar().setElevation(0.0f);

        //tab setup
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setElevation(4.0f);

        //can pass as extra or  get with firebase auth
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userID = extras.getString("userID");
            // and get whatever type user account id is
        }

        userID=FirebaseAuth.getInstance().getUid();

        // / retrieve the data using keyName
        //firestore
//        // Access a Cloud Firestore instance from your Activity



        FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }
    public void displayNameAndDept(Faculty faculty)
    {
        userFaculty.add(faculty);
        facultyName.setText(userFaculty.get(0).getFirstName()+" "+userFaculty.get(0).getLastName());
        facultyDepartment.setText(userFaculty.get(0).getDepartment());


    }


    public String getUserID() {
        return userID;
    }


    //setting up the 3 dot more options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        // Find the menuItem to add your SubMenu
        MenuItem moreOptions=menu.findItem(R.id.action_item_more);
        // Inflating the sub_menu menu this way, will add its menu items
        // to the empty SubMenu you created in the xml
        getMenuInflater().inflate(R.menu.more_options_menu, moreOptions.getSubMenu());

        //TODO signout option

        return true;
    }

    //what to do when 3 dot more options menu is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_item_more:
                //do something

                break;

            case R.id.signOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,Login.class));
                break;

            case R.id.editexcelsheet:
                Intent excelIntent = new Intent(this, Excel.class);
                startActivity(excelIntent);
                break;

            case android.R.id.home:
                Intent homeIntent = new Intent(this, Portal.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                break;
            case R.id.action_item_search:
                toolbar.setVisibility(View.GONE);
                actvFacultySearch.setVisibility(View.VISIBLE);
                ibSearch.setVisibility(View.VISIBLE);
                ibCancel.setVisibility(View.VISIBLE);
                actvFacultySearch.setDropDownBackgroundResource(R.color.windowBackground);

                db.collection("Faculty")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        Faculty faculty=document.toObject(Faculty.class);
                                        facultyNames.add(faculty.getFirstName()+" "+faculty.getLastName());
                                        //Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    //Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });


                ArrayAdapter adapter = new ArrayAdapter(Profiler.this,android.R.layout.simple_spinner_dropdown_item,facultyNames);
                actvFacultySearch.setAdapter(adapter);
                ibSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        //code
//                        Bundle bundle = new Bundle();
//                        bundle.putString("label", actvFacultySearch.getText().toString());
//                        OtherProfileFragment fragobj = new OtherProfileFragment();
//                        fragobj.setArguments(bundle);
//                        gotoOtherUser();



                    }
                });
                ibCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toolbar.setVisibility(View.VISIBLE);
                        actvFacultySearch.setVisibility(View.GONE);
                        ibSearch.setVisibility(View.GONE);
                        ibCancel.setVisibility(View.GONE);
                    }
                });



                break;


        }

        return super.onOptionsItemSelected(item);
    }
    public void gotoOtherUser(){
        Intent i=new Intent(Profiler.this,OtherUserActivity.class);
        // i.putExtra("label",actvFacultySearch.getText().toString());
        startActivity(i);
    }


    //setting up the pager view under each tabs and naming the tabs
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ProfileFragment(), "PROFILE");
        adapter.addFrag(new SentRequestsFragment(), "SENT REQUESTS");
        adapter.addFrag(new ReceivedRequestsFragment(), "RECEIVED REQUESTS");
        viewPager.setAdapter(adapter);
    }



    // Page viewer adapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);

    }



}
