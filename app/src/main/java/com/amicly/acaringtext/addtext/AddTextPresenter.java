package com.amicly.acaringtext.addtext;

import android.support.annotation.NonNull;
import android.util.Log;

import com.amicly.acaringtext.data.Text;
import com.amicly.acaringtext.data.TextsRepository;
import com.amicly.acaringtext.data.quotes.Quote;
import com.amicly.acaringtext.data.quotes.QuoteResponse;
import com.amicly.acaringtext.data.quotes.QuotesService;
import com.amicly.acaringtext.util.DateUtil;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.amicly.acaringtext.data.quotes.QuoteResponse.QUOTES_URL;
import static com.amicly.acaringtext.util.DateUtil.getDateFromDateString;
import static com.amicly.acaringtext.util.DateUtil.getDateStringFromDate;
import static com.amicly.acaringtext.util.DateUtil.getTimeFromTimeString;
import static com.amicly.acaringtext.util.DateUtil.getTimeStringFromDate;
import static com.amicly.acaringtext.util.DateUtil.mergeDateAndTime;

/**
 * Created by daz on 2/2/16.
 */
public class AddTextPresenter implements AddTextContract.UserActionsListener {

    private final TextsRepository mTextsRepository;

    private Date mDate;
    private Date mTime;

    @NonNull
    private final AddTextContract.View mAddTextView;

    public AddTextPresenter(@NonNull TextsRepository textsRepository,
                            @NonNull AddTextContract.View addTextView) {
        mTextsRepository = textsRepository;
        mAddTextView = addTextView;
    }

    @Override
    public void setDate(Date date){
        mDate = date;
        mAddTextView.showDate(getDateStringFromDate(date));
    }

    @Override
    public void setTime(Date date) {
        mTime = date;
        mAddTextView.showTime(getTimeStringFromDate(date));
    }

    @Override
    public void getQuoteOfTheDay() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QUOTES_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuotesService quotesService = retrofit.create(QuotesService.class);
        String category = "inspire";
        Call<QuoteResponse> call = quotesService.getQuote(category);
        call.enqueue(new Callback<QuoteResponse>() {
            @Override
            public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {
                QuoteResponse quoteResponse = response.body();
                Log.d("Texts", "The response body is:" + response.body());
                Log.d("Texts", "The response toString() is:"
                        + quoteResponse.getContents().getQuotes().get(0));
                Quote quoteOfTheDay = quoteResponse.getContents().getQuotes().get(0);
                mAddTextView.showQuoteOfTheDay("\""+quoteOfTheDay.getQuote() +"\" - "
                        + quoteOfTheDay.getAuthor());
            }

            @Override
            public void onFailure(Call<QuoteResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void saveText(String dateString, String timeString, String contact, String contactNumber,
                         String message) {

        if (dateString.isEmpty() || timeString.isEmpty() || (null == contactNumber)
                || message.isEmpty()) {

            mAddTextView.showEmptyTextError();

        } else {

            Date date = getDateFromDateString(dateString);
            Date time = getTimeFromTimeString(timeString);

            Date dateTime = mergeDateAndTime(date, time);

            if(DateUtil.getTimeDifferenceFromNowInMilliseconds(dateTime.getTime()) > 7) {

                Text textToSave = new Text(dateTime.getTime(), contact.trim(), contactNumber.trim(),
                        message.trim());
                if (textToSave.isEmpty()) {
                    mAddTextView.showEmptyTextError();
                } else {
                    mTextsRepository.saveText(textToSave);
                }
                mAddTextView.showTexts();
            } else {
                mAddTextView.showFutureDateError();
            }
        }
    }
}
