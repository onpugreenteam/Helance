package com.ranpeak.ProjectX.splashscreen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.logIn.LogInActivity;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.user.data.RequestHandler;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashScreen extends AwesomeSplash {


    @Override
    public void initSplash(ConfigSplash configSplash) {

        getAllUsers();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
        startActivity(new Intent(SplashScreen.this, LogInActivity.class));
    }

    private void getAllUsers(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL.GET_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Data downloading", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Waiting...", Toast.LENGTH_SHORT).show();
                    }
        });
        RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);
    }
}
