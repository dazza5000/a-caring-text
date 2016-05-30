package com.amicly.acaringtext.pickers;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.amicly.acaringtext.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daz on 5/4/16.
 */
public class NumberPickerFragment extends DialogFragment {

    public static final String EXTRA_PHONE_NUMBER =
            "com.amicly.acaringtext.phone_number";

    private static final String ARG_CONTACT = "contact";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Uri contact = getArguments().getParcelable(ARG_CONTACT);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_number, null);

            Cursor cursor = null;
            String phoneNumber = "";
            List<String> allNumbers = new ArrayList<>();
            int phoneIdx = 0;
            try {
                String id = contact.getLastPathSegment();
                cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[] { id }, null);
                phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
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
                    return new AlertDialog.Builder(getActivity())
                            .setTitle("Choose a number")
                            .setView(v)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    String selectedNumber = items[item].toString();
                                    selectedNumber = selectedNumber.replace("-", "");
                                    sendResult(Activity.RESULT_OK, selectedNumber);

                                }
                            }).create();

            }



    }


    public static NumberPickerFragment newInstance(Uri uri) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_CONTACT, uri);
        NumberPickerFragment fragment = new NumberPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, String phoneNumber) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);

        getTargetFragment().
                onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
