package com.amicly.acaringtext.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by daz on 2/1/16.
 */
@Module
public class ApplicationModule {
    private Application mApp;

    public ApplicationModule(Application app) {
        mApp = app;
    }

    @Provides
    @Singleton
    Application provideApplicationContext() {
        return mApp;
    }

    @Provides
    @Singleton
    String provideTestString() { return "hello dagger!";}
}
