package com.example.sridatta.timely;

import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.core.Context;
import com.firebase.ui.database.FirebaseIndexListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BattleGround extends AppCompatActivity {

    //declarations
    private Firebase mRootRef;

    private RecyclerView mSchedule;
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_ground);

        //mRootRef=new Firebase("https://timely-508d7.firebaseio.com/Faculty");

        //firebase database references
        firebaseDatabase=FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("Faculty/sridatta/timetable");
        //mDatabase = FirebaseDatabase.getInstance().getReference("Faculty/sridatta/timetable");


        Toast.makeText(BattleGround.this,mDatabase.toString(),Toast.LENGTH_SHORT).show();

        //recycler view for time table schedule
        mSchedule=(RecyclerView)findViewById(R.id.rv_schedule);
        mSchedule.setHasFixedSize(true);
        //making it horizontal in the vertical nested scroll view
        //mSchedule.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));

      mLayoutManager=new GridLayoutManager(this,7,GridLayoutManager.HORIZONTAL,false);
//        mLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true);
        mSchedule.setLayoutManager(mLayoutManager);
//        mLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true);

//            mSchedule.setLayoutManager(new GridLayoutManager(this,7,GridLayoutManager.HORIZONTAL,false));

        Toast.makeText(BattleGround.this,"going in",Toast.LENGTH_SHORT).show();

        //mSchedule.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<schedule,scheduleViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<schedule, scheduleViewHolder>(
               //parameters
                schedule.class,
                R.layout.lecture_card,
                scheduleViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(scheduleViewHolder viewHolder, schedule model, int position) {
//                scheduleViewHolder viewHolder
//                schedule model
//                int position
                viewHolder.setCourseId(model.getCourseId());
                viewHolder.setCourseName(model.getCourseName());
                viewHolder.setClassName(model.getClassName());
                viewHolder.setClassRoom(model.getClassRoom());
                viewHolder.setHelpingFaculty(model.getHelpingFaculty());

            }
        };

        mSchedule.setAdapter(firebaseRecyclerAdapter);

    }

    // view holder for the recycler view i.e schedule
    public static class scheduleViewHolder extends RecyclerView.ViewHolder{

        //creating a view to easily manipulate the view
        View mView;

        public scheduleViewHolder(View itemView) {
            super(itemView);
            //passing desired view using mView
            mView=itemView;
        }

        //for setting the data
        public void setCourseId(String courseId){
            //linking it to the card view
            //the view is apparently stored in mview

            TextView tvCourseId=(TextView) mView.findViewById(R.id.tv_courseId);
            tvCourseId.setText(courseId);
        }
        public void setCourseName(String courseName){
            //linking it to the card view
            //the view is apparently stored in mview
            TextView tvCourseName=(TextView) mView.findViewById(R.id.tv_courseName);
            tvCourseName.setText(courseName);
        }
        public void setClassName(String className){
            //linking it to the card view
            //the view is apparently stored in mview
            TextView tvClassName=(TextView) mView.findViewById(R.id.tv_className);
            tvClassName.setText(className);
        }
        public void setClassRoom(String classRoom){
            //linking it to the card view
            //the view is apparently stored in mview
            TextView tvClassRoom=(TextView) mView.findViewById(R.id.tv_classRoom);
            tvClassRoom.setText(classRoom);
        }
        public void setHelpingFaculty(String helpingFaculty){
            //linking it to the card view
            //the view is apparently stored in mview
            TextView tvHelpingFaculty=(TextView) mView.findViewById(R.id.tv_helpingFaculty);
            tvHelpingFaculty.setText(helpingFaculty);
        }
    }
}

