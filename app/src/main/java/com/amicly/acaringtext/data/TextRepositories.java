package com.amicly.acaringtext.data;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daz on 2/1/16.
 */
public class TextRepositories {

    private TextRepositories(){}

    private static TextsRepository repository = null;

    public synchronized static TextsRepository getInMemoryRepoInstance(@NonNull TextsServiceApi textsServiceApi){
        checkNotNull(textsServiceApi);
        if (null == repository){
            repository = new InMemoryTextsRepository(textsServiceApi);
        }
        return repository;
    }


}
