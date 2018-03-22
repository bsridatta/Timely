package com.example.sridatta.timely.adapter;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.fragment_portal.CurrentRequestsFragment;

import com.example.sridatta.timely.objects.Faculty;
import com.example.sridatta.timely.objects.SwapRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Pratyush Srivastava on 11-02-2018.
 */

public class CurrentRequestsAdapter extends RecyclerView.Adapter<CurrentRequestsAdapter.MyViewHolder>{

    private ArrayList<Faculty> titles;
    private ArrayList<SwapRequest> details;
    private ArrayList<Integer> images;
    private ArrayList<String> time;
    private ArrayList<String> date;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;

    //dialog elements
    private Button doneSwapButton;
    private TextView dialogQues;


    private CurrentRequestsFragment context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CurrentRequestsAdapter(CurrentRequestsFragment context,ArrayList<Faculty> titles, ArrayList<SwapRequest> details, ArrayList<Integer> images, ArrayList<String> time, ArrayList<String> date) {
         super();


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

            Log.d(TAG,"  (Current Adapter)PRINTING AFTER ASSIGNING IN RECYCLER ADAPTER CONSTRUCTOR"+this.titles.get(i).getFirstName());
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


        public MyViewHolder(View v) {
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

        }

        @Override
        public boolean onLongClick(View v) {
            int p=getAdapterPosition();
            onClickCurrentRequest(p);
            return false;
        }
        private void onClickCurrentRequest(int position)
        {
        final Dialog dialogdoneswap = new Dialog(context.getContext());
        dialogdoneswap.setContentView(R.layout.dialog_confirmswap);
        dialogdoneswap.show();// Context, this, etc.

        doneSwapButton=(Button)dialogdoneswap.findViewById(R.id.bv_confirmSwap);
        dialogQues=(TextView)dialogdoneswap.findViewById(R.id.textView);
        dialogQues.setText("Do you want to remove ?");


        doneSwapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDoneSwap(position);

                dialogdoneswap.dismiss();
                Toast.makeText(context.getContext(),"Request completed",Toast.LENGTH_SHORT).show();

            }

        });
            return;
    }
    private void onClickDoneSwap(int position)
    {
        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        db.collection("Faculty").document(userID).collection("receivedRequests").document(titles.get(position).getFirstName()+" "+titles.get(position).getLastName()+" "+details.get(position).getDay()+" "+details.get(position).getHour()).set(details.get(position));




        deleteCurrentRequest(position);
    }


    private void deleteCurrentRequest(int position)
    {
        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();


        db.collection("Faculty").document(userID).collection("currentRequests").document(titles.get(position).getFirstName()+" "+titles.get(position).getLastName()+" "+details.get(position).getDay()+" "+details.get(position).getHour())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error deleting document", e);
                    }
                });
        return;
    }

    }



    // Create new views (invoked by the layout manager)
    @Override
    public CurrentRequestsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CurrentRequestsAdapter.MyViewHolder vh = new CurrentRequestsAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(CurrentRequestsAdapter.MyViewHolder holder, final int position) {

        holder.tvFacultyTitle.setText(titles.get(position).getFirstName()+" "+titles.get(position).getLastName());
        holder.tvFacultyDetail.setText(details.get(position).getDay()+" "+details.get(position).getHour());
        //holder.ivFacultyImage.setImageResource(images.get(position));
        holder.tvDate.setText(date.get(position));
        holder.tvTime.setText(time.get(position));

    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}
