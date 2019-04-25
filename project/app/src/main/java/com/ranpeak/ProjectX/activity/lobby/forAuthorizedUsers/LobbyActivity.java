package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.resumesNavFragment.ResumesFragment;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.tasksNavFragment.TasksFragment;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.tasksNavFragment.adapter.TaskListAdapter;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.ProfileFragment;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.forYouNavFragment.ForYouFragment;
import com.ranpeak.ProjectX.dataBase.App;
import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.TaskDAO;


public class LobbyActivity extends AppCompatActivity implements Activity {

   private final static int LOBBY_ACTIVITY = R.layout.activity_lobby;

//   private AnimationDrawable animationDrawable;
   private BottomNavigationView bottomNavigationView;
   final Fragment resumes = new ResumesFragment();
   final Fragment tasks = new TasksFragment();
   final Fragment forYou = new ForYouFragment();
   final Fragment notifications = new ProfileFragment();
   final FragmentManager fm = getSupportFragmentManager();

   Fragment active = forYou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewById();
        onListener();

        fm.beginTransaction().add(R.id.navigation_container,notifications,"4").hide(notifications).commit();
        fm.beginTransaction().add(R.id.navigation_container,tasks,"2").hide(tasks).commit();
        fm.beginTransaction().add(R.id.navigation_container,resumes,"1").hide(resumes).commit();
        fm.beginTransaction().add(R.id.navigation_container,forYou,"3").commit();

        bottomNavigationView.setSelectedItemId(R.id.nav_recom);

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
                    fm.beginTransaction().hide(active).show(notifications).commit();
                    active = notifications;
                    return true;
                default:
                    return false;
            }
        });
    }

//    private void animationBackground(){
//        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
//        animationDrawable.setEnterFadeDuration(4500);
//        animationDrawable.setExitFadeDuration(4500);
//        animationDrawable.start();
//    }
}
