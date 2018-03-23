package com.example.sridatta.timely.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sridatta.timely.adapter.CurrentRequestsAdapter;
import com.example.sridatta.timely.fragment_portal.RequestsFragment;

import com.example.sridatta.timely.fragment_portal.TimetableFragment;
import com.example.sridatta.timely.fragment_portal.CurrentRequestsFragment;
import com.example.sridatta.timely.R;
import com.example.sridatta.timely.fragment_profiler.SentRequestsFragment;
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
import java.util.List;

public class Portal extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String userID;
    private AutoCompleteTextView actvFacultySearch;
    private ImageButton ibSearch;
    private ImageButton ibCancel;
    ArrayList<String> facultyNames;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);



        //toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //faculty list retrieval
        facultyNames=new ArrayList<>();
        actvFacultySearch=(AutoCompleteTextView)findViewById(R.id.auto_complete);
        ibSearch=(ImageButton)findViewById(R.id.ibv_search);
        ibCancel=(ImageButton)findViewById(R.id.ibv_cancel);
        db= FirebaseFirestore.getInstance();


        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        //when search button is clicked, auto complete text view pops up



        //this set back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //this is set custom image to back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_account_circle);




        //pager setup
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        getSupportActionBar().setElevation(0.0f);


        //tab setup
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setElevation(4.0f);

        //function to set icons
        setupTabIcons();

        //firestore
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userID=FirebaseAuth.getInstance().getUid();



    }


    public Toolbar getToolbar() {

        return toolbar;
    }
    public void floatbutton(View view)
    {
        Toast.makeText(this,"Floating button clicked ",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    //setting up the 3 dot more options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        // Find the menuItem to add your SubMenu
        MenuItem moreOptions=menu.findItem(R.id.action_item_more);
        //menu.findItem(ReceivedRequestsAdapter.id.action_item_search);

        // Inflating the sub_menu menu this way, will add its menu items
        // to the empty SubMenu you created in the xml
        getMenuInflater().inflate(R.menu.more_options_menu, moreOptions.getSubMenu());



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
                Intent homeIntent = new Intent(this, Profiler.class);
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




                ArrayAdapter adapter = new ArrayAdapter(Portal.this,android.R.layout.simple_spinner_dropdown_item,facultyNames);
                actvFacultySearch.setAdapter(adapter);
                ibSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //code
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

    //setting up the pager view under each tabs and naming the tabs
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),this);
        adapter.addFrag(new TimetableFragment(), "Schedule");
        adapter.addFrag(new RequestsFragment(), "Requests");
        adapter.addFrag(new CurrentRequestsFragment(), "UpComing");

        viewPager.setAdapter(adapter);

    }

    //function to set icons
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_developer_board);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_forum);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_face);
    }


    // Page viewer adapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
//        Context context;

        public ViewPagerAdapter(FragmentManager manager,Context context) {
            super(manager);
//            this.context=context;
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

            //making it null so only icons are there
                  return null;
        }


    }

    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);

    }

}
