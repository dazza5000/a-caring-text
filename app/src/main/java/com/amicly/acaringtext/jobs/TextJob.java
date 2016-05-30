package com.amicly.acaringtext.jobs;

import android.support.annotation.NonNull;
import android.telephony.SmsManager;

import com.evernote.android.job.Job;

/**
 * Created by daz on 5/29/16.
 */
public class TextJob  extends Job {

    public static final String TAG = "text_job_tag";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {

        String message = params.getExtras().getString("message", "Greetings!");

        SmsManager smsManager = SmsManager.getDefault();

        smsManager.sendTextMessage("15126937499", null,
                message, null, null);
        return Result.SUCCESS;
    }
}

