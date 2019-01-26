package com.ranpeak.ProjectX.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.ranpeak.ProjectX.R;

public class StartActivity extends AppCompatActivity {

    private Button log_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_window);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
