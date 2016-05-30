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
        // run your job

        SmsManager smsManager = SmsManager.getDefault();

        smsManager.sendTextMessage("15126937499", null,
                "This is a message from the job scheduler", null, null);
        return Result.SUCCESS;
    }
}

