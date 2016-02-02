package com.amicly.acaringtext.data;

import com.amicly.acaringtext.model.Text;

import java.util.List;

/**
 * Created by daz on 2/1/16.
 */
public interface TextsServiceApi {

    interface TextsServiceCallback<T>{

        void onLoaded(T notes);

    }

    void getAllTexts(TextsServiceCallback<List<Text>> callback);

    void getText(String textId, TextsServiceCallback<Text>);

    void saveText(Text text);
}


