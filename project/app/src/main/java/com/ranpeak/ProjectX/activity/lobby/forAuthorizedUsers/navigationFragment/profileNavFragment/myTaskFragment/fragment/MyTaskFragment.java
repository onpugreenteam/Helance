package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.adapter.MyTaskListAdapter;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task.MyTaskEditActivity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task.MyTaskViewActivity;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.settings.SharedPrefManager;
import com.ranpeak.ProjectX.viewModel.TaskViewModel;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;


public class MyTaskFragment extends Fragment implements Activity {

    private View view;
    private RecyclerView recyclerView;
    private MyTaskListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TaskViewModel taskViewModel;
    private CompositeDisposable disposable = new CompositeDisposable();

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
    }

    @Override
    public void onListener() {
    }

    private void initItems() {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new MyTaskListAdapter(/*myTaskItems, */getActivity());
        recyclerView.setAdapter(adapter);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        disposable.add(taskViewModel.getAllUsersTask(
                String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskDTOS -> {
                    Log.d("Tasks user profile", String.valueOf(taskDTOS.size()));
                    adapter.submitList(taskDTOS);
                },throwable -> {
                    Log.d("Error upload my task", throwable.getMessage());
                })
        );
//        disposable.dispose();

        // if one of the items was clicked
        adapter.setOnItemClickListener(new MyTaskListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(TaskDTO task) {
                Intent intent = new Intent(getContext(), MyTaskViewActivity.class);
                intent.putExtra("MyTask", task);

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(TaskDTO task) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void itemDeletable(boolean delete) {

            }

            @Override
            public void onUpdateStatusClick(TaskDTO task) {
                if (task.getStatus().equals(getString(R.string.not_active))) {
                    Completable.fromRunnable(() -> {
                        task.setStatus(getString(R.string.active));
                        taskViewModel.update(task);
                    })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                } else {
                    Completable.fromRunnable(() -> {
                        task.setStatus(getString(R.string.not_active));
                        taskViewModel.update(task);
                    })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDeleteClick(TaskDTO task) {
                taskViewModel.delete(task);
            }

            @Override
            public void onEditClick(TaskDTO task) {
                Intent intent = new Intent(getActivity(), MyTaskEditActivity.class);
                intent.putExtra("MyTask", task);

                startActivity(intent);
            }
        });
    }
}
