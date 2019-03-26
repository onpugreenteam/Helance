package com.ranpeak.ProjectX.activity.lobby;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.ProfileActivity;
import com.ranpeak.ProjectX.activity.creatingTask.CreatingTaskActivity;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.homeNavFragment.HomeFragment;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.MainFragment;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.adapter.TaskListAdapter;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.notificationsNavFragment.NotificationsFragment;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.searchNavFragment.SearchFragment;
import com.ranpeak.ProjectX.activity.settings.SettingsActivity;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.dto.TaskDTO;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class LobbyActivity extends AppCompatActivity implements Activity {

   private final static int LOBBY_ACTIVITY = R.layout.activity_lobby;

   private ImageView imageViewButtonProfile;
   private ImageView imageViewButtonSettings;
   private AnimationDrawable animationDrawable;
   private TextView textView;
   private BottomNavigationView bottomNavigationView;
   final Fragment fragment1 = new HomeFragment();
   final Fragment fragment2 = new MainFragment();
   final Fragment fragment3 = new SearchFragment();
   final Fragment fragment4 = new NotificationsFragment();
   final FragmentManager fm = getSupportFragmentManager();
   private TaskListAdapter adapter;

   private List<TaskDTO> data;

   Fragment active = fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        createFragment();
        findViewById();
        onListener();

        new GetFreeTask().execute();
        fm.beginTransaction().add(R.id.navigation_container,fragment4,"4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.navigation_container,fragment2,"2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.navigation_container,fragment1,"1").hide(fragment1).commit();
        fm.beginTransaction().add(R.id.navigation_container,fragment3,"3").commit();

//        loadFragment(searchFragment);
        bottomNavigationView.setSelectedItemId(R.id.nav_search);
        textView.setText(bottomNavigationView.getMenu().getItem(2).getTitle());

    }


    @Override
    public void findViewById(){
        imageViewButtonProfile = findViewById(R.id.imageViewProfileButton);
        textView = findViewById(R.id.textView2);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        imageViewButtonSettings = findViewById(R.id.imageViewSettingsButton);
    }


    @Override
    public void onListener(){
        imageViewButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        imageViewButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                textView.setText(menuItem.getTitle());
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
//                        loadFragment(homeFragment);
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;
                    case R.id.nav_main:
//                        loadFragment(mainFragment);
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;
                    case R.id.nav_addTask:
                        startActivity(new Intent(getApplicationContext(), CreatingTaskActivity.class));
                        return true;
                    case R.id.nav_search:
//                        loadFragment(searchFragment);
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;
                    case R.id.nav_notification:
//                        loadFragment(homeFragment);
                        fm.beginTransaction().hide(active).show(fragment4).commit();
                        active = fragment4;
                        return true;
                    default:
                        return false;
                }
            }
        });
    }


//    private void createFragment(){
//        mainFragment = MainFragment.newInstance();
//        homeFragment = HomeFragment.newInstance();
//        notificationsFragment = NotificationsFragment.newInstance();
//        searchFragment = SearchFragment.newInstance();
//    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.navigation_container,fragment);
        fragmentTransaction.commit();
    }


//    private void animationBackground(){
//        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
//        animationDrawable.setEnterFadeDuration(4500);
//        animationDrawable.setExitFadeDuration(4500);
//        animationDrawable.start();
//    }


    public class GetFreeTask extends AsyncTask<Void, Void, List<TaskDTO>> {

        @Override
        protected List<TaskDTO> doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<TaskDTO>> response = restTemplate.exchange(
                    Constants.URL.GET_ALL_TASK,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TaskDTO>>(){});
            List<TaskDTO> taskDTOS = response.getBody();

            return taskDTOS;
        }

        @Override
        protected void onPostExecute(List<TaskDTO> taskDTOS) {
            data = taskDTOS;
            Log.d("Data Size", String.valueOf(data.size()));
        }
    }

}
