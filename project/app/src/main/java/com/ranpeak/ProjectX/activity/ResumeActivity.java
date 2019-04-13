package com.ranpeak.ProjectX.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ranpeak.ProjectX.R;

public class ResumeActivity extends AppCompatActivity {

    private static final int RESUME_ACTIVITY = R.layout.activity_resume;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(RESUME_ACTIVITY);
    }
}
