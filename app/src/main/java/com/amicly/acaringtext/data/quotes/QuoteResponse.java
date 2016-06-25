package com.amicly.acaringtext.data.quotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by daz on 6/25/16.
 */

public class QuoteResponse {

    public static final String QUOTES_URL = " http://quotes.rest";

    @SerializedName("contents")
    @Expose
    private Contents contents;

    /**
     *
     * @return
     * The contents
     */
    public Contents getContents() {
        return contents;
    }

    /**
     *
     * @param contents
     * The contents
     */
    public void setContents(Contents contents) {
        this.contents = contents;
    }

}

