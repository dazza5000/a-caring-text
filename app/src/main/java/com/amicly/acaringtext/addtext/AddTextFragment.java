package com.amicly.acaringtext.addtext;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.amicly.acaringtext.Injection;
import com.amicly.acaringtext.R;
import com.amicly.acaringtext.pickers.DatePickerFragment;
import com.amicly.acaringtext.pickers.NumberPickerFragment;
import com.amicly.acaringtext.pickers.TimePickerFragment;
import com.amicly.acaringtext.texts.TextsActivity;

import java.util.Date;

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
    private EditText mMessage;

    private String mDate;
    private String mTime;

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
//                mActionListener.saveText(mDateButton.getText().toString(),
//                        mTimeButton.getText().toString(), mContactButton.getText().toString(),
//                        mMessage.getText().toString());
                mActionListener.saveText("", "", "", "");
            }
        });

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionListener = new AddTextPresenter(Injection.provideTextsRepository(getContext()), this);

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
//            mDateButton.setText(date.toString());
            mActionListener.setDate(date);
        }

        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
//            mTimeButton.setText(date.toString());
            mActionListener.setTime(date);
        }

        if (requestCode == REQUEST_CONTACT && data != null) {
            FragmentManager fm = getFragmentManager();
            NumberPickerFragment dialog = NumberPickerFragment.newInstance(data.getData());
            dialog.setTargetFragment(AddTextFragment.this, REQUEST_NUMBER);
            dialog.show(fm, DIALOG_NUMBER);
        }
        if (requestCode == REQUEST_NUMBER && data != null) {

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
}
