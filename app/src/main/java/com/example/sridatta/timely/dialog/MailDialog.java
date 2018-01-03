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

public class MailDialog extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText editMail;
    private TextView actionOk, actionCancel;

    private static final String TAG="Mail Dialog";


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            EditMailDialogListener listener = (EditMailDialogListener) getActivity();
            listener.onFinishEditMailDialog(editMail.getText().toString());
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }

    // 1. Defines the listener interface with a method passing back data result.
    public interface EditMailDialogListener {
        void onFinishEditMailDialog(String inputText);
    }

    public MailDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static MailDialog newInstance(String title) {
        MailDialog frag = new MailDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_mail, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        editMail = (EditText) view.findViewById(R.id.edit_mail);
        actionCancel=(TextView) view.findViewById(R.id.et_mail_action_cancel);
        actionOk=(TextView) view.findViewById(R.id.et_mail_action_ok);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Mail");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        editMail.requestFocus();
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
                String input=editMail.getText().toString();
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
        editMail.setOnEditorActionListener(this);
    }

    public void sendBackResult() {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditMailDialogListener listener = (EditMailDialogListener) getTargetFragment();
        listener.onFinishEditMailDialog(editMail.getText().toString());
        Log.d(TAG,"going to dismiss");
        dismiss();
    }
}
