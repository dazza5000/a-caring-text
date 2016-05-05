package com.amicly.acaringtext.data;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import com.amicly.acaringtext.jobs.TextJobService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by daz on 2/1/16.
 */
public class TextsServiceApiImpl implements TextsServiceApi {

    private Context context;

    public TextsServiceApiImpl(Context context) {
        this.context = context;
    }


    List<Text> texts = new ArrayList<>();

    @Override
    public void getAllTexts(TextsServiceCallback<List<Text>> callback) {

//        Text text = new Text();
//        text.setmId("asdf");
//
//        realm.beginTransaction();
//        realm.copyToRealm(text);
//        realm.commitTransaction();

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Text> texts = realm.where(Text.class).findAll();


        callback.onLoaded(texts);



    }

    @Override
    public void getText(String textId, TextsServiceCallback<Text> callback) {

    }

    @Override
    public void saveText(Text text) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(text);
        realm.commitTransaction();


//        Text fakeText = new Text();
//        text.setmDateTime("77:77");
//        text.setmContact("Mr or Mrs. let's rock #" +77);
//        text.setmMessage("hello moto " +77);
//        texts.add(texts.size() , fakeText);

        ComponentName componentName = new ComponentName(context.getApplicationContext(),
                TextJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(
                (int) Calendar.getInstance().getTimeInMillis(), componentName)
                .setRequiresCharging(false)
                .setPersisted(true)
                .setMinimumLatency(777)
                .setOverrideDeadline(777+7000)
                .build();
        JobScheduler jobScheduler = (JobScheduler)
                context.getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);


    }
}
