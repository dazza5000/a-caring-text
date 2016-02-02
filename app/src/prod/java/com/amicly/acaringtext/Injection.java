package com.amicly.acaringtext;

/**
 * Enables injection of production implementations for {@link TextsRepository}
 * at compile time.
 */
public class Injection {

    public static TextsRepository provideTextsRepository() {
        return TextRepositories.getInMemoryRepoInstance(new TextsServiceApiImpl());
    }
}
