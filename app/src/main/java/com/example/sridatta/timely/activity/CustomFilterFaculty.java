package com.example.sridatta.timely.activity;

import android.widget.Filter;

import com.example.sridatta.timely.objects.Faculty;

import java.util.ArrayList;

/**
 * Created by Pratyush Srivastava on 23/12/2017.
 */
public class CustomFilterFaculty extends Filter {

    FacultyAdapter adapter;
    ArrayList<Faculty> filterList;


    public CustomFilterFaculty(ArrayList<Faculty> filterList, FacultyAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Faculty> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getFirstName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

       adapter.players= (ArrayList<Faculty>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
