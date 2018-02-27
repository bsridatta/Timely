package com.example.sridatta.timely.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.fragment_profiler.ReceivedRequestsFragment;
import com.example.sridatta.timely.objects.Faculty;
import com.example.sridatta.timely.objects.SwapRequest;

import java.util.ArrayList;

/**
 * Created by Pratyush Srivastava on 10-02-2018.
 */

public class ReceivedRequestsAdapter extends RecyclerView.Adapter<ReceivedRequestsAdapter.MyViewHolder>
{
    private ArrayList<Faculty> titles;
    private ArrayList<SwapRequest> details;
    private ArrayList<Integer> images;
    private ArrayList<String> time;
    private ArrayList<String> date;
    private ReceivedRequestsFragment context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ReceivedRequestsAdapter(ReceivedRequestsFragment context,ArrayList<Faculty> titles, ArrayList<SwapRequest> details, ArrayList<Integer> images,
                                   ArrayList<String> time, ArrayList<String> date)
    {


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



        public MyViewHolder(View v) {
            super(v);
            cvSwapRequest = (CardView) v.findViewById(R.id.card_view);
            cardItemImage = (CardView) v.findViewById(R.id.card_item_image);
            tvFacultyImage = (ImageView) v.findViewById(R.id.iv_faculty_image);
            tvFacultyTitle = (TextView) v.findViewById(R.id.tv_facultyTitle);
            tvFacultyDetail =
                    (TextView) v.findViewById(R.id.tv_facultyDetail);
            tvTime = (TextView) v.findViewById(R.id.tv_time);
            tvDate = (TextView) v.findViewById(R.id.tv_date);

        }

    }



    // Create new views (invoked by the layout manager)
    @Override
    public ReceivedRequestsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ReceivedRequestsAdapter.MyViewHolder vh = new ReceivedRequestsAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ReceivedRequestsAdapter.MyViewHolder holder, final int position) {
        holder.tvFacultyTitle.setText(titles.get(position).getFirstName()+" "+titles.get(position).getLastName());
        holder.tvFacultyDetail.setText(details.get(position).getDay()+" "+details.get(position).getHour());
        holder.tvFacultyImage.setImageResource(images.get(position));
        holder.tvDate.setText(date.get(position));
        holder.tvTime.setText(time.get(position));

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
}
