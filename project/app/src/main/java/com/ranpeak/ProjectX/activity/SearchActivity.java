package com.ranpeak.ProjectX.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ranpeak.ProjectX.R;

public class SearchActivity extends AppCompatActivity {

    private final static int SEARCH_ACTIVITY = R.layout.activity_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(SEARCH_ACTIVITY);
    }
}
