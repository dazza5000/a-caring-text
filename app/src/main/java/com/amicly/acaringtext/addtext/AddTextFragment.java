package com.amicly.acaringtext.addtext;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.amicly.acaringtext.R;
import com.amicly.acaringtext.data.InMemoryTextsRepository;
import com.amicly.acaringtext.data.TextsServiceApiImpl;
import com.amicly.acaringtext.pickers.DatePickerFragment;
import com.amicly.acaringtext.pickers.NumberPickerFragment;
import com.amicly.acaringtext.pickers.TimePickerFragment;
import com.amicly.acaringtext.texts.TextsActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by daz on 2/2/16.
 */
public class AddTextFragment extends Fragment implements AddTextContract.View {

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final String DIALOG_NUMBER = "DialogNumber";

    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_CONTACT = 2;
    private static final int REQUEST_NUMBER = 3;

    private Button mDateButton;
    private Button mTimeButton;
    private Button mContactButton;
    private Button mQuoteOfTheDay;
    private EditText mMessage;

    private DatePickerFragment mDatePickerDialog;
    private TimePickerFragment mTimePickerDialog;

    private String mDate;
    private String mTime;
    private String mContactNumber;

    private AddTextContract.UserActionsListener mActionListener;


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
                if (mDatePickerDialog == null) {
                    FragmentManager fm = getFragmentManager();
                    mDatePickerDialog = DatePickerFragment.newInstance(new Date());
                    mDatePickerDialog.setTargetFragment(AddTextFragment.this, REQUEST_DATE);
                    mDatePickerDialog.show(fm, DIALOG_DATE);
                }
            }
        });
        mTimeButton = (Button) root.findViewById(R.id.add_text_time);
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimePickerDialog == null) {
                    FragmentManager fm = getFragmentManager();
                    mTimePickerDialog = TimePickerFragment.newInstance(new Date());
                    mTimePickerDialog.setTargetFragment(AddTextFragment.this, REQUEST_TIME);
                    mTimePickerDialog.show(fm, DIALOG_TIME);
                }
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

                mActionListener.saveText(mDateButton.getText().toString().trim(),
                        mTimeButton.getText().toString().trim(),
                        mContactButton.getText().toString().trim(),
                        mContactNumber,
                        mMessage.getText().toString().trim());
            }
        });

        mQuoteOfTheDay = (Button) root.findViewById(R.id.add_text_quote);
        mQuoteOfTheDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActionListener.getQuoteOfTheDay();
            }
        });

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionListener = new AddTextPresenter(
                new InMemoryTextsRepository(new TextsServiceApiImpl()), this);

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
            mActionListener.setDate(date);
            mDatePickerDialog = null;
        }

        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mActionListener.setTime(date);
            mTimePickerDialog = null;
        }

        if (requestCode == REQUEST_CONTACT && data != null) {

            Uri contactUri = data.getData();
            // Specify which files you want your query to return
            // values for.
            String[] mProjection = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME};
            String mSelectionClause = ContactsContract.Contacts.HAS_PHONE_NUMBER + " = ?";
            String[] mSelectionArgs = {"1"};
            // Perform your query - the contactUri is like a "where"
            // clause here
            Cursor c = getActivity().getContentResolver()
                    .query(contactUri, mProjection, mSelectionClause, mSelectionArgs, null);

            try {
                // Double-check that you actually got results
                if (c.getCount() == 0) {
                    return;
                }

                // Pull out the first column of the first row of data -
                // that is your contact's name
                c.moveToFirst();
                String contactName = c.getString(0);
                mContactButton.setText(contactName);


            } finally {
                c.close();
            }

            List<String> allNumbers = new ArrayList<>();
            int phoneIdx = 0;
            Cursor cursor = null;
            try {
                String id = data.getData().getLastPathSegment();
                cursor = getActivity().getContentResolver()
                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                                new String[]{id}, null);
                phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
                if (cursor.moveToFirst()) {
                    while (cursor.isAfterLast() == false) {
                        String phoneNumber = cursor.getString(phoneIdx);
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
            }
            if (allNumbers.size() > 1) {
                FragmentManager fm = getFragmentManager();
                NumberPickerFragment dialog = NumberPickerFragment.newInstance(data.getData());
                dialog.setTargetFragment(AddTextFragment.this, REQUEST_NUMBER);
                dialog.show(fm, DIALOG_NUMBER);
            } else {
                mContactNumber = allNumbers.get(0);
            }
        }

        if (requestCode == REQUEST_NUMBER && data != null) {
            mContactNumber = (String) data
                    .getSerializableExtra(NumberPickerFragment.EXTRA_PHONE_NUMBER);

        }
    }

    @Override
    public void showTime(String time) {
        mTimeButton.setText(time);
    }

    @Override
    public void showDate(String date) {
        mDateButton.setText(date);
    }

    @Override
    public void showTexts() {
        startActivity(new Intent(getActivity(), TextsActivity.class));
    }

    @Override
    public void showEmptyTextError() {
        Snackbar.make(mDateButton, "Please enter all fields.", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showFutureDateError() {
        Snackbar.make(mDateButton, "Please schedule a text in the future.",
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showQuoteOfTheDay(String quoteOfTheDay) {
        mMessage.setText(mMessage.getText().toString() + " " + quoteOfTheDay);
    }
}
