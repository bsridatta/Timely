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
import com.example.sridatta.timely.fragment_portal.ItemClickSupport;
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


    private CurrentRequestsFragment context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CurrentRequestsAdapter(CurrentRequestsFragment context,ArrayList<Faculty> titles, ArrayList<SwapRequest> details, ArrayList<Integer> images, ArrayList<String> time, ArrayList<String> date) {
 //       super();
        this.titles = titles;
        this.details = details;
        this.images = images;
        this.time=time;
        this.date=date;
        this.context=context;
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


        public MyViewHolder(View v) {
            super(v);
            cvSwapRequest = (CardView) v.findViewById(R.id.card_view);
            cardItemImage = (CardView) v.findViewById(R.id.card_item_image);
            tvFacultyImage =(ImageView)v.findViewById(R.id.iv_faculty_image);
            tvFacultyTitle = (TextView)v.findViewById(R.id.tv_facultyTitle);
            tvFacultyDetail =
                    (TextView)v.findViewById(R.id.tv_facultyDetail);
            tvTime=(TextView)v.findViewById(R.id.tv_time);
            tvDate=(TextView)v.findViewById(R.id.tv_date);
        }

    }



    // Create new views (invoked by the layout manager)
    @Override
    public CurrentRequestsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(context.getContext())
                .inflate(R.layout.card_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CurrentRequestsAdapter.MyViewHolder vh = new CurrentRequestsAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CurrentRequestsAdapter.MyViewHolder holder, final int position) {

        holder.tvFacultyTitle.setText(titles.get(position).getFirstName()+" "+titles.get(position).getLastName());
        holder.tvFacultyDetail.setText(details.get(position).getDay()+" "+details.get(position).getHour());
        holder.tvFacultyImage.setImageResource(images.get(position));
        holder.tvDate.setText(date.get(position));
        holder.tvTime.setText(time.get(position));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}
