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
import com.ranpeak.ProjectX.activity.lobby.adapter.TabsFragmentAdapter;
import com.ranpeak.ProjectX.activity.lobby.fragment.navigation.HomeFragment;
import com.ranpeak.ProjectX.activity.lobby.fragment.navigation.MainFragment;
import com.ranpeak.ProjectX.activity.lobby.fragment.navigation.ProfileFragment;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.dto.TaskDTO;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class LobbyActivity extends AppCompatActivity {

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
        initTitle();

        NavFragmentPageAdapter adapter = new NavFragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.beginFakeDrag();

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

    private void findViewById(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        imageViewButtonProfile = findViewById(R.id.imageViewProfileButton);
        imageViewButtonSearch = findViewById(R.id.imageViewSearchButton);
        viewPager = findViewById(R.id.viewPager12);
        textView = findViewById(R.id.textView2);
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
            adapter.setData(taskDTOS);
        }
    }


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
                    return ProfileFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
