package com.example.sridatta.timely.adapter;


import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.fragment_portal.RequestsFragment;
import com.example.sridatta.timely.objects.Faculty;
import com.example.sridatta.timely.objects.SwapRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.ContentValues.TAG;

/**
 * Created by Pratyush Srivastava on 06-12-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>  {
    private ArrayList<Faculty> titles;
    private ArrayList<SwapRequest> details;
    private ArrayList<Integer> images;
    private ArrayList<String> time;
    private ArrayList<String> date;
    private RequestsFragment context;
//
//
//    //dialog elements
//    private TextView tvcourseCode;
//    private TextView tvCourseName;
//    private TextView tvdegree;
//    private TextView tvdepartment;
//    private TextView tvsemester;
//    private TextView tvsection;
//    private TextView tvblock;
//    private TextView tvfloor;
//    private TextView tvroomNo;
//    private TextView tvassistingFaculty;
//    private TextView tvday ;
//    private TextView tvhour;
//    private Button acceptButton;
//    private Button confirmButton;
//    //database retrieval
//    private FirebaseFirestore db;
//    private FirebaseAuth mAuth;
//    private String userID;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(RequestsFragment context,ArrayList<Faculty> titles, ArrayList<SwapRequest> details, ArrayList<Integer> images, ArrayList<String> time, ArrayList<String> date) {
     //   super();
        this.context=context;
        this.titles = titles;
        this.details = details;
        this.images = images;
        this.time=time;
        this.date=date;

    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cvSwapRequest;
        public CardView cardItemImage;
        public ImageView tvFacultyImage;
        public TextView tvFacultyTitle;
        public TextView tvFacultyDetail;
        public TextView tvTime;
        public TextView tvDate;


        public MyViewHolder(View v)
        {
            super(v);
            cvSwapRequest = (CardView) v.findViewById(R.id.card_view);
            cardItemImage = (CardView) v.findViewById(R.id.card_item_image);
            tvFacultyImage =(ImageView)v.findViewById(R.id.iv_faculty_image);
            tvFacultyTitle = (TextView)v.findViewById(R.id.tv_facultyTitle);
            tvFacultyDetail =
                    (TextView)v.findViewById(R.id.tv_facultyDetail);
            tvTime=(TextView)v.findViewById(R.id.tv_time);
            tvDate=(TextView)v.findViewById(R.id.tv_date);


//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    final int position=getAdapterPosition();
//                    onClickSwapRequest(position);
//                    return true;
//                }
//            });


        }

    }



    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(context.getContext())
                .inflate(R.layout.card_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecyclerAdapter.MyViewHolder vh = new RecyclerAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder( RecyclerAdapter.MyViewHolder holder,final int position) {

        holder.tvFacultyTitle.setText(titles.get(position).getFirstName()+" "+titles.get(position).getLastName());
        holder.tvFacultyDetail.setText(details.get(position).getDay()+" "+details.get(position).getHour()+" "
                +this.details.get(position).getSemester()+" "+details.get(position).getSection());
        holder.tvFacultyImage.setImageResource(images.get(position));
        holder.tvDate.setText(date.get(position));
        holder.tvTime.setText(time.get(position));





    }

    @Override
    public int getItemCount() {
        return details.size();
    }


//    public static interface ClickListener{
//        public void onClick(View view,int position);
//        public void onLongClick(View view,int position);
//    }
//    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
//
//        private ClickListener clicklistener;
//        private GestureDetector gestureDetector;
//
//        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){
//
//            this.clicklistener=clicklistener;
//            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return false;
//                }
//
//                @Override
//                public void onLongPress(MotionEvent e) {
//                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
//                    if(child!=null && clicklistener!=null){
//                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
//                    }
//                }
//            });
//        }
//
//        @Override
//        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//            View child=rv.findChildViewUnder(e.getX(),e.getY());
//            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
//                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
//            }
//
//            return false;
//        }
//
//        @Override
//        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//        }
//
//        @Override
//        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//        }
//    }

}