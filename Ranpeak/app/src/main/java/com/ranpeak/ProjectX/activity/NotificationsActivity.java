package com.ranpeak.ProjectX.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ranpeak.ProjectX.R;

public class NotificationsActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ACTIVITY = R.layout.activity_notifications;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(NOTIFICATION_ACTIVITY);
    }
}
