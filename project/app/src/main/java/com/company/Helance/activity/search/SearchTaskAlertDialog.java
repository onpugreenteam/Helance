package com.company.Helance.activity.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.company.Helance.R;
import com.company.Helance.interfaces.Activity;

public class SearchTaskAlertDialog extends AppCompatActivity implements Activity {

    private final static int SEARCH_TASK_ALERT_DIALOG = R.layout.fragment_search_task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(SEARCH_TASK_ALERT_DIALOG);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void onListener() {

    }
}
