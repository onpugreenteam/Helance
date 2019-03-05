package com.ranpeak.ProjectX.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creatingTask.CreatingTaskActivity;
import com.ranpeak.ProjectX.adapter.TabsFragmentAdapter;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.dto.TaskDTO;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class LobbyActivity extends AppCompatActivity {

   private final static int LOBBY_ACTIVITY = R.layout.activity_lobby;

   private ListView listView;
   private FloatingActionButton fab;
   private Toolbar toolbar;
   private ImageView imageViewButtonProfile;
   private ImageView imageViewButtonNotifications;
   TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        findViewById();

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

initTabs();
    }

    private TabsFragmentAdapter adapter;
    private ViewPager viewPager;

    private void initTabs() {
        viewPager = findViewById(R.id.viewPager);
        adapter = new TabsFragmentAdapter(getApplicationContext(), getSupportFragmentManager(), new ArrayList<TaskDTO>());
        viewPager.setAdapter(adapter);

//        new RemindMeTask().execute();

        new FirstTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void findViewById(){
        fab = findViewById(R.id.floatingActionButton);
        imageViewButtonProfile = findViewById(R.id.imageViewProfileButton);
        imageViewButtonNotifications = findViewById(R.id.imageViewNotificationButton);

    }


    private class RemindMeTask extends AsyncTask<Void, Void, TaskDTO> {

        @Override
        protected TaskDTO doInBackground(Void... params) {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            return template.getForObject(Constants.URL.GET_ALL_TASK, TaskDTO.class);
        }

        @Override
        protected void onPostExecute(TaskDTO remindDTO) {
            List<TaskDTO> data = new ArrayList<>();
            data.add(remindDTO);

            adapter.setData(data);
        }
    }


    private class FirstTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {

            new RemindMeTask().execute();
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.e("task","first");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }

    }

}
