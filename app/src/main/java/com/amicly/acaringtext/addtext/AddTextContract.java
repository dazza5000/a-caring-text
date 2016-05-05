package com.amicly.acaringtext.addtext;

import java.util.Date;

/**
 * Created by daz on 2/2/16.
 */
public interface AddTextContract {

    interface View {

//        void showEmptyTextError();
//
//        void showTextsList();
//
//        void showContactPicker();
//
//        void showDatePicker();
//
//        void showTimePicker();
//
//        void setUserActionListener(UserActionsListener listener);
//
        void showDate(String date);

        void showTime(String time);

        void showTexts();

    }

    interface UserActionsListener {

        void saveText(String dateTime, String contact, String contactNumber, String message);
//
//        void pickDate();

//        void pickTime();

        void setDate(Date date);

        void setTime(Date date);

//        void chooseContact();

    }
}
