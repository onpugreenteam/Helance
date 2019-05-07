package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.adapter.MyTaskListAdapter;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task.MyTaskEditActivity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task.MyTaskViewActivity;
import com.ranpeak.ProjectX.dataBase.local.dto.Task;
import com.ranpeak.ProjectX.viewModel.TaskViewModel;


public class MyTaskFragment extends Fragment implements Activity {

    private View view;
    private RecyclerView recyclerView;
    private MyTaskListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TaskViewModel taskViewModel;

    private Button size;


    public MyTaskFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_task, container, false);
        findViewById();
        onListener();
        initItems();
        return view;
    }

    @Override
    public void findViewById() {
        recyclerView = view.findViewById(R.id.fragment_my_task_recyclerView);
        size = view.findViewById(R.id.fragment_my_task_size);
    }

    @Override
    public void onListener() {
        size.setOnClickListener(v->{
            adapter.getItemCount();
            String str = String.valueOf(adapter.getItemCount());
            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        });
    }

    private void initItems() {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new MyTaskListAdapter(/*myTaskItems, */getActivity());
        recyclerView.setAdapter(adapter);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, tasks -> {
            adapter.submitList(tasks);
        });

        // if one of the items was clicked
        adapter.setOnItemClickListener(new MyTaskListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Task task) {
                Intent intent = new Intent(getContext(), MyTaskViewActivity.class);
                intent.putExtra("MyTask", task);

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(Task task) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void itemDeletable(boolean delete) {

            }

            @Override
            public void onUpdateStatusClick(Task task) {
                if (task.getStatus().equals(getString(R.string.not_active))) {
                    task.setStatus(getString(R.string.active));
                } else {
                    task.setStatus(getString(R.string.not_active));
                }
                taskViewModel.update(task);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDeleteClick(Task task) {
                taskViewModel.delete(task);
            }

            @Override
            public void onEditClick(Task task) {
                Intent intent = new Intent(getActivity(), MyTaskEditActivity.class);
                intent.putExtra("MyTask", task);

                startActivity(intent);
            }
        });
    }
}
