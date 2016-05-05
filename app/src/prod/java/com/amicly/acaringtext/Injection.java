package com.amicly.acaringtext;

import android.content.Context;

import com.amicly.acaringtext.data.TextRepositories;
import com.amicly.acaringtext.data.TextsRepository;
import com.amicly.acaringtext.data.TextsServiceApiImpl;

/**
 * Enables injection of production implementations for {@link TextsRepository}
 * at compile time.
 */
public class Injection {

    public static TextsRepository provideTextsRepository(Context context) {
        return TextRepositories.getInMemoryRepoInstance(new TextsServiceApiImpl(context));
    }
}
