package com.ranpeak.ProjectX.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.user.data.SharedPrefManager;

public class LobbyActivity extends AppCompatActivity {

   private final static int LOBBY_ACTIVITY = R.layout.activity_lobby;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    public void ClickProfile(View view){
        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
    }


    public void ClickOneToOne(View view){
        Intent intent = new Intent(getApplicationContext(),WaitingTimeActivity.class);
        startActivity(intent);
    }
}
