package com.example.birdquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class QuestionNumberPickerDialogFragment extends DialogFragment {

    private static final String TAG = QuestionNumberPickerDialogFragment.class.getSimpleName();
    //Bundle Key constants
    private static final String NUMBER_PICKER_MAX_INT_KEY = "MaxValue";
    private static final String NUMBER_PICKER_MIN_INT_KEY = "MinValue";
    //Instance of the interface to deliver the action events
    private QuestionNumberPickedListener mListener;
    //Stores the number picked by the user
    private int mSelectedValue;

    //Creating a static instance of the DialogFragment
    static QuestionNumberPickerDialogFragment newInstance(int minValue, int maxValue) {
        QuestionNumberPickerDialogFragment numberPickerDialog = new QuestionNumberPickerDialogFragment();
        //Storing Argument values: START
        Bundle args = new Bundle();
        args.putInt(NUMBER_PICKER_MIN_INT_KEY, minValue);
        args.putInt(NUMBER_PICKER_MAX_INT_KEY, maxValue);
        numberPickerDialog.setArguments(args);
        //Storing Argument values: END
        return numberPickerDialog; //Returning the instance
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (QuestionNumberPickedListener) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException", e);
            throw new ClassCastException(context.toString() + " must implement QuestionNumberPickedListener");
        }

    }

    //Attaching the Activity to the fragment
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (QuestionNumberPickedListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException", e);
            throw new ClassCastException(activity.toString() + " must implement QuestionNumberPickedListener");
        }
    }

    //Creating the Dialog to be shown
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Building the Alert dialog for the picker
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        //Retrieving the Layout Inflater
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        //Retrieving the Question Number Picker view
        //(passing null since the view will be attached to dialog)
        View layoutView = layoutInflater.inflate(R.layout.question_number_picker_layout, null);

        //Retrieving the NumberPicker
        NumberPicker numberPicker = layoutView.findViewById(R.id.number_picker_id);
        //Setting the Max Value
        numberPicker.setMaxValue(getArguments().getInt(NUMBER_PICKER_MAX_INT_KEY));
        //Setting the Min Value
        numberPicker.setMinValue(getArguments().getInt(NUMBER_PICKER_MIN_INT_KEY));

        //Defaulting the selected value as 2
        mSelectedValue = 2;
        numberPicker.setValue(mSelectedValue);

        numberPicker.setWrapSelectorWheel(true);
        //Registering the value change listener on NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Updating the selected value to the New Value
                mSelectedValue = newVal;
            }
        });

        //Setting Positive Button and its listener
        Button positiveButton = layoutView.findViewById(R.id.qd_positive_button_id);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passing the positive button event to the host activity
                mListener.onDialogPositiveClick(mSelectedValue);
                dismiss();
            }
        });

        //Setting Negative Button and its listener
        Button negativeButton = layoutView.findViewById(R.id.rename_reference_button);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passing the negative button event to the host activity
                mListener.onDialogNegativeClick();
            }
        });

        //Setting the view on dialog
        dialogBuilder.setView(layoutView);

        //Returning the dialog created
        return dialogBuilder.create();
    }

    //Called after the Dialog has been created/displayed
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Preventing cancellation through back button or on touch of the screen
        getDialog().setCancelable(false);
    }

    /**
     * Activity that creates the instance of this {@link DialogFragment}
     * needs to implement this interface to receive event callbacks
     */
    interface QuestionNumberPickedListener {
        /**
         * Callback Method of {@link QuestionNumberPickedListener}
         * invoked when the user clicks on the SET Button
         *
         * @param selectedValue The Number picked by the user through the Number Picker
         */
        void onDialogPositiveClick(int selectedValue);

        /**
         * Callback Method of {@link QuestionNumberPickedListener}
         * invoked when the user clicks on the CANCEL Button
         */
        void onDialogNegativeClick();
    }
}