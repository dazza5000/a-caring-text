package com.amicly.acaringtext.addtext;

/**
 * Created by daz on 2/2/16.
 */
public interface AddTextContract {

    public interface View {

        void showEmptyTextError();

        void showTextsList();

        void showContactPicker();

        void showDatePicker();

        void showTimePicker();

        void setUserActionListener(UserActionsListener listener);
    }

    public interface UserActionsListener {

        void saveText(String dateTime, String contact, String contactNumber, String message);

        void pickDate();

        void pickTime();

        void saveDate();

        void saveTime();

        void chooseContact();

    }
}
