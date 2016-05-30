package com.amicly.acaringtext.data;

import com.amicly.acaringtext.jobs.TextJob;
import com.amicly.acaringtext.util.DateUtil;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by daz on 2/1/16.
 */
public class TextsServiceApiImpl implements TextsServiceApi {


    public TextsServiceApiImpl() {
    }

    @Override
    public void getAllTexts(TextsServiceCallback<List<Text>> callback) {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Text> texts = realm.where(Text.class).findAll();

        callback.onLoaded(texts);

    }

    @Override
    public void getText(String textId, TextsServiceCallback<Text> callback) {

    }

    @Override
    public void saveText(Text text) {

        Integer scheduledJobId;

        Long executionWindow = DateUtil.getTimeDifferenceFromNowInMilliseconds(text.getmDateTime());

        PersistableBundleCompat extras = new PersistableBundleCompat();
        extras.putString("message", text.getmMessage());

        JobRequest newJobRequest = new JobRequest.Builder(TextJob.TAG)
                .setExtras(extras)
                .setExecutionWindow(executionWindow, executionWindow + 7000L)
                .setPersisted(true)
                .build();

        // Get the job id so we can persist it with the Text object
        scheduledJobId = newJobRequest.getJobId();

        // Set the scheduled job id on the Text object
        text.setmScheduledJobId(scheduledJobId);

        // Schedule the Job Request
        newJobRequest.schedule();

        // Save the Text object in Realm
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(text);
        realm.commitTransaction();
    }
}
