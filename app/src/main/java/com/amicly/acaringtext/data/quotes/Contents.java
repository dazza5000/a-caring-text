package com.amicly.acaringtext.data.quotes;

/**
 * Created by daz on 6/25/16.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Contents {

    @SerializedName("quotes")
    @Expose
    private List<Quote> quotes;

    public Contents(){
        quotes = new ArrayList<>();
    }

    /**
     * @return The quotes
     */
    public List<Quote> getQuotes() {
        return quotes;
    }

    /**
     * @param quotes The quotes
     */
    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    public static Contents parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Contents contents = gson.fromJson(response, Contents.class);
        return contents;
    }
}