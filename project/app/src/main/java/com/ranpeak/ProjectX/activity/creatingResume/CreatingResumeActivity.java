package com.ranpeak.ProjectX.activity.creatingResume;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ranpeak.ProjectX.R;

public class CreatingResumeActivity extends AppCompatActivity {

    private final static int CREATING_RESUME_ACTIVITY = R.layout.activity_creating_resume;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(CREATING_RESUME_ACTIVITY);
    }
}
