package com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.adapter.TabsFragmentAdapter;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.dto.TaskDTO;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MainFragment extends Fragment implements Activity {

    private TabsFragmentAdapter adapter;
    private ViewPager viewPager;
    private View view;
    private SwipeRefreshLayout pullToRefresh;


    public MainFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        findViewById();
        onListener();
        initTabs();


        return view;
    }

    @Override
    public void findViewById(){
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        viewPager = view.findViewById(R.id.viewPager1);
    }

    @Override
    public void onListener(){
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new GetFreeTask().execute();
                        pullToRefresh.setRefreshing(false);
                    }
                },2500);
            }
        });
    }

    private void initTabs() {
        adapter = new TabsFragmentAdapter(getApplicationContext(), getChildFragmentManager(), new ArrayList<TaskDTO>(),new ArrayList<String>());
        viewPager.setAdapter(adapter);
        new GetFreeTask().execute();
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }


    public static MainFragment newInstance() {
        return new MainFragment();
    }


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
}
