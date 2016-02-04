package com.amicly.acaringtext;

import android.app.Application;

import com.amicly.acaringtext.di.ApplicationComponent;
import com.amicly.acaringtext.di.ApplicationModule;
import com.amicly.acaringtext.di.DaggerApplicationComponent;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by daz on 2/1/16.
 */
public class ActApplication extends Application {
    private ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.i("Creating our Application");

        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }

}
