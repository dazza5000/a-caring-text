package com.amicly.acaringtext.data;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by daz on 2/1/16.
 */
public class TextsServiceApiImpl implements TextsServiceApi {

    private Realm realm = Realm.getDefaultInstance();

    @Override
    public void getAllTexts(TextsServiceCallback<List<Text>> callback) {

//        Text text = new Text();
//        text.setmId("asdf");
//
//        realm.beginTransaction();
//        realm.copyToRealm(text);
//        realm.commitTransaction();

        List<Text> texts = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Text text = new Text();
            text.setmDateTime("7:77");
            text.setmContact("Mr or Mrs. let's rock #" +i);
            text.setmMessage("hello moto " +i);
            texts.add(i, text);

        }

        callback.onLoaded(texts);



    }

    @Override
    public void getText(String textId, TextsServiceCallback<Text> callback) {

    }

    @Override
    public void saveText(Text text) {

    }
}
