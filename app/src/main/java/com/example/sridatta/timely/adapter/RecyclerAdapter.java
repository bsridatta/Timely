package com.example.sridatta.timely.adapter;

/**
 * Created by Shade on 5/9/2016.
 */

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sridatta.timely.R;

/**
 * Created by Pratyush Srivastava on 06-12-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private String[] titles;
    private String[] details;
    private int[] images;
    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(String[] titles, String[] details, int[] images) {
        this.titles = titles;
        this.details = details;
        this.images = images;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;
        public View horizontal;
        public MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            itemImage = (ImageView)v.findViewById(R.id.item_image);
            itemTitle = (TextView)v.findViewById(R.id.item_title);
            itemDetail =
                    (TextView)v.findViewById(R.id.item_detail);
            horizontal=v.findViewById(R.id.black_line);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Class swapped with " + titles[position],
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }

    }



    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.itemTitle.setText(titles[position]);
        holder.itemDetail.setText(details[position]);
        holder.itemImage.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}