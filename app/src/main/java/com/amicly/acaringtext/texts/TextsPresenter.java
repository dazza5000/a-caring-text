package com.amicly.acaringtext.texts;

import android.support.annotation.NonNull;

import com.amicly.acaringtext.data.TextsRepository;
import com.amicly.acaringtext.model.Text;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by darrankelinske on 1/30/16.
 */
public class TextsPresenter implements TextsContract.UserActionsListener {

    private final TextsRepository mTextsRepository;
    private final TextsContract.View mTextsView;

    public TextsPresenter(
            @NonNull TextsRepository textsRepository, @NonNull TextsContract.View textsView) {
        mTextsRepository = checkNotNull(textsRepository, "notesRepository cannot be null");
        mTextsView = checkNotNull(textsView, "notesView cannot be null!");
    }

    @Override
    public void loadTexts(boolean forceUpdate) {

        List<Text> texts = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Text text = new Text();
            text.setmDateTime("7:77");
            text.setmContact("Mr or Mrs. let's rock #" +i);
            text.setmMessage("hello moto " +i);
            texts.add(i, text);

        }

        mTextsView.showTexts(texts);
    }

    @Override
    public void addNewText() {
        mTextsView.showAddText();
    }

    @Override
    public void openTextDetails(@NonNull Text requestedText) {

    }
}
