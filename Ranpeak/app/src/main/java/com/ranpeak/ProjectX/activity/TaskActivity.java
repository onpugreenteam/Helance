package com.ranpeak.ProjectX.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.ranpeak.ProjectX.R;

public class TaskActivity extends AppCompatActivity {

    private final static int TASK_ACTIVITY = R.layout.activity_task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(TASK_ACTIVITY);
    }
}
