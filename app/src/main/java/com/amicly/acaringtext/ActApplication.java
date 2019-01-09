package com.amicly.acaringtext;

import android.app.Application;

import com.amicly.acaringtext.di.ApplicationComponent;
import com.amicly.acaringtext.di.ApplicationModule;
import com.amicly.acaringtext.di.DaggerApplicationComponent;
import com.amicly.acaringtext.jobs.TextJobCreator;
import com.crashlytics.android.Crashlytics;
import com.evernote.android.job.JobManager;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by daz on 2/1/16.
 */
public class ActApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        // Initialize Evernote Job Creator
        JobManager.create(this).addJobCreator(new TextJobCreator());

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
