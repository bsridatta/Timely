package com.example.sridatta.timely.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sridatta.timely.R;

/**
 * Created by Sukrita on 03-01-2018.
 */

public class NumberDialog extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText editNumber;
    private TextView actionOk, actionCancel;

    private static final String TAG="Number Dialog";


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            EditNumberDialogListener listener = (EditNumberDialogListener) getActivity();
            listener.onFinishEditNumberDialog(editNumber.getText().toString());
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }

    // 1. Defines the listener interface with a method passing back data result.
    public interface EditNumberDialogListener {
        void onFinishEditNumberDialog(String inputText);
    }

    public NumberDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static NumberDialog newInstance(String title) {
        NumberDialog frag = new NumberDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_number, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        editNumber = (EditText) view.findViewById(R.id.edit_number);
        actionCancel=(TextView) view.findViewById(R.id.et_number_action_cancel);
        actionOk=(TextView) view.findViewById(R.id.et_number_action_ok);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Number");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        editNumber.requestFocus();
        actionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: closing dialog");
                dismiss();

            }
        });

        actionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: number edited");
                String input=editNumber.getText().toString();
                if(!input.equals("")) {
                    Log.d(TAG,"sending back result");
                    sendBackResult();
                }
                Log.d(TAG,"going to dismiss again");
                dismiss();


            }
        });

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        // 2. Setup a callback when the "Done" button is pressed on keyboard
        editNumber.setOnEditorActionListener(this);
    }

    public void sendBackResult() {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditNumberDialogListener listener = (EditNumberDialogListener) getTargetFragment();
        listener.onFinishEditNumberDialog(editNumber.getText().toString());
        Log.d(TAG,"going to dismiss");
        dismiss();
    }
}
