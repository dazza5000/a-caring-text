package com.amicly.acaringtext.addtext;

/**
 * Created by daz on 2/2/16.
 */
public interface AddTextContract {

    public interface View {

        void showEmptyTextError();

        void showTextsList();

        void openContactChooser();

        void setUserActionListener(UserActionsListener listener);
    }

    public interface UserActionsListener {

        void saveText(String dateTime, String contact, String contactNumber, String message);

        void chooseContact(String contactName, String contactNumber);

    }
}
