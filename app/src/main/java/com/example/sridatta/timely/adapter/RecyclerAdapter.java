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
import com.example.sridatta.timely.fragment_profiler.ReceivedRequestsFragment;
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
    //dialog elements
    private TextView tvcourseCode;
    private TextView tvCourseName;
    private TextView tvdegree;
    private TextView tvdepartment;
    private TextView tvsemester;
    private TextView tvsection;
    private TextView tvblock;
    private TextView tvfloor;
    private TextView tvroomNo;
    private TextView tvassistingFaculty;
    private TextView tvday ;
    private TextView tvhour;
    private Button acceptButton;
    private Button confirmButton;
    //database retrieval
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(RequestsFragment context, ArrayList<Faculty> titles, ArrayList<SwapRequest> details, ArrayList<Integer> images, ArrayList<String> time, ArrayList<String> date) {
               super();
//        for(int i=0;i<this.titles.size();i++)
//        {
//
//            Log.d(TAG,"  PRINTING BEFORE ASSIGNING IN RECYCLER ADAPTER CONSTRUCTOR"+this.titles.get(i));
//        }


        this.titles=new ArrayList<>();
        this.details=new ArrayList<>();
        this.images=new ArrayList<>();
        this.time=new ArrayList<>();
        this.date=new ArrayList<>();


        this.context=context;
        this.titles = titles;
        this.details = details;
        this.images = images;
        this.time=time;
        this.date=date;

        for(int i=0;i<this.titles.size();i++)
        {

            Log.d(TAG,"  PRINTING AFTER ASSIGNING IN RECYCLER ADAPTER CONSTRUCTOR"+this.titles.get(i).getFirstName());
        }


    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public CardView cvSwapRequest;
        public CardView cardItemImage;
        public ImageView ivFacultyImage;
        public TextView tvFacultyTitle;
        public TextView tvFacultyDetail;
        public TextView tvTime;
        public TextView tvDate;
        public MyViewHolder(View v)
        {
            super(v);
            cvSwapRequest = (CardView) v.findViewById(R.id.card_view);
            cardItemImage = (CardView) v.findViewById(R.id.card_item_image);
            //ivFacultyImage =(ImageView) v.findViewById(R.id.iv_faculty_image);
            tvFacultyTitle = (TextView)v.findViewById(R.id.tv_facultyTitle);
            tvFacultyDetail =
                    (TextView)v.findViewById(R.id.tv_facultyDetail);
            tvTime=(TextView)v.findViewById(R.id.tv_time);
            tvDate=(TextView)v.findViewById(R.id.tv_date);
            v.setOnLongClickListener(this);


//            cvSwapRequest.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//
//                    return false;
//                }
//            });


//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    int position=getAdapterPosition();
//                    onClickSwapRequest(position);
//
//
//                    return false;
//                }
//            });


        }
        @Override
        public boolean onLongClick(View v) {
            //nothing
            int position=getAdapterPosition();
            onClickSwapRequest(position);
            return false;

        }
        private void onClickSwapRequest(int position)
        {

            final Dialog dialogswap = new Dialog(context.getContext());
            dialogswap.setContentView(R.layout.dialog_swapslot);
            dialogswap.show();// Context, this, etc.


            tvcourseCode=(TextView) dialogswap.findViewById(R.id.tv_courseCodeDialog);
            tvCourseName=(TextView)dialogswap.findViewById(R.id.tv_courseNameDialog);
            tvdegree=(TextView)dialogswap.findViewById(R.id.tv_degreeDialog);
            tvdepartment=(TextView)dialogswap.findViewById(R.id.tv_departmentDialog);
            tvsemester=(TextView)dialogswap.findViewById(R.id.tv_semesterDialog);
            tvsection=(TextView)dialogswap.findViewById(R.id.tv_sectionDialog);
            tvblock=(TextView)dialogswap.findViewById(R.id.tv_blockDialog);
            tvfloor=(TextView)dialogswap.findViewById(R.id.tv_floorDialog);
            tvroomNo=(TextView)dialogswap.findViewById(R.id.tv_roomNoDialog);
            tvassistingFaculty=(TextView)dialogswap.findViewById(R.id.tv_assistingFacultyDialog);
            tvday=(TextView)dialogswap.findViewById(R.id.tv_dayDialog) ;
            tvhour=(TextView)dialogswap.findViewById(R.id.tv_HourDialog);
            acceptButton=(Button)dialogswap.findViewById(R.id.bv_swap);
            acceptButton.setText("ACCEPT ");

            //setting the contents of the dialog


            tvcourseCode.setText(details.get(position).getCourseCode());
            tvCourseName.setText(details.get(position).getCourseName());
            tvdegree.setText(details.get(position).getDegree());
            tvdepartment.setText(details.get(position).getDepartment());
            tvsemester.setText(details.get(position).getSemester());
            tvsection.setText(details.get(position).getSection());
            tvblock.setText(details.get(position).getBlock());
            tvfloor.setText(details.get(position).getFloor());
            tvroomNo.setText(details.get(position).getRoomNo());
            tvassistingFaculty.setText(details.get(position).getAssistingFaculty());
            tvday.setText(details.get(position).getDay());
            tvhour.setText(details.get(position).getHour());




            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickAcceptButton(position);
                    dialogswap.dismiss();



                }
            });

            return;
        }
        private void onClickAcceptButton(int position)
        {
            final Dialog confirmdialog = new Dialog(context.getContext()); // Context, this, etc.
            confirmdialog.setContentView(R.layout.dialog_confirmswap);
            confirmdialog.show();
            confirmButton=(Button)confirmdialog.findViewById(R.id.bv_confirmSwap);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickConfirmButton(position);

                    confirmdialog.dismiss();
                    Toast.makeText(context.getContext(),"Swap Request Accepted Successfully "+position,Toast.LENGTH_SHORT).show();

                }
            });

            return;
        }
        private void onClickConfirmButton(int position)
        {

            db= FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();
            userID = mAuth.getCurrentUser().getUid();

            Log.d(TAG, "5TH ENTRY IN WHEN ACCEPT BUTTON IS CLICKED IN REQUEST ADAPTER WITH POSITION "+position);
            db.collection("Faculty").document(userID).collection("currentRequests").document(titles.get(position).getFirstName()+" "+titles.get(position).getLastName()+" "+details.get(position).getDay()+" "+details.get(position).getHour()).set(details.get(position));



            deleteDocument(position);
            return;

        }


        private void deleteDocument(int position)
        {
            db= FirebaseFirestore.getInstance();


            //deleting the document from the database
            Log.d(TAG, "6TH ENTRY JUST BEFORE DELETING WITH DOCUMENT ID "+titles.get(position).getFirstName()+" "+titles.get(position).getLastName()+" "+details.get(position).getDay()+" "+details.get(position).getHour());

            db.collection("Requests").document(titles.get(position).getFirstName()+" "+titles.get(position).getLastName()+" "+details.get(position).getDay()+" "+details.get(position).getHour())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DELETED DOCUMENT SUCCESSFULLY");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error deleting document", e);
                        }
                    });
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, titles.size());
            return;
        }



    }



    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
