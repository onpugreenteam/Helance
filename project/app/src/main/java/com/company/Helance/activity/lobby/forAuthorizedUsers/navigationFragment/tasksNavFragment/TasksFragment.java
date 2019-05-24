package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.tasksNavFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.company.Helance.R;
import com.company.Helance.activity.creating.creatingTask.CreatingTaskActivity;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.interfaces.navigators.TaskNavigator;
import com.company.Helance.activity.lobby.adaptersForList.TaskListAdapter;
import com.company.Helance.activity.lobby.viewModel.TaskViewModel;
import com.company.Helance.dto.TaskDTO;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment implements Activity, TaskNavigator {

    private View view;
    private List<TaskDTO> data = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskListAdapter taskListAdapter;
    private PullRefreshLayout mSwipeRefreshLayout;
    private ImageView search;
    private FloatingActionButton fab;
    private TaskViewModel taskViewModel;
    private ProgressBar progressBar;

    public TasksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tasks, container, false);

        taskViewModel = new TaskViewModel(getContext());
        taskViewModel.setNavigator(this);

        findViewById();
        typeRefresh();
        onListener();

        setupAdapter();
        getTasks();
        return view;
    }

    @Override
    public void findViewById() {
        recyclerView = view.findViewById(R.id.fragment_tasks_recycleView_tasks);
        mSwipeRefreshLayout = view.findViewById(R.id.fragment_tasks_swipeRefresh);
        fab = view.findViewById(R.id.fragment_tasks_floatingActionButton);
        search = view.findViewById(R.id.fragment_tasks_search);
        progressBar = view.findViewById(R.id.loading_tasks);
    }

    @Override
    public void onListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            Handler handler = new Handler();
            handler.postDelayed(() -> {

                getTasks();
                mSwipeRefreshLayout.setRefreshing(false);
            }, 1000);
        });
        fab.setOnClickListener(v -> startActivity(
                new Intent(getContext(), CreatingTaskActivity.class)));

//        search.setOnClickListener(v -> startActivity(
//                new Intent(getContext(), SearchTaskAlertDialog.class)));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });
    }

    public void handleError(Throwable throwable) {
        Toast.makeText(getContext(),"Tasks don`t upload",Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataInAdapter(List<TaskDTO> taskDTOS) {
        data.clear();
        data.addAll(taskDTOS);
        taskListAdapter.notifyDataSetChanged();
    }

    @Override
    public void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void setupAdapter(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskListAdapter = new TaskListAdapter(data,recyclerView,getActivity());
        recyclerView.setAdapter(taskListAdapter);
    }

    private void typeRefresh() {
        mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);
    }

    private void getTasks(){
        taskViewModel.getTasksFromServer();
    }
}
