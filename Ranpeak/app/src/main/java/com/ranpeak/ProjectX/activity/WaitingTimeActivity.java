package com.ranpeak.ProjectX.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;

public class WaitingTimeActivity extends AppCompatActivity {

    private final static int WAITING_TIME_ACTIVITY = R.layout.activity_waiting_time;

    private int seconds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(WAITING_TIME_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        runtimer(true);
    }



    private void runtimer(final Boolean running){

        final TextView textView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {

                int second = seconds%60;
                int minutes = (seconds%3600)/60;

                String time = String.format("%02d:%02d", minutes, second);
                textView.setText(time);

                if(running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

}
