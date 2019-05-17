package com.company.Helance.activity.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.Helance.R;
import com.company.Helance.activity.lobby.forGuestUsers.LobbyForGuestActivity;
import com.company.Helance.interfaces.navigators.SplashNavigator;
import com.company.Helance.activity.splashscreen.viewModel.SplashViewModel;

public class SplashScreen extends AppCompatActivity implements SplashNavigator {

    private final static int SPLASH_ACTIVITY = R.layout.activity_splash_screen;
    private TextView textView;
    private ImageView imageView;
    private TextView connectionStatus;
    private SplashViewModel splashViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(SPLASH_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        splashViewModel = new SplashViewModel(getApplicationContext());
        splashViewModel.setNavigator(this);

        checkInternetConnection();
        findViewById();
        animate();
    }

    @Override
    public void finish() {
        startActivity(new Intent(SplashScreen.this, LobbyForGuestActivity.class));
        super.finish();
    }

    @Override
    public void onBackPressed() {
        end();
    }

    private void end() {
        super.finish();
    }

    private void findViewById() {
        textView = findViewById(R.id.splash_text);
        imageView = findViewById(R.id.splash_image);
        connectionStatus = findViewById(R.id.splash_textView);
    }

    private void animate() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        textView.startAnimation(animation);
        imageView.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                connectionStatus.setVisibility(View.VISIBLE);
                splashViewModel.getAllTask();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void startLoadData() {
        finish();
    }

    private boolean checkInternetConnection() {

        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Server don`t started", Toast.LENGTH_SHORT).show();
        startLoadData();
    }

    @Override
    public void completeLoad() {
        Toast.makeText(getApplicationContext(), "Data downloading", Toast.LENGTH_SHORT).show();
        startLoadData();
    }
}
