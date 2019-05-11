package com.ranpeak.ProjectX.activity.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;

public class SearchResumeAlertDialog extends AppCompatActivity implements Activity {

    private final static int SEARCH_RESUME_ALERT_DIALOG = R.layout.fragment_search_resume;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(SEARCH_RESUME_ALERT_DIALOG);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void onListener() {

    }
}
