package com.amicly.acaringtext.di;

import com.amicly.acaringtext.ActApplication;
import com.amicly.acaringtext.texts.TextsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by daz on 2/1/16.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(ActApplication app);
    void inject(TextsActivity activity);
}