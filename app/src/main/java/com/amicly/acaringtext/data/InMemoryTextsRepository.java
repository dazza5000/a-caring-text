package com.amicly.acaringtext.data;

import android.support.annotation.NonNull;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daz on 2/1/16.
 */
public class InMemoryTextsRepository implements TextsRepository {

    private final TextsServiceApi mTextsServiceApi;

    List<Text> mCachedTexts;

    public InMemoryTextsRepository(@NonNull TextsServiceApi textsServiceApi) {
        mTextsServiceApi = checkNotNull(textsServiceApi);
    }

    @Override
    public void getTexts(@NonNull final LoadTextsCallback callback) {
        checkNotNull(callback);
        if (mCachedTexts == null) {
            mTextsServiceApi.getAllTexts(new TextsServiceApi.TextsServiceCallback<List<Text>>() {
                @Override
                public void onLoaded(List<Text> texts){
                    mCachedTexts = ImmutableList.copyOf(texts);
                    callback.onTextsLoaded(mCachedTexts);
                }
            });
        } else {
            callback.onTextsLoaded(mCachedTexts);
        }

    }

    @Override
    public void getText(@NonNull String textId, @NonNull final LoadTextCallback callback) {
        checkNotNull(textId);
        checkNotNull(callback);
        mTextsServiceApi.getText(textId, new TextsServiceApi.TextsServiceCallback<Text>() {
            @Override
            public void onLoaded(Text text) {
                callback.onTextLoaded(text);
            }

        });

    }

    @Override
    public void saveText(@NonNull Text text) {
        checkNotNull(text);
        mTextsServiceApi.saveText(text);
        refreshData();
    }

    @Override
    public void refreshData() {
        mCachedTexts = null;
    }
}
