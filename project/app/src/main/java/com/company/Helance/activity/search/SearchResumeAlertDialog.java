package com.company.Helance.activity.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.company.Helance.R;
import com.company.Helance.activity.settings.LanguageHelper;
import com.company.Helance.base.BaseActivity;
import com.company.Helance.interfaces.Activity;

public class SearchResumeAlertDialog extends BaseActivity implements Activity {

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
