package com.example.sridatta.timely.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.sridatta.timely.R;
import com.example.sridatta.timely.objects.Faculty;

import java.util.ArrayList;

/**
 * Created by Pratyush Srivastava on 23/12/2017.
 */
public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.MyHolder> implements Filterable {

    ArrayList<Faculty> players,filterList;
    CustomFilterFaculty filter;


    public FacultyAdapter(ArrayList<Faculty> players)
    {
        this.players=players;
        this.filterList=players;
    }


    @Override
    public FacultyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //CONVERT XML TO VIEW ONBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_faculty,null);

        //HOLDER
        MyHolder holder=new MyHolder(v);

        return holder;
    }

    //DATA BOUND TO VIEWS
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        //BIND DATA
        holder.nameTxt.setText(players.get(position).getFirstName()+" "+players.get(position).getLastName());
    }

    //GET TOTAL NUM OF PLAYERS
    @Override
    public int getItemCount() {
        return players.size();
    }

    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilterFaculty(filterList,this);
        }

        return filter;
    }
    //inner class
    public class MyHolder extends RecyclerView.ViewHolder {

        //OUR VIEWS
        TextView nameTxt;
        View horizontalFaculty;
        public MyHolder(View view) {
            super(view);

            nameTxt= (TextView) view.findViewById(R.id.nameTxt);
            horizontalFaculty=view.findViewById(R.id.horizontal_line_faculty);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "You have clicked " + players.get(position).getFirstName()+" "+players.get(position).getLastName(),
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }

    }

}
