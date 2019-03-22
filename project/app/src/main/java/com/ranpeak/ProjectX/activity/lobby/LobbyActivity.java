package com.ranpeak.ProjectX.activity.lobby;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.ProfileActivity;
import com.ranpeak.ProjectX.activity.SearchActivity;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.adapter.TabsFragmentAdapter;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.homeNavFragment.HomeFragment;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.MainFragment;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.notificationsNavFragment.NotificationsFragment;

public class LobbyActivity extends AppCompatActivity implements Activity {

   private final static int LOBBY_ACTIVITY = R.layout.activity_lobby;

   private ImageView imageViewButtonProfile;
   private ImageView imageViewButtonSearch;
   private TabsFragmentAdapter adapter;
   private ViewPager viewPager;
   private AnimationDrawable animationDrawable;
   private BottomNavigationView bottomNavigationView;
   private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewById();
        onListener();
        initTitle();

        NavFragmentPageAdapter adapter = new NavFragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.beginFakeDrag();


    }

    @Override
    public void findViewById(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        imageViewButtonProfile = findViewById(R.id.imageViewProfileButton);
        imageViewButtonSearch = findViewById(R.id.imageViewSearchButton);
        viewPager = findViewById(R.id.viewPager12);
        textView = findViewById(R.id.textView2);
    }

    @Override
    public void onListener(){
        imageViewButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        imageViewButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                textView.setText(menuItem.getTitle());
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.nav_main:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.nav_profile:
                        viewPager.setCurrentItem(2);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    private void initTitle() {
        textView.setText(bottomNavigationView.getMenu().getItem(0).getTitle());
    }


//    private void animationBackground(){
//        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
//        animationDrawable.setEnterFadeDuration(4500);
//        animationDrawable.setExitFadeDuration(4500);
//        animationDrawable.start();
//    }


    private static class NavFragmentPageAdapter extends FragmentPagerAdapter {

        private NavFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return MainFragment.newInstance();
                case 2:
                    return NotificationsFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
