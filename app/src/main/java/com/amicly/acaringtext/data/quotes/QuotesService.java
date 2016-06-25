package com.amicly.acaringtext.data.quotes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by daz on 6/25/16.
 */

public interface QuotesService {
    @GET("/qod.json/")
    Call<QuoteResponse> getQuote(@Query("category") String category);
}
