package com.amicly.acaringtext.addtext;

import android.support.annotation.NonNull;

import java.util.Date;

import static com.amicly.acaringtext.util.DateUtil.getDateStringFromDate;
import static com.amicly.acaringtext.util.DateUtil.getTimeStringFromDate;

/**
 * Created by daz on 2/2/16.
 */
public class AddTextPresenter implements AddTextContract.UserActionsListener {

    private Date mDate;
    private Date mTime;


    @NonNull
    private final AddTextContract.View mAddTextView;

    public AddTextPresenter(@NonNull AddTextContract.View addTextView) {
        mAddTextView = addTextView;
    }

    @Override
    public void setDate(Date date){
        mDate = date;
        mAddTextView.showDate(getDateStringFromDate(date));
    }

    @Override
    public void setTime(Date date) {
        mTime = date;
        mAddTextView.showTime(getTimeStringFromDate(date));
    }
}
