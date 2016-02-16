package com.amicly.acaringtext.addtext;

import android.support.annotation.NonNull;

import com.amicly.acaringtext.data.Text;
import com.amicly.acaringtext.data.TextsRepository;

import java.util.Date;

import static com.amicly.acaringtext.util.DateUtil.getDateStringFromDate;
import static com.amicly.acaringtext.util.DateUtil.getTimeStringFromDate;

/**
 * Created by daz on 2/2/16.
 */
public class AddTextPresenter implements AddTextContract.UserActionsListener {

    private final TextsRepository mTextsRepository;

    private Date mDate;
    private Date mTime;


    @NonNull
    private final AddTextContract.View mAddTextView;

    public AddTextPresenter(@NonNull TextsRepository textsRepository, @NonNull AddTextContract.View addTextView) {
        mTextsRepository = textsRepository;
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

    @Override
    public void saveText(String dateTime, String contact, String contactNumber, String message) {
        mTextsRepository.saveText(new Text());
    }
}
