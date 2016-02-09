package com.amicly.acaringtext.addtext;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by daz on 2/2/16.
 */
public class AddTextPresenter implements AddTextContract.UserActionsListener {

    @NonNull
    private final AddTextContract.View mAddTextView;

    public AddTextPresenter(@NonNull AddTextContract.View addTextView) {
        mAddTextView = addTextView;
    }

    @Override
    public void setDate(){
        //Complete later
    }

    @Override
    public void setTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        Calendar calendar = new GregorianCalendar();
        dateFormat.setCalendar(calendar);
        calendar.setTime(date);
        String formattedTime = dateFormat.format(calendar.getTime());
        mAddTextView.showTime(formattedTime);
    }
}
