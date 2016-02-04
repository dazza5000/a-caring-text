package com.amicly.acaringtext.addtext;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.amicly.acaringtext.R;

/**
 * Created by daz on 2/2/16.
 */
public class AddTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (null == savedInstanceState) {
            initFragment(AddTextFragment.newInstance());
        }
    }

    private void initFragment(Fragment addTextFragment) {
        // Add the NotesFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, addTextFragment);
        transaction.commit();
    }

}
