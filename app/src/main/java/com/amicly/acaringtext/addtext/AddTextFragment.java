package com.amicly.acaringtext.addtext;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amicly.acaringtext.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by daz on 2/2/16.
 */
public class AddTextFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_CONTACT = 2;

    private Button mDateButton;
    private Button mTimeButton;
    private Button mContactButton;
    private EditText mMessage;

    private String mDate;
    private String mTime;

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
        mContactButton = (Button) root.findViewById(R.id.add_text_contact);

        final Intent pickContact = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);

        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact,PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mContactButton.setEnabled(false);
        }

        mContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });



        mMessage = (EditText) root.findViewById(R.id.add_text_message);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_notes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mActionsListener.saveText(mDateButton.getText().toString(),
//                        mTimeButton.getText().toString(), mContactButton.getText().toString(),
//                        mMessage.getText().toString());
            }
        });

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mDate = savedInstanceState.getString(KEY_DATE);
            mTime = savedInstanceState.getString(KEY_TIME);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_DATE, mDate);
        outState.putString(KEY_TIME, mTime);
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

        if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME
            };

            Cursor c = getActivity().getContentResolver()
                    .query(contactUri, queryFields, null, null, null);

            try {
                if (c.getCount() == 0){
                    return;
                }
                Cursor cursor = null;
                String phoneNumber = "";
                List<String> allNumbers = new ArrayList<>();
                int phoneIdx = 0;
                try {
                    Uri result = data.getData();
                    String id = result.getLastPathSegment();
                    cursor = getActivity().getContentResolver().query(Phone.CONTENT_URI, null,
                            Phone.CONTACT_ID + "=?", new String[] { id }, null);
                    phoneIdx = cursor.getColumnIndex(Phone.DATA);
                    if (cursor.moveToFirst()) {
                        while (cursor.isAfterLast() == false) {
                            phoneNumber = cursor.getString(phoneIdx);
                            allNumbers.add(phoneNumber);
                            cursor.moveToNext();
                        }
                    } else {
                        //no results actions
                    }
                } catch (Exception e) {
                    //error actions
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }

                    final CharSequence[] items = allNumbers.toArray(new String[allNumbers.size()]);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Choose a number");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            String selectedNumber = items[item].toString();
                            selectedNumber = selectedNumber.replace("-", "");
                            Toast.makeText(getActivity(), selectedNumber, Toast.LENGTH_SHORT);
                            mContactButton.setText(selectedNumber);
                        }
                    });
                    AlertDialog alert = builder.create();
                    if(allNumbers.size() > 1) {
                        alert.show();
                    } else {
                        String selectedNumber = phoneNumber.toString();
                        selectedNumber = selectedNumber.replace("-", "");
                        Toast.makeText(getActivity(), selectedNumber, Toast.LENGTH_SHORT);
                       mContactButton.setText(selectedNumber);
                    }

                    if (phoneNumber.length() == 0) {
                        //no numbers found actions
                    }
                }
            } finally {
                c.close();
            }

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
