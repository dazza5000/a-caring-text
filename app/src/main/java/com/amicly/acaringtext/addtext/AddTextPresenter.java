package com.amicly.acaringtext.addtext;

import android.support.annotation.NonNull;

import com.amicly.acaringtext.data.Text;
import com.amicly.acaringtext.data.TextsRepository;
import com.amicly.acaringtext.util.DateUtil;

import java.util.Date;

import static com.amicly.acaringtext.util.DateUtil.getDateFromDateString;
import static com.amicly.acaringtext.util.DateUtil.getDateStringFromDate;
import static com.amicly.acaringtext.util.DateUtil.getTimeFromTimeString;
import static com.amicly.acaringtext.util.DateUtil.getTimeStringFromDate;
import static com.amicly.acaringtext.util.DateUtil.mergeDateAndTime;

/**
 * Created by daz on 2/2/16.
 */
public class AddTextPresenter implements AddTextContract.UserActionsListener {

    private final TextsRepository mTextsRepository;

    private Date mDate;
    private Date mTime;


    @NonNull
    private final AddTextContract.View mAddTextView;

    public AddTextPresenter(@NonNull TextsRepository textsRepository,
                            @NonNull AddTextContract.View addTextView) {
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
    public void saveText(String dateString, String timeString, String contact, String contactNumber,
                         String message) {

        if (dateString.isEmpty() || timeString.isEmpty() || contactNumber.isEmpty()
                || message.isEmpty()) {

            mAddTextView.showEmptyTextError();

        } else {

            Date date = getDateFromDateString(dateString);
            Date time = getTimeFromTimeString(timeString);

            Date dateTime = mergeDateAndTime(date, time);

            if(DateUtil.getTimeDifferenceFromNowInMilliseconds(date.getTime()) < 7) {


                Text textToSave = new Text(dateTime.getTime(), contact.trim(), contactNumber.trim(),
                        message.trim());
                if (textToSave.isEmpty()) {
                    mAddTextView.showEmptyTextError();
                } else {
                    mTextsRepository.saveText(textToSave);
                }
                mAddTextView.showTexts();
            } else {
                mAddTextView.showFutureDateError();
            }
        }
    }
}
