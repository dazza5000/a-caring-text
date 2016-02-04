package com.amicly.acaringtext.addtext;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.amicly.acaringtext.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by daz on 2/2/16.
 */
public class AddTextFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";

    private Button mDateButton;
    private Button mTimeButton;
    private Button mContact;
    private EditText mMessage;

    private AddTextContract.UserActionsListener mActionsListener;


    public static AddTextFragment newInstance() {
        return new AddTextFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_text, container, false);

        mDateButton = (Button) root.findViewById(R.id.add_text_date);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
                dialog.show(fm, DIALOG_DATE);
            }
        });
        mTimeButton = (Button) root.findViewById(R.id.add_text_time);
        mContact = (Button) root.findViewById(R.id.add_text_contact);
        mMessage = (EditText) root.findViewById(R.id.add_text_message);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActionsListener.saveText(mDateButton.getText().toString(),
                        mTimeButton.getText().toString(), mContact.getText().toString(),
                        mMessage.getText().toString());
            }
        });

        return root;

    }


    //DatePicker to choose the day to send the text on
    public static class DatePickerFragment extends DialogFragment{

        private static final String ARG_DATE = "date";

        private DatePicker mDatePicker;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Date date = (Date) getArguments().getSerializable(ARG_DATE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            View v = LayoutInflater.from(getActivity())
                     .inflate(R.layout.dialog_date, null);

            mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
            mDatePicker.init(year, month, day, null);

            return new AlertDialog.Builder(getActivity())
                     .setView(v)
                     .setTitle(R.string.date_picker_title)
                     .setPositiveButton(android.R.string.ok, null)
                     .create();
            }

        private static DatePickerFragment newInstance(Date date) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_DATE, date);
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }

        public static class TimePickerDialogFragment extends DialogFragment
                implements TimePickerDialog.OnTimeSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the current time as the default values for the picker

                // Create a new instance of TimePickerDialog and return it
                return new TimePickerDialog(getActivity(), this, getArguments().getInt("HOURS"), getArguments().getInt("MINUTES"),
                        DateFormat.is24HourFormat(getActivity()));
            }

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

//                TextView habitTime = (TextView) getActivity().findViewById(R.id.text_view_habit_time);
//                habitTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));

            }
        }

}
