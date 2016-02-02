package com.amicly.acaringtext.data;

import com.amicly.acaringtext.model.Text;

import java.util.List;

import io.realm.Realm;

/**
 * Created by daz on 2/1/16.
 */
public class TextsServiceApiImpl implements TextsServiceApi {

    private Realm realm = Realm.getDefaultInstance();

    @Override
    public void getAllTexts(TextsServiceCallback<List<Text>> callback) {

        Text text = new Text();
        text.setmId("asdf");

        realm.beginTransaction();
        realm.copyToRealm(text);
        realm.commitTransaction();

    }

    @Override
    public void getText(String textId, TextsServiceCallback<Text> callback) {

    }

    @Override
    public void saveText(Text text) {

    }
}
