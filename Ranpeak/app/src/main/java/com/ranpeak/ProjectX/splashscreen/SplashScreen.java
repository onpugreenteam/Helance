package com.ranpeak.ProjectX.splashscreen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.LobbyActivity;
import com.ranpeak.ProjectX.activity.StartActivity;
import com.ranpeak.ProjectX.user.data.SharedPrefManager;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashScreen extends AwesomeSplash {


    @Override
    public void initSplash(ConfigSplash configSplash) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Анимация перед показом эмблемы и заголовка
        configSplash.setBackgroundColor(R.color.bg_splash);
        configSplash.setAnimCircularRevealDuration(2000);
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);
        configSplash.setRevealFlagX(Flags.REVEAL_BOTTOM);

        // Эмблема
        configSplash.setLogoSplash(R.drawable.splashscreen_emblem);
        configSplash.setAnimLogoSplashDuration(5000);
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce);

        // Текст(заголовок)
        configSplash.setTitleSplash(getString(R.string.splashscreenText));
        configSplash.setTitleTextColor(R.color.colorAccent);
        configSplash.setTitleTextSize(30f);
        configSplash.setAnimLogoSplashDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.Bounce);
    }


    @Override
    public void animationsFinished() {
        startActivity(new Intent(SplashScreen.this, StartActivity.class));
    }


//    public final static int SPLASH = R.layout.activity_splash_screen;
//
//    Handler handler = new Handler();
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//
////            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
////            params.setMargins(1, -240, 1, 1);
////
////            image.setLayoutParams(params);
////            text.setVisibility(View.GONE);
////            image.getLayoutParams().width = 130;
////            image.getLayoutParams().height = 130;
//            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////            startActivity(intent);
//        }
//    };
//    ImageView image;
//    TextView text;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(SPLASH);
//
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//
//
//        image = findViewById(R.id.splash_image);
//        text = findViewById(R.id.splash_text);
//
//        handler.postDelayed(runnable, 2000);
//    }
}
