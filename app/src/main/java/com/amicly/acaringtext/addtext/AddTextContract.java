package com.amicly.acaringtext.addtext;

import java.util.Date;

/**
 * Created by daz on 2/2/16.
 */
public interface AddTextContract {

    interface View {

        void showDate(String date);

        void showTime(String time);

        void showTexts();

        void showEmptyTextError();

        void showFutureDateError();

    }

    interface UserActionsListener {

        void saveText(String date, String time, String contact, String contactNumber,
                      String message);

        void setDate(Date date);

        void setTime(Date date);

    }
}
