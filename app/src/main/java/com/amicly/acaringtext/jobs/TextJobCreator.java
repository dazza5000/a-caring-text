package com.amicly.acaringtext.jobs;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by daz on 5/29/16.
 */
public class TextJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case TextJob.TAG:
                return new TextJob();
            default:
                return null;
        }
    }
}