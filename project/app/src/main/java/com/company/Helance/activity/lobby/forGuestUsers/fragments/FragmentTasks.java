package com.company.Helance.activity.lobby.forGuestUsers.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.company.Helance.R;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.interfaces.navigators.TaskNavigator;
import com.company.Helance.activity.lobby.adaptersForList.TaskListAdapter;
import com.company.Helance.activity.lobby.viewModel.TaskViewModel;
import com.company.Helance.dto.TaskDTO;

import java.util.ArrayList;
import java.util.List;

public class FragmentTasks extends Fragment implements Activity, TaskNavigator {

     View view;
     private RecyclerView recyclerView;
     private TaskListAdapter taskListAdapter;
     private List<TaskDTO> data = new ArrayList<>();
     private TaskViewModel taskViewModel;
     private PullRefreshLayout pullRefreshLayout;
     private ProgressBar progressBar;

     public FragmentTasks() {
     }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fargment_lobby_list_tasks, container, false);

         taskViewModel = new TaskViewModel(getContext());
        taskViewModel.setNavigator(this);

        findViewById();
        typeRefresh();
        onListener();

        setupAdapter();
        getTasks();

        return view;
    }

    private void typeRefresh() {
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);
    }

    @Override
    public void findViewById() {
         pullRefreshLayout = view.findViewById(R.id.refresh_tasks);
         recyclerView = view.findViewById(R.id.recycler_tasks);
         progressBar = view.findViewById(R.id.loading_tasks_guest);
    }

    @Override
    public void onListener() {
         pullRefreshLayout.setOnRefreshListener(() -> {
             final Handler handler = new Handler();
             handler.postDelayed(() -> {
                 getTasks();
                 pullRefreshLayout.setRefreshing(false);
             }, 1000);
         });
    }

    @Override
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

    private void getTasks(){
         taskViewModel.getTasksFromServer();
    }
}


