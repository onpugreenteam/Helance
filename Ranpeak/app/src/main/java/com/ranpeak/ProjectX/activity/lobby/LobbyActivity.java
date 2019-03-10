package com.ranpeak.ProjectX.activity.lobby;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.NotificationsActivity;
import com.ranpeak.ProjectX.activity.ProfileActivity;
import com.ranpeak.ProjectX.activity.creatingTask.CreatingTaskActivity;
import com.ranpeak.ProjectX.activity.lobby.adapter.TabsFragmentAdapter;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.dto.TaskDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

public class LobbyActivity extends AppCompatActivity {

   private final static int LOBBY_ACTIVITY = R.layout.activity_lobby;

   private FloatingActionButton fab;
   private ImageView imageViewButtonProfile;
   private ImageView imageViewButtonNotifications;
   private TabsFragmentAdapter adapter;
   private ViewPager viewPager;
   private SwipeRefreshLayout pullToRefresh;
   private AnimationDrawable animationDrawable;
   private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        findViewById();
        initTabs();
        animationBackground();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LobbyActivity.this, CreatingTaskActivity.class));
            }
        });

        imageViewButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

            }
        });

        imageViewButtonNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
            }
        });

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new GetFreeTask().execute();
                        pullToRefresh.setRefreshing(false);
                    }
                },4000);
            }
        });

    }


    private void initTabs() {
        adapter = new TabsFragmentAdapter(getApplicationContext(), getSupportFragmentManager(), new ArrayList<TaskDTO>());
        viewPager.setAdapter(adapter);
        new GetFreeTask().execute();
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void findViewById(){
        constraintLayout = findViewById(R.id.myBackLobby);
        fab = findViewById(R.id.floatingActionButton);
        imageViewButtonProfile = findViewById(R.id.imageViewProfileButton);
        imageViewButtonNotifications = findViewById(R.id.imageViewNotificationButton);
        viewPager = findViewById(R.id.viewPager);
        pullToRefresh = findViewById(R.id.pullToRefresh);
    }

    private void animationBackground(){
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();
    }

    private class GetFreeTask extends AsyncTask<Void, Void, List<TaskDTO>> {

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

}
