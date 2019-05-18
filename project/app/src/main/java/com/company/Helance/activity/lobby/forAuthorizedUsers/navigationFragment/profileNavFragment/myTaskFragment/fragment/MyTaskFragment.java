package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.fragment;

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
import android.widget.Toast;

import com.company.Helance.R;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.adapter.MyTaskListAdapter;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task.MyTaskEditActivity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task.MyTaskViewActivity;
import com.company.Helance.dto.MyTaskDTO;
import com.company.Helance.networking.IsOnline;
import com.company.Helance.networking.retrofit.ApiService;
import com.company.Helance.networking.retrofit.RetrofitClient;
import com.company.Helance.settingsApp.SharedPrefManager;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyTaskViewModel;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;


public class MyTaskFragment extends Fragment implements Activity {

    private View view;
    private RecyclerView recyclerView;
    private MyTaskListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MyTaskViewModel myTaskViewModel;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);


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

        myTaskViewModel = ViewModelProviders.of(this).get(MyTaskViewModel.class);
        disposable.add(myTaskViewModel.getAllUsersTask(
                String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskDTOS -> {
                    Log.d("Users_tasks_in_vm", String.valueOf(taskDTOS.size()));
                    adapter.submitList(taskDTOS);
                    adapter.notifyDataSetChanged();
                })
        );

        // if one of the items was clicked
        adapter.setOnItemClickListener(new MyTaskListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(MyTaskDTO task) {
                Intent intent = new Intent(getContext(), MyTaskViewActivity.class);
                intent.putExtra("MyTask", task);

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(MyTaskDTO task) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void itemDeletable(boolean delete) {

            }

            @Override
            public void onUpdateStatusClick(MyTaskDTO task, int pos) {
                if(IsOnline.getInstance().isConnectingToInternet(Objects.requireNonNull(getContext()))) {
                    if (!task.isActive()) {
                        task.setActive(true);
                    } else {
                        task.setActive(false);
                    }
                    myTaskViewModel.update(task);
                    adapter.notifyItemChanged(pos);
                } else Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(MyTaskDTO task) {
                if(IsOnline.getInstance().isConnectingToInternet(Objects.requireNonNull(getContext()))) {
                    myTaskViewModel.delete(task);
                } else {
                    Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onEditClick(MyTaskDTO task) {
                if(IsOnline.getInstance().isConnectingToInternet(getContext())) {
                    Intent intent = new Intent(getActivity(), MyTaskEditActivity.class);
                    intent.putExtra("MyTask", task);

                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
