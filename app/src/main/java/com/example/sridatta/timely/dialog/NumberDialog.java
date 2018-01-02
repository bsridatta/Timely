package com.example.sridatta.timely.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sridatta.timely.R;

/**
 * Created by Sukrita on 12/30/2017.
 */

public class NumberDialog extends android.support.v4.app.DialogFragment {

    public interface OnInputSelected{
        void sendInput(String input);
    }
    public OnInputSelected onInputSelected;

    private EditText editNumber;
    private TextView actionOk, actionCancel;
    private static final String TAG="Number Dialog";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.number_dialog, container, false);

        actionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        actionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: number edited");
                String input=editNumber.getText().toString();
                if(!input.equals("")) {
                    onInputSelected.sendInput(input);
                }
                getDialog().dismiss();

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onInputSelected=(OnInputSelected)getTargetFragment();

        }catch(ClassCastException e){
            Log.e(TAG,"onAttach: ClassCastException: "+e.getMessage());
        }
    }
}
