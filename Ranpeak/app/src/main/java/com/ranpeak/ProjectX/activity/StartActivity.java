package com.ranpeak.ProjectX.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.user.data.SharedPrefManager;

public class StartActivity extends AppCompatActivity {

    private final static int START_ACTIVITY = R.layout.activity_start_window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(START_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LobbyActivity.class));
            return;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    /** Метод отключает на данной активити кнопку назад!**/
//    @Override
//    public void onBackPressed() {
//
//    }

    public void ClickLogIn(View view){
        Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
        startActivity(intent);
    }


    public void Click1to1(View view){
        Intent intent = new Intent(getApplicationContext(), WaitingTimeActivity.class);
        startActivity(intent);
    }

}
