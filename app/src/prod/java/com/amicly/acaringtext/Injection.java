package com.amicly.acaringtext;

import com.amicly.acaringtext.data.TextRepositories;
import com.amicly.acaringtext.data.TextsRepository;
import com.amicly.acaringtext.data.TextsServiceApiImpl;

/**
 * Enables injection of production implementations for {@link TextsRepository}
 * at compile time.
 */
public class Injection {

    public static TextsRepository provideTextsRepository() {
        return TextRepositories.getInMemoryRepoInstance(new TextsServiceApiImpl());
    }
}
