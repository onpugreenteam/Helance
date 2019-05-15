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
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.adapter.MyTaskListAdapter;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task.MyTaskEditActivity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task.MyTaskViewActivity;
import com.ranpeak.ProjectX.dto.MyTaskDTO;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.dto.pojo.TaskPOJO;
import com.ranpeak.ProjectX.networking.IsOnline;
import com.ranpeak.ProjectX.networking.retrofit.ApiService;
import com.ranpeak.ProjectX.networking.retrofit.RetrofitClient;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.settings.SharedPrefManager;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyTaskViewModel;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
            public void onUpdateStatusClick(MyTaskDTO task) {
                if(IsOnline.getInstance().isConnectingToInternet(Objects.requireNonNull(getContext()))) {
                    if (task.getStatus().equals(getString(R.string.not_active))) {
                        task.setStatus(getString(R.string.active));
                    } else {
                        task.setStatus(getString(R.string.not_active));
                    }

                    myTaskViewModel.update(task);
                    adapter.notifyDataSetChanged();
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