//        v.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//
//                return false;
//            }
//        });
        //v.setLongClickable(true);

        Log.d(TAG," ON CREATE VIEW HOLDER ");
        RecyclerAdapter.MyViewHolder vh = new RecyclerAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder,final int position) {

        holder.tvFacultyTitle.setText(titles.get(position).getFirstName()+" "+titles.get(position).getLastName());
        holder.tvFacultyDetail.setText(details.get(position).getDay()+" "+details.get(position).getHour());
        //holder.ivFacultyImage.setImageResource(images.get(position));
        holder.tvDate.setText(date.get(position));
        holder.tvTime.setText(time.get(position));


        Log.d(TAG," ON BIND VIEW HOLDER ");

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                //int p=holder.getAdapterPosition();
//                onClickSwapRequest(position);
//
//                return false;
//            }
//        });
        return;





    }

    @Override
    public int getItemCount() {

        Log.d(TAG," GET ITEM COUNT "+details.size());
        return details.size();
    }


//    private void onClickSwapRequest( int position)
//    {
//
//        final Dialog dialogswap = new Dialog(context.getContext());
//        dialogswap.setContentView(R.layout.dialog_swapslot);
//        dialogswap.show();// Context, this, etc.
//
//
//        tvcourseCode=(TextView) dialogswap.findViewById(R.id.tv_courseCodeDialog);
//        tvCourseName=(TextView)dialogswap.findViewById(R.id.tv_courseNameDialog);
//        tvdegree=(TextView)dialogswap.findViewById(R.id.tv_degreeDialog);
//        tvdepartment=(TextView)dialogswap.findViewById(R.id.tv_departmentDialog);
//        tvsemester=(TextView)dialogswap.findViewById(R.id.tv_semesterDialog);
//        tvsection=(TextView)dialogswap.findViewById(R.id.tv_sectionDialog);
//        tvblock=(TextView)dialogswap.findViewById(R.id.tv_blockDialog);
//        tvfloor=(TextView)dialogswap.findViewById(R.id.tv_floorDialog);
//        tvroomNo=(TextView)dialogswap.findViewById(R.id.tv_roomNoDialog);
//        tvassistingFaculty=(TextView)dialogswap.findViewById(R.id.tv_assistingFacultyDialog);
//        tvday=(TextView)dialogswap.findViewById(R.id.tv_dayDialog) ;
//        tvhour=(TextView)dialogswap.findViewById(R.id.tv_HourDialog);
//        acceptButton=(Button)dialogswap.findViewById(R.id.bv_swap);
//        acceptButton.setText("ACCEPT ");
//
//        //setting the contents of the dialog
//
//
//        tvcourseCode.setText(details.get(position).getCourseCode());
//        tvCourseName.setText(details.get(position).getCourseName());
//        tvdegree.setText(details.get(position).getDegree());
//        tvdepartment.setText(details.get(position).getDepartment());
//        tvsemester.setText(details.get(position).getSemester());
//        tvsection.setText(details.get(position).getSection());
//        tvblock.setText(details.get(position).getBlock());
//        tvfloor.setText(details.get(position).getFloor());
//        tvroomNo.setText(details.get(position).getRoomNo());
//        tvassistingFaculty.setText(details.get(position).getAssistingFaculty());
//        tvday.setText(details.get(position).getDay());
//        tvhour.setText(details.get(position).getHour());
//
//        db= FirebaseFirestore.getInstance();
//        mAuth = FirebaseAuth.getInstance();
//        userID = mAuth.getCurrentUser().getUid();
//
//
//
//        acceptButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogswap.dismiss();
//                onClickAcceptButton(position);
//
//
//
//            }
//        });
//
//
//    }
//    private void onClickAcceptButton(int position)
//    {
//        final Dialog confirmdialog = new Dialog(context.getContext()); // Context, this, etc.
//        confirmdialog.setContentView(R.layout.dialog_confirmswap);
//        confirmdialog.show();
//        confirmButton=(Button)confirmdialog.findViewById(R.id.bv_confirmSwap);
//        confirmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "3RD ENTRY IN WHEN ACCEPT BUTTON IS CLICKED");
//                onClickConfirmButton(position);
//                confirmdialog.dismiss();
//                Toast.makeText(context.getContext(),"Swap Request Accepted Successfully",Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//    }
//    private void onClickConfirmButton(int position)
//    {
//        db.collection("Faculty").document(userID).collection("currentRequests").document(titles.get(0).getFirstName()+""+titles.get(0).getLastName()+" "+details.get(0).getDay()+" "+details.get(0).getHour()).set(details.get(position));
//
//        Log.d(TAG, "PASSING OF REQUEST ID- DELETE STATEMENT DELETE FLAW 2"+details.get(position).getRequestDocumentId());
//        //function call
//        deleteDocument(position);
//
//    }
//    private void deleteDocument(int position)
//    {
//
//        //deleting the document from the database
//        db.collection("Requests").document(details.get(position).getRequestDocumentId())
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "5TH ENTRY IN DELETION FROM REQUESTS DB");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error deleting document", e);
//                    }
//                });
//
//    }






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