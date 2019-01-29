package com.ranpeak.ProjectX.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ranpeak.ProjectX.R;

public class CommunicationActivity extends AppCompatActivity {

    private final static int COMMUNICATION_ACTIVITY = R.layout.activity_communication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(COMMUNICATION_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
}
