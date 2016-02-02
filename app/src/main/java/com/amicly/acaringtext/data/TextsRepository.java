package com.amicly.acaringtext.data;

import android.support.annotation.NonNull;

import com.amicly.acaringtext.model.Text;

import java.util.List;

/**
 * Created by daz on 2/1/16.
 */
public interface TextsRepository {

    interface LoadTextsCallback {

        void onTextsLoaded(List<Text> texts);

    }

    interface LoadTextCallback {

        void onTextLoaded(Text text);

    }

    void getTexts(@NonNull LoadTextCallback callback);

    void getText(@NonNull String textId, @NonNull LoadTextCallback callback);

    void saveText(@NonNull Text text);

    void refreshData();

}
