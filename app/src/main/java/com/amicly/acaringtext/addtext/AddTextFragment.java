package com.amicly.acaringtext.addtext;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import java.util.GregorianCalendar;

/**
 * Created by daz on 2/2/16.
 */
public class AddTextFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

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
                dialog.setTargetFragment(AddTextFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });
        mTimeButton = (Button) root.findViewById(R.id.add_text_time);
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(new Date());
                dialog.setTargetFragment(AddTextFragment.this, REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
        }
        });
        mContact = (Button) root.findViewById(R.id.add_text_contact);
        mMessage = (EditText) root.findViewById(R.id.add_text_message);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_notes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mActionsListener.saveText(mDateButton.getText().toString(),
//                        mTimeButton.getText().toString(), mContact.getText().toString(),
//                        mMessage.getText().toString());
            }
        });

        return root;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDateButton.setText(date.toString());
        }

        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mTimeButton.setText(date.toString());
        }
    }

    //DatePicker to choose the day to send the text on
    public static class DatePickerFragment extends DialogFragment{

        public static final String EXTRA_DATE =
                "com.amicly.acaringtext.date";

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
//                     .setTitle(R.string.date_picker_title)
                     .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             int year = mDatePicker.getYear();
                             int month = mDatePicker.getMonth();
                             int day = mDatePicker.getDayOfMonth();
                             Date date = new GregorianCalendar(year, month, day).getTime();
                             sendResult(Activity.RESULT_OK, date);
                         }
                     })
                     .create();
            }

        private static DatePickerFragment newInstance(Date date) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_DATE, date);
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setArguments(args);
            return fragment;
        }

        private void sendResult(int resultCode, Date date) {
            if (getTargetFragment() == null) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DATE, date);

            getTargetFragment().
                    onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }

    //DatePicker to choose the day to send the text on
    public static class TimePickerFragment extends DialogFragment{

        public static final String EXTRA_TIME =
                "com.amicly.acaringtext.time";

        private static final String ARG_TIME = "time";

        private TimePicker mTimePicker;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Date date = (Date) getArguments().getSerializable(ARG_TIME);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            View v = LayoutInflater.from(getActivity())
                    .inflate(R.layout.dialog_time, null);

            mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
            mTimePicker.setCurrentHour(hour);
            mTimePicker.setCurrentMinute(minute);

            return new AlertDialog.Builder(getActivity())
                    .setView(v)
//                    .setTitle(R.string.time_picker_title)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int hour = mTimePicker.getCurrentHour();
                            int minute = mTimePicker.getCurrentMinute();
                            Date date = new GregorianCalendar(0, 0, 0, hour, minute).getTime();
                            sendResult(Activity.RESULT_OK, date);
                        }
                    })
                    .create();
        }

        private static TimePickerFragment newInstance(Date date) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_TIME, date);
            TimePickerFragment fragment = new TimePickerFragment();
            fragment.setArguments(args);
            return fragment;
        }

        private void sendResult(int resultCode, Date date) {
            if (getTargetFragment() == null) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TIME, date);

            getTargetFragment().
                    onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }

}
