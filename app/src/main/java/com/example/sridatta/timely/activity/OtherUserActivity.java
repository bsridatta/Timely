package com.example.sridatta.timely.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.fragment_other_user.OtherProfileFragment;
import com.example.sridatta.timely.fragment_other_user.OtherTimeTableFragment;
import com.example.sridatta.timely.fragment_profiler.ProfileFragment;
import com.example.sridatta.timely.fragment_profiler.ReceivedRequestsFragment;
import com.example.sridatta.timely.fragment_profiler.SentRequestsFragment;
import com.example.sridatta.timely.objects.Faculty;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OtherUserActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FirebaseFirestore db;
    private static final String TAG = OtherUserActivity.class.getSimpleName();

    private String userID;
    private FirebaseAuth mAuth;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();


        name=getIntent().getExtras().getString("userID");
        Log.i(TAG,name);

        //pager setup
        viewPager = (ViewPager) findViewById(R.id.other_viewpager);
        setupViewPager(viewPager);

        //tab setup
        tabLayout = (TabLayout) findViewById(R.id.other_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setElevation(4.0f);
        setupTabIcons();

        userID=FirebaseAuth.getInstance().getUid();

    }


    public String getUserID() {
        return userID;
    }

    @Override
    public void onResume() {
        super.onResume();
    }




    //setting up the pager view under each tabs and naming the tabs
    private void setupViewPager(ViewPager viewPager) {
        OtherUserActivity.ViewPagerAdapter adapter = new OtherUserActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OtherProfileFragment(), " USER PROFILE");
        adapter.addFrag(new OtherTimeTableFragment(),"USER TIMETABLE");
        //adapter.addFrag(new SentRequestsFragment(), "SENT REQUESTS");
        viewPager.setAdapter(adapter);
    }
    //function to set icons
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_face);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_developer_board);
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
            return null;
        }
    }



    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);

    }



}
