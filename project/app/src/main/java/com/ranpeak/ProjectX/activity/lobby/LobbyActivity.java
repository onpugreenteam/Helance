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
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.ProfileActivity;
import com.ranpeak.ProjectX.activity.SearchActivity;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.homeNavFragment.adapter.TabsHomeFragmentAdapter;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.adapter.TabsFragmentAdapter;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.homeNavFragment.HomeFragment;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.MainFragment;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.notificationsNavFragment.NotificationsFragment;

import java.util.ArrayList;
import java.util.List;

public class LobbyActivity extends AppCompatActivity implements Activity,BottomNavigationView.OnNavigationItemSelectedListener {

   private final static int LOBBY_ACTIVITY = R.layout.activity_lobby;

   private ImageView imageViewButtonProfile;
   private ImageView imageViewButtonSearch;
   private ViewPager viewPager;
   private AnimationDrawable animationDrawable;
   private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewById();
        onListener();

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        NavFragmentPageAdapter adapter = new NavFragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        textView.setText(bottomNavigationView.getMenu().getItem(0).getTitle());
//        viewPager.beginFakeDrag();
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                textView.setText(bottomNavigationView.getMenu().getItem(position).getTitle());
            }
        });

        if (savedInstanceState == null) {
            onNavigationItemSelected(bottomNavigationView.getMenu().findItem(R.id.nav_home));
        }

    }

    @Override
    public void findViewById(){
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
    }



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
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }



}
