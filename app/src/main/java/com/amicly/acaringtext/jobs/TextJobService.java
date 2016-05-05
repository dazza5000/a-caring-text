package com.amicly.acaringtext.jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by daz on 5/4/16.
 */
public class TextJobService extends JobService {
    private static final String TAG = "TextJobService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroyed");
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "We got the following job" + params.getJobId());

        SmsManager smsManager = SmsManager.getDefault();

        smsManager.sendTextMessage("15126937499", null,
                "This is a message from the job scheduler", null, null);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob for: " + params.getJobId());
        return false;
    }
}
