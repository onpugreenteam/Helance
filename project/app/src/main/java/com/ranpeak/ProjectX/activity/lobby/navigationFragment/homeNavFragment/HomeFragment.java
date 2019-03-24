package com.ranpeak.ProjectX.activity.lobby.navigationFragment.homeNavFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creatingTask.CreatingTaskActivity;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.homeNavFragment.adapter.TabsHomeFragmentAdapter;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.homeNavFragment.tabFragment.GaveTaskFragment;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.MainFragment;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.adapter.TabsFragmentAdapter;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment implements Activity {

    private FloatingActionButton fab;
    private View view;
    private TabsHomeFragmentAdapter adapter;
    private ViewPager viewPager;
    private SwipeRefreshLayout pullToRefresh;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        findViewById();
        onListener();
        initTabs();

        return view;
    }

    @Override
    public void findViewById() {
        fab = view.findViewById(R.id.floatingActionButton);
        pullToRefresh = view.findViewById(R.id.pullToRefresh1);
        viewPager = view.findViewById(R.id.viewPager11);
    }

    @Override
    public void onListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), CreatingTaskActivity.class));
            }
        });

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefresh.setRefreshing(false);
                    }
                },2500);
            }
        });
    }

    private void initTabs() {
        adapter = new TabsHomeFragmentAdapter(getApplicationContext(), getChildFragmentManager(), new ArrayList<TaskDTO>(),new ArrayList<String>());
        viewPager.setAdapter(adapter);
        new GetTaskWhenUserCostumer().execute();
        TabLayout tabLayout = view.findViewById(R.id.tabLayout1);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public class GetTaskWhenUserCostumer extends AsyncTask<Void, Void, List<TaskDTO>> {

        @Override
        protected List<TaskDTO> doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<TaskDTO>> response = restTemplate.exchange(
                    Constants.URL.GET_ALL_TASK_WHEN_USER_COSTUMER + String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserLogin()),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TaskDTO>>() {
                    });
            List<TaskDTO> taskDTOS = response.getBody();

            return taskDTOS;
        }

        @Override
        protected void onPostExecute(List<TaskDTO> taskDTOS) {
            adapter.setData(taskDTOS);

        }
    }
}
