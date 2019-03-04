package com.ranpeak.ProjectX.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creatingTask.CreatingTaskActivity;

public class LobbyActivity extends AppCompatActivity {

   private final static int LOBBY_ACTIVITY = R.layout.activity_lobby;

   private ListView listView;
   private FloatingActionButton fab;
   private Toolbar toolbar;
   private ImageView imageViewButtonProfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        findViewById();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LobbyActivity.this, CreatingTaskActivity.class));
            }
        });


        imageViewButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

            }
        });
    }


    private void findViewById(){
        fab = findViewById(R.id.floatingActionButton);
        imageViewButtonProfile = findViewById(R.id.imageViewProfileButton);
    }

}
