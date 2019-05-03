package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.forYouNavFragment.ForYouFragment;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.ProfileFragment;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.resumesNavFragment.ResumesFragment;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.tasksNavFragment.TasksFragment;


public class LobbyActivity extends AppCompatActivity implements Activity {

   private final static int LOBBY_ACTIVITY = R.layout.activity_lobby;

//   private AnimationDrawable animationDrawable;
   private BottomNavigationView bottomNavigationView;
   final Fragment resumes = new ResumesFragment();
   final Fragment tasks = new TasksFragment();
   final Fragment forYou = new ForYouFragment();
   final Fragment profile = new ProfileFragment();
   final FragmentManager fm = getSupportFragmentManager();

   Fragment active = forYou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewById();
        onListener();

        fm.beginTransaction().add(R.id.navigation_container, profile,"4").hide(profile).commit();
        fm.beginTransaction().add(R.id.navigation_container,tasks,"2").hide(tasks).commit();
        fm.beginTransaction().add(R.id.navigation_container,resumes,"1").hide(resumes).commit();
        fm.beginTransaction().add(R.id.navigation_container,forYou,"3").commit();

        bottomNavigationView.setSelectedItemId(R.id.nav_recom);

    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LobbyActivity.class);
        return intent;
    }

    @Override
    public void findViewById(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }


    @Override
    public void onListener(){
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.nav_resumes:
                    fm.beginTransaction().hide(active).show(resumes).commit();
                    active = resumes;
                    return true;
                case R.id.nav_tasks:
                    fm.beginTransaction().hide(active).show(tasks).commit();
                    active = tasks;
                    return true;
                case R.id.nav_recom:
                    fm.beginTransaction().hide(active).show(forYou).commit();
                    active = forYou;
                    return true;
                case R.id.nav_profile:
                    fm.beginTransaction().hide(active).show(profile).commit();
                    active = profile;
                    return true;
                default:
                    return false;
            }
        });
    }
}
