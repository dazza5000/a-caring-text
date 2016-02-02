package com.amicly.acaringtext.texts;

import android.support.annotation.NonNull;

import com.amicly.acaringtext.model.Text;

import java.util.List;

/**
 * Created by darrankelinske on 1/30/16.
 */
public interface TextsContract {

    interface View {

        void showTexts(List<Text> texts);

        void showAddText();

        void showTextDetailUi(String textId);

    }

    interface UserActionsListener {

        void loadTexts(boolean forceUpdate);

        void addNewText();

        void openTextDetails(@NonNull Text requestedText);

    }
}
