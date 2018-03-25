package com.example.sridatta.timely.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.sridatta.timely.R;
import com.example.sridatta.timely.fragment_other_user.OtherTimeTableFragment;
import com.example.sridatta.timely.fragment_portal.TimetableFragment;
import com.example.sridatta.timely.objects.LectureSlot;

import java.util.ArrayList;

/**
 * Created by Sukrita on 3/25/2018.
 */

public class OtherGridViewAdapter extends ArrayAdapter{
    private static final String TAG = "TAG";
    private OtherTimeTableFragment context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public OtherGridViewAdapter(OtherTimeTableFragment context, int layoutResourceId, ArrayList data) {
        super(context.getActivity(), layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        GridViewAdapter.ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context.getActivity()).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new GridViewAdapter.ViewHolder();
            //row.setBackgroundColor(Color.parseColor(""));

            holder.dayHour=(TextView) row.findViewById(R.id.tv_dayHour);

            holder.courseCode=(TextView) row.findViewById(R.id.tv_courseCode);
            holder.courseName=(TextView) row.findViewById(R.id.tv_courseName);

            holder.batchDetails=(TextView) row.findViewById(R.id.tv_batchDetails);
            //merging  holder.section;  holder.degree;  holder.department;   holder.semester;

            holder.classLocation=(TextView) row.findViewById(R.id.tv_classLocation);
//            // merging     holder.roomNo;   holder.block;   holder.floor;

            holder.assistingFaculty=(TextView) row.findViewById(R.id.tv_helpingFaculty);

            row.setTag(holder);

        }

        else {
            holder = (GridViewAdapter.ViewHolder) row.getTag();
        }

        LectureSlot item = (LectureSlot) data.get(position);


        String dayHour=item.getDay()+item.getHour();
        row.setBackgroundColor(Color.parseColor(item.getColorOfTheSlot()));
        if(item.getColorOfTheSlot().equals("#FFFF99"))
            row.setElevation(0);


        holder.dayHour.setText(dayHour);


        holder.courseCode.setText(item.getCourseCode());
        holder.courseName.setText(item.getCourseName());


        String batchDetails = item.getDegree()+" "+item.getDepartment()+" "+ item.getSemester() +" "+ item.getSection() ;
        holder.batchDetails.setText(batchDetails);

        String classLocation = item.getBlock()+" "+item.getFloor()+" "+item.getRoomNo();
        holder.classLocation.setText(classLocation);

        holder.assistingFaculty.setText(item.getAssistingFaculty());


        return row;
    }

    static class ViewHolder {

        TextView dayHour;

        TextView courseCode;
        TextView courseName;

        TextView batchDetails;
        TextView classLocation;

        TextView assistingFaculty;
    }
}
