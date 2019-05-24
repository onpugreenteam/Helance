package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.forYouNavFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.company.Helance.R;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.forYouNavFragment.adapter.ForYouListAdapter;
import com.company.Helance.dataBase.App;
import com.company.Helance.dataBase.local.LocalDB;
import com.company.Helance.dataBase.local.dao.TaskDAO;
import com.company.Helance.dto.TaskDTO;
import com.company.Helance.interfaces.Activity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ForYouFragment extends Fragment implements Activity {

    private View view;
    private LocalDB localDB;
    private TaskDAO taskDAO;
    private List<TaskDTO> data = new ArrayList<>();
    private RecyclerView recyclerView;
    private ForYouListAdapter adapter;
    private String subject;
    private ImageView search;
    private ProgressBar progressDialog;

    public ForYouFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_for_you, container, false);
        findViewById();
        onListener();
        progressDialog = new ProgressBar(getContext());

        subject = getString(R.string.maths);

        localDB = App.getInstance().getLocalDB();
        taskDAO = localDB.taskDao();
        setupAdapter();

        getRecommendation();

        return view;
    }

    @Override
    public void findViewById() {
        recyclerView = view.findViewById(R.id.fragment_for_you_recycleView);
        search = view.findViewById(R.id.fragment_for_you_search);
    }

    @Override
    public void onListener() {
//        search.setOnClickListener(v -> startActivity(
//                new Intent(getContext(), SearchTaskAlertDialog.class)));
    }

    public static ForYouFragment newInstance() {
        return new ForYouFragment();
    }

    private void setupAdapter(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ForYouListAdapter(data, recyclerView, getActivity());
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("CheckResult")
    private void getRecommendation(){
        taskDAO.getAllTasksForYou(subject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskDTOS -> {
                    data.clear();
                    data.addAll(taskDTOS);
                    adapter.notifyDataSetChanged();
                    Log.d("Data size ForYou", String.valueOf(taskDTOS.size()));
                },throwable -> {
                    Log.d("Error ForYou", String.valueOf(throwable.getMessage()));
                });
    }
}
