package com.ranpeak.ProjectX.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.ranpeak.ProjectX.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_window);

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
        Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
        startActivity(intent);
    }

    public void Click1to1(View view){
        Intent intent = new Intent(getApplicationContext(),WaitingTimeActivity.class);
        startActivity(intent);
    }

}
