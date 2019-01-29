package com.ranpeak.ProjectX.splashscreen;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.daimajia.androidanimations.library.Techniques;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.StartActivity;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashScreen extends AwesomeSplash {


    @Override
    public void initSplash(ConfigSplash configSplash) {


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Background animation
        configSplash.setBackgroundColor(R.color.bg_splash);
        configSplash.setAnimCircularRevealDuration(2000);
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);
        configSplash.setRevealFlagX(Flags.REVEAL_BOTTOM);

        //Logo
        configSplash.setLogoSplash(R.drawable.ddd);
        configSplash.setAnimLogoSplashDuration(5000);
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce);


        //Title
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
    //Реализовать окно загрузки программы с нашей эмблемой
}
